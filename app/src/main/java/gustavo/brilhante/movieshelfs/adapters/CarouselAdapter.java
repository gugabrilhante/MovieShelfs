package gustavo.brilhante.movieshelfs.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CirclePageIndicator;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

import gustavo.brilhante.movieshelfs.R;
import gustavo.brilhante.movieshelfs.activity.ShowMovieActivity_;
import gustavo.brilhante.movieshelfs.models.Movie;
import gustavo.brilhante.movieshelfs.models.MovieList;

/**
 * Created by Gustavo on 05/02/17.
 */

public class CarouselAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MovieList> movieCollection;
    Context context;

    int imageWith;

    public CarouselAdapter(ArrayList<MovieList> movieCollection, Context context) {
        this.movieCollection = movieCollection;
        this.context = context;
    }

    class ViewHolderCarousel extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        CarouselView carouselView;
        View mView;
        MovieList movieList;

        public ViewHolderCarousel(View itemView) {
            super(itemView);
            mView = itemView;
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            carouselView = (CarouselView) itemView.findViewById(R.id.carouselView);

        }

        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_carousel_holder, parent, false);
        ViewHolderCarousel viewHolderCarousel = new ViewHolderCarousel(view);
        return viewHolderCarousel;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieList movieList = movieCollection.get(position);
        ViewHolderCarousel viewHolderCarousel = (ViewHolderCarousel) holder;

        viewHolderCarousel.titleTextView.setText(movieList.getLabel());

        viewHolderCarousel.carouselView.setViewListener(intantiateViewListener( movieList, context));
        viewHolderCarousel.carouselView.setPageCount(movieList.getMovies().size());
        setCarouselHeightSize(viewHolderCarousel.carouselView);
        CirclePageIndicator indicator = (CirclePageIndicator) viewHolderCarousel.carouselView.findViewById(R.id.indicator);
        if (indicator != null) {
            indicator.setVisibility(View.GONE);
        }

    }

    ViewListener intantiateViewListener(final MovieList movieList, final Context context){
        return new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                final Movie movie = movieList.getMovies().get(position);

                View customView = LayoutInflater.from(context).inflate(R.layout.carousel_item, null);
                customView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowMovieActivity_.intent(context).movie(movie).justShow(true).start();
                    }
                });

                ImageView imageView = (ImageView) customView.findViewById(R.id.carouselImageview);
                TextView textView = (TextView) customView.findViewById(R.id.carouselTextView);
                if(movie.getImagePoster()!=null) {
                    imageView.setImageBitmap(movie.getImagePoster());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
                imageWith = imageView.getLayoutParams().width;
                textView.setText(movie.getTitle());

                return customView;
            }


        };
    }

    void setCarouselHeightSize(CarouselView carouselView){
        int width = carouselView.getLayoutParams().width;
        int height = carouselView.getLayoutParams().height;
        //int padding = (width - ((2*height)/3))/2;
        int padding = height / 2;
        ViewPager containerViewPager = (ViewPager) carouselView.findViewById(R.id.containerViewPager);
        //if(containerViewPager.get)containerViewPager.setCurrentItem();
        containerViewPager.setPadding(padding,0,padding,0);
        containerViewPager.setClipToPadding(false);
        containerViewPager.setPageMargin(-height/5);
    }

    @Override
    public int getItemCount() {
        return movieCollection.size();
    }
}
