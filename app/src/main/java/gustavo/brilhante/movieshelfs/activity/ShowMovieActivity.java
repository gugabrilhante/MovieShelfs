package gustavo.brilhante.movieshelfs.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import gustavo.brilhante.movieshelfs.R;
import gustavo.brilhante.movieshelfs.models.Movie;
import gustavo.brilhante.movieshelfs.requests.HttpRequest;
import io.realm.Realm;
import io.realm.internal.IOException;

@EActivity(R.layout.activity_show_movie)
public class ShowMovieActivity extends AppCompatActivity {

    @ViewById
    TextView Title, Year, Rated, Released, Runtime, Genre, Director, Writer, Actors, Plot, Language, Country, Awards;
    @ViewById
    TextView imdbRating, imdbVotes, Episode, Season, totalSeasons;

    @ViewById
    LinearLayout totalSeansonsLayout;

    @ViewById
    ImageButton posterButton;

    @ViewById
    CircularProgressView progressView;

    @Extra
    boolean justShow = false;

    @Extra
    Movie movie;

    Realm realm;

    @ViewById
    RelativeLayout buttonLayout;


    @AfterViews
    void afterViews(){
        realm = Realm.getDefaultInstance();
        setViews();
        if(!justShow)requestImage();
        else if(movie.getImagePoster()!=null && posterButton!=null){
            posterButton.setBackground(null);
            posterButton.setImageBitmap(movie.getImagePoster());
            posterButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    void setViews(){
        if(movie!=null){
            Title.setText("Título: " + movie.getTitle());

            if(!movie.getType().equals("episode")) {
                Episode.setVisibility(View.GONE);
                Season.setVisibility(View.GONE);
                Year.setText("Ano: " + movie.getYear());
                Runtime.setText("Duração: " + movie.getRuntime());
            }else{
                Episode.setText(movie.getEpisode());
                Season.setText(movie.getSeason());
                Year.setVisibility(View.GONE);
                Runtime.setVisibility(View.GONE);
            }

            if(movie.getTotalSeasons()!=null){
                totalSeasons.setText(movie.getTotalSeasons());
            }else{
                totalSeansonsLayout.setVisibility(View.GONE);
            }

            Rated.setVisibility(View.GONE);
            Released.setText(movie.getReleased());
            Genre.setText(movie.getGenre());
            Director.setText(movie.getDirector());
            Writer.setText(movie.getWriter());
            Actors.setText(movie.getActors());
            Plot.setText(movie.getPlot());
            Language.setText(movie.getLanguage());
            Country.setText(movie.getCountry());
            Awards.setText(movie.getAwards());
            imdbRating.setText(movie.getImdbRating());
            imdbVotes.setText(movie.getImdbVotes());

            if(justShow)buttonLayout.setVisibility(View.GONE);
        }
    }

    @Click(R.id.saveButton)
    void onSaveButtonClick(){
        try {
            if(movie!=null){/*
                Number currentIdNum = realm.where(Movie.class).max(usersFields.ID);
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }*/

                realm.beginTransaction();
                realm.copyToRealm(movie);
                realm.commitTransaction();
                finish();
            }
        } catch (IOException ex) {

        }
    }

    @Background
    void requestImage(){


        String url = movie.getPoster();

        Bitmap bitmap = HttpRequest.getBitmapFromURL(url);

        setBitmapToButton(bitmap);

        /*try {
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
        }*/

    }

    @UiThread
    void setBitmapToButton(Bitmap bitmap){
        if(movie!=null)movie.setImagePoster(bitmap);
        if(posterButton!=null) {
            posterButton.setBackground(null);
            posterButton.setImageBitmap(bitmap);
            posterButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    @UiThread
    void setLoading(boolean isLoading){
        if(isLoading && progressView.getVisibility()==View.GONE){
            if(progressView!=null) {
                progressView.setVisibility(View.VISIBLE);
                progressView.startAnimation();
            }

        }else if(!isLoading && progressView.getVisibility() == View.VISIBLE){
            if(progressView!=null) {
                progressView.setVisibility(View.GONE);
                progressView.stopAnimation();
            }
        }
    }

}
