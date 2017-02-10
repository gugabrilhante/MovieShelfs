package gustavo.brilhante.movieshelfs.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.synnapps.carouselview.ViewListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import gustavo.brilhante.movieshelfs.R;
import gustavo.brilhante.movieshelfs.adapters.CarouselAdapter;
import gustavo.brilhante.movieshelfs.base.BaseFragment;
import gustavo.brilhante.movieshelfs.models.Movie;
import gustavo.brilhante.movieshelfs.models.MovieList;
import io.realm.Realm;

@EFragment(R.layout.fragment_shelf)
public class ShelfFragment extends BaseFragment {

    @ViewById
    RecyclerView recyclerView;


    Realm realm;

    List<Movie> moviesFullList;

    ArrayList<MovieList> movieCollection = new ArrayList<>();

    CarouselAdapter adapter;

    @AfterViews
    void afterViews(){

        realm = Realm.getDefaultInstance();
        moviesFullList = realm.where(Movie.class).findAll();
        moviesFullList = realm.copyFromRealm(moviesFullList);
        //separeteList();


        /*MovieList movieList = new MovieList();
        movieList.setLabel("teste");
        movieList.setMovies(new ArrayList<Movie>(moviesFullList));
        movieCollection.add(movieList);
        setAdapter();*/
        separeteByTypeList();
        setAdapter();
    }

    void separeteByTypeList(){
        MovieList movieList = new MovieList();
        movieList.setLabel("Filmes");
        movieList.createList();
        MovieList serieList = new MovieList();
        serieList.setLabel("Séries");
        serieList.createList();
        for(int i=0; i<moviesFullList.size(); i++){
            if(moviesFullList.get(i).getType().toLowerCase().trim().contains("serie"))serieList.getMovies().add(moviesFullList.get(i));
            else movieList.getMovies().add(moviesFullList.get(i));
        }

        movieCollection.add(movieList);
        movieCollection.add(serieList);


    }

    void separeteList(){
        boolean found = false;

        for(int i=0; i< moviesFullList.size(); i++){
            Movie movie = moviesFullList.get(i);
            for(int k=0; k<movieCollection.size(); k++){
                if(movieCollection.get(k).getMovies().size()>0) {
                    if (movieCollection.get(k).getMovies().get(0).getType()==movie.getType()){
                        found = true;
                        movieCollection.get(k).getMovies().add(movie);
                        break;
                    }
                }
            }
            if(!found){
                ArrayList<Movie> moviesListToAdd = new ArrayList<Movie>();
                moviesListToAdd.add(movie);
                MovieList movieList = new MovieList();
                movieList.setMovies(moviesListToAdd);
                movieList.setLabel(movie.getType());
                movieCollection.add(movieList);
            }
            found=false;
        }
        setAdapter();

    }

    void setAdapter(){
        adapter = new CarouselAdapter(movieCollection, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    ViewListener intantiateViewListener(final MovieList movieList, final Context context){
        return new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                Movie movie = movieList.getMovies().get(position);

                View customView = LayoutInflater.from(context).inflate(R.layout.carousel_item, null);
                customView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                ImageView imageView = (ImageView) customView.findViewById(R.id.carouselImageview);
                TextView textView = (TextView) customView.findViewById(R.id.carouselTextView);
                if(movie.getImagePoster()!=null) {
                    imageView.setImageBitmap(movie.getImagePoster());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
                textView.setText(movie.getTitle());

                return customView;
            }


        };
    }


}
