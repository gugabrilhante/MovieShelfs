package gustavo.brilhante.movieshelfs.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;

import gustavo.brilhante.movieshelfs.R;
import gustavo.brilhante.movieshelfs.SearchListener;
import gustavo.brilhante.movieshelfs.adapters.SearchAdapter;
import gustavo.brilhante.movieshelfs.models.Argument;
import gustavo.brilhante.movieshelfs.models.Movie;
import gustavo.brilhante.movieshelfs.models.Search;
import gustavo.brilhante.movieshelfs.models.SearchList;
import gustavo.brilhante.movieshelfs.requests.HttpRequest;
import gustavo.brilhante.movieshelfs.requests.MakeRequest;
import gustavo.brilhante.movieshelfs.utils.constants.Constants;
import okhttp3.Response;

@EActivity(R.layout.activity_search_list)
public class SearchListActivity extends AppCompatActivity implements SearchListener {

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    CircularProgressView progressView;

    @ViewById
    ImageButton backwardBtn, fowardBtn;

    @ViewById
    TextView pageIndexTextView;

    SearchAdapter adapter;

    @Extra
    SearchList searchList;

    @Extra
    String title;

    @Extra
    int page;

    Movie movie;

    int bitmapCount = 0;

    Gson gson;

    boolean isLoadingSpinning = false;


    @AfterViews
    void afterViews(){

        gson = new Gson();

        //searchList = gson.fromJson(Constants.searchMock, SearchList.class);

//        adapter = new SearchAdapter(this, new ArrayList<Search>(searchList.getSearch()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
//        recyclerView.setAdapter(adapter);


        if (page == 1) backwardBtn.setVisibility(View.INVISIBLE);

        setLoading(true);
        setImagesRequest();


    }

    @Click(R.id.backwardBtn)
    void backwardClick(){
        if(!isLoadingSpinning) {
            if (page > 1) {
                page--;
                makeListRequest();
                if (page == 1) backwardBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Click(R.id.fowardBtn)
    void fowardClick(){
        if(!isLoadingSpinning) {
            page++;
            makeListRequest();
        }

    }

    @Background
    void makeListRequest(){
        setLoading(true);
        String title = this.title;
        String pagina = page+"";

        ArrayList<Argument> parametros = new ArrayList<Argument>();
        //parametros.put("r","json");
        if(!title.trim().isEmpty())parametros.add(new Argument("s",title.replaceAll(" ","+")));
        if(!pagina.trim().isEmpty())parametros.add(new Argument("page",pagina));

        String url = Constants.baseUrl;

        try {
            Response response = MakeRequest.get(url, parametros, null);

            if(response.isSuccessful()){
                String resp = response.body().string();


                searchList = gson.fromJson(resp, SearchList.class);
                setImagesRequest();

            }else{

            }

        }
        catch (JsonParseException e) {
            page--;
            fowardBtn.setVisibility(View.INVISIBLE);
            setLoading(false);
        } catch (IOException e) {
            e.printStackTrace();
            setLoading(false);
        }

    }


    @UiThread
    void setLoading(boolean isLoading){
        if(isLoading && progressView.getVisibility()== View.GONE){
            isLoadingSpinning = isLoading;
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
            recyclerView.setVisibility(View.GONE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,  WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }else if(!isLoading && progressView.getVisibility() == View.VISIBLE){
            isLoadingSpinning = isLoading;
            progressView.setVisibility(View.GONE);
            progressView.stopAnimation();
            recyclerView.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Background(serial = "imageFlow")
    void setImagesRequest(){
        bitmapCount = 0;
        for (int i=0; i<searchList.getSearch().size(); i++){
            requestImage(i);
        }
    }

    @Background(serial = "imageFlow")
    void requestImage(int index){

        String url = searchList.getSearch().get(index).getPoster();


        Bitmap bitmap = HttpRequest.getBitmapFromURL(url);
        setImageToSearch(index, bitmap);

        /*try {
            Response response = MakeRequest.get(url, null);

            if(response.isSuccessful()){
                String resp = response.body().string();

                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                setImageToSearch(index, bitmap);

            }else{
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Background
    void setImageToSearch(int index, Bitmap bitmap){
        bitmapCount++;
        searchList.getSearch().get(index).setBitmap(bitmap);
        if(bitmapCount>=searchList.getSearch().size()){
            adapter = new SearchAdapter(this, new ArrayList<Search>(searchList.getSearch()));
            adapter.setListener(this);
            setAdapter();
            setLoading(false);
        }
        //adapter.notifyDataSetChanged();
    }

    @UiThread
    void setAdapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        if(page>1)backwardBtn.setVisibility(View.VISIBLE);
        pageIndexTextView.setText(""+page);
    }

    @Background
    void makeRequestById(String id){

        setLoading(true);

        ArrayList<Argument> parametros = new ArrayList<Argument>();
        parametros.add(new Argument("i",id));

        String url = Constants.baseUrl;

        try {
            Response response = MakeRequest.get(url, parametros, null);

            if(response.isSuccessful()){
                String resp = response.body().string();

                movie = gson.fromJson(resp, Movie.class);

                goToShowMovie();
            }else{
                movie = gson.fromJson(Constants.serieMock, Movie.class);
                goToShowMovie();
                //showError();
            }

        } catch (IOException e) {
            e.printStackTrace();
            setLoading(false);
        }


    }

    @UiThread
    void goToShowMovie(){
        ShowMovieActivity_.intent(this).movie(movie).start();
        setLoading(false);
    }

    @Override
    public void onSearchClick(Search search) {
        makeRequestById(search.imdbID);
    }
}
