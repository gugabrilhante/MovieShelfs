package gustavo.brilhante.movieshelfs.adapters.holders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.io.IOException;
import java.io.InputStream;

import gustavo.brilhante.movieshelfs.R;
import gustavo.brilhante.movieshelfs.SearchListener;
import gustavo.brilhante.movieshelfs.models.Search;
import gustavo.brilhante.movieshelfs.requests.MakeRequest;
import okhttp3.Response;

/**
 * Created by Gustavo on 04/02/17.
 */

public class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageButton posterButton;
    CircularProgressView progressView;
    TextView Title, Year, Type;

    View view;

    Context context;

    SearchListener listener;

    Search search;

    public SearchHolder(View view) {
        super(view);
        this.view = view;
        view.setOnClickListener(this);
        context = view.getContext();
        posterButton = (ImageButton) view.findViewById(R.id.posterButton);
        posterButton.setOnClickListener(this);
        progressView = (CircularProgressView) view.findViewById(R.id.progressView);
        Title = (TextView) view.findViewById(R.id.Title);
        Year = (TextView) view.findViewById(R.id.Year);
        Type = (TextView) view.findViewById(R.id.Type);
    }
    public void bind(Search search){
        this.search = search;

        //requestImage();

        if(search.getBitmap()!=null){
            setBitmapToButton(search.getBitmap());
        }

        Title.setText(search.getTitle());
        Year.setText(search.getYear());
        Type.setText(search.getType());

    }
    public static SearchHolder build(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_holder, parent, false);
        SearchHolder holder = new SearchHolder(view);
        return holder;
    }

    void requestImage(){

        String url = search.getPoster();

        try {
            setLoading(true);
            Response response = MakeRequest.get(url, null);

            if(response.isSuccessful()){
                String resp = response.body().string();

                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                setBitmapToButton(bitmap);
                setLoading(false);
            }else{
                String resp = response.body().string();
                setLoading(false);
                //showError();
            }

        } catch (IOException e) {
            e.printStackTrace();
            setLoading(false);
        }

    }


    void setBitmapToButton(Bitmap bitmap){
        posterButton.setBackground(null);
        posterButton.setImageBitmap(bitmap);
        posterButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    void setLoading(boolean isLoading){
        if(isLoading && progressView.getVisibility()==View.GONE){
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();

        }else if(!isLoading && progressView.getVisibility() == View.VISIBLE){
            progressView.setVisibility(View.GONE);
            progressView.stopAnimation();
        }
    }

    public void setListener(SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null)listener.onSearchClick(search);
    }
}
