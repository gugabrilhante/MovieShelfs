package gustavo.brilhante.movieshelfs.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;

import gustavo.brilhante.movieshelfs.R;
import gustavo.brilhante.movieshelfs.activity.SearchListActivity_;
import gustavo.brilhante.movieshelfs.activity.ShowMovieActivity_;
import gustavo.brilhante.movieshelfs.base.BaseFragment;
import gustavo.brilhante.movieshelfs.models.Argument;
import gustavo.brilhante.movieshelfs.models.Movie;
import gustavo.brilhante.movieshelfs.models.SearchList;
import gustavo.brilhante.movieshelfs.requests.MakeRequest;
import gustavo.brilhante.movieshelfs.utils.components.GustavoEditText;
import gustavo.brilhante.movieshelfs.utils.constants.Constants;
import okhttp3.Response;

@EFragment(R.layout.fragment_search)
public class SearchFragment extends BaseFragment {

    public static int SERIE_SELECTED = 1;
    public static int FILME_SELECTED = 2;
    public static int TODOS_SELECTED = 3;

    int typeSelected = 0;

    public static int TRANSITION_DURATION = 300;

    @ViewById
    GustavoEditText nomeFilmeEditText, anoLancamentoEditText, nomeSerieEditText, temporadaEditText, episodioEditText, nomeEditText, paginaEditText;

    @ViewById
    LinearLayout rootView, filmeLinearLayout, serieLinearLayout, todosLinearLayout;

    @ViewById
    TextView filmeTextView, serieTextView, todosTextView;

    @ViewById
    CircularProgressView progressView;

    @ViewById
    TextView buscarTextView;

    Gson gson;

    Movie movie;
    SearchList searchList;

    String title, pagina;

    boolean isAnimating = false;

    @AfterViews
    void afterViews(){
        gson = new Gson();

        //setViews();
        setViewSelected(FILME_SELECTED);
    }

    void setViews(){
        setLoading(false);
    }

    @UiThread
    void setLoading(boolean isLoading){
        if(isLoading && progressView.getVisibility()==View.GONE){
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
            buscarTextView.setVisibility(View.GONE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,  WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }else if(!isLoading && progressView.getVisibility() == View.VISIBLE){
            progressView.setVisibility(View.GONE);
            progressView.stopAnimation();
            buscarTextView.setVisibility(View.VISIBLE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Click(R.id.filmeTextView)
    void filmeClick(){
        setViewSelected(FILME_SELECTED);
    }

    @Click(R.id.todosTextView)
    void todosClick(){
        setViewSelected(TODOS_SELECTED);
    }

    @Click(R.id.serieTextView)
    void serieClick(){
        setViewSelected(SERIE_SELECTED);

    }

    @Click(R.id.searchButton)
    void searchClick(){
        setLoading(true);
        if(typeSelected==FILME_SELECTED)makeMovieRequest();
        else if(typeSelected==TODOS_SELECTED)makeListRequest();
        else if(typeSelected==SERIE_SELECTED)makeSerieRequest();
    }

    @Background
    void makeMovieRequest(){
        String title = nomeFilmeEditText.getText();
        String anoLancamento = anoLancamentoEditText.getText();

        ArrayList<Argument> parametros = new ArrayList<Argument>();
        //parametros.put("r","json");
        if(!title.trim().isEmpty())parametros.add(new Argument("t",title.replaceAll(" ","+")));
        if(!anoLancamento.trim().isEmpty())parametros.add(new Argument("y",anoLancamento));
        if(parametros.size()>0)parametros.add(new Argument("r","json"));

        String url = Constants.baseUrl;

        try {
            Response response = MakeRequest.get(url, parametros, null);

            if(response.isSuccessful()){
                String resp = response.body().string();

                movie = gson.fromJson(resp, Movie.class);

                setLoading(false);
                goToShowMovie();
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

    @Background
    void makeListRequest(){
        title = nomeEditText.getText();
        pagina = paginaEditText.getText();

        if(pagina.trim().isEmpty()) pagina = "1";

        ArrayList<Argument> parametros = new ArrayList<Argument>();
        //parametros.put("r","json");
        if(!title.trim().isEmpty())parametros.add(new Argument("s",title.replaceAll(" ","+")));
        parametros.add(new Argument("page",pagina));

        String url = Constants.baseUrl;

        try {
            Response response = MakeRequest.get(url, parametros, null);

            if(response.isSuccessful()){
                String resp = response.body().string();

                searchList = gson.fromJson(resp, SearchList.class);

                setLoading(false);
                goToSearchList();
            }else{
                setLoading(false);
                searchList = gson.fromJson(Constants.searchMock, SearchList.class);
                goToSearchList();
                //showError();
            }

        } catch (IOException e) {
            e.printStackTrace();
            setLoading(false);
        }

    }

    private void goToSearchList() {
        try{
            int page = Integer.parseInt(pagina);
            SearchListActivity_.intent(this)
                .searchList(searchList)
                .title(title)
                .page(page)
                .start();
        }catch(NumberFormatException e){

        }
    }

    String addArg(String key, String arg){
        return key+"="+arg;
    }



    @Background
    void makeSerieRequest(){
        String title = nomeSerieEditText.getText();
        String temporada = temporadaEditText.getText();
        String episode = episodioEditText.getText();

        ArrayList<Argument> parametros = new ArrayList<Argument>();
        if(!title.trim().isEmpty())parametros.add(new Argument("t",title.replaceAll(" ","+")));
        if(!temporada.trim().isEmpty())parametros.add(new Argument("Season",temporada));
        if(!temporada.trim().isEmpty())parametros.add(new Argument("Episode",episode));

        String url = Constants.baseUrl;

        try {
            Response response = MakeRequest.get(url, parametros, null);

            if(response.isSuccessful()){
                String resp = response.body().string();

                movie = gson.fromJson(resp, Movie.class);

                setLoading(false);
                goToShowMovie();
            }else{
                setLoading(false);
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
        ShowMovieActivity_.intent(getActivity()).movie(movie).start();
    }


    void setViewSelected(int viewSelected){
        if(viewSelected==FILME_SELECTED && typeSelected!=viewSelected) {
            filmeTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_left_selected));
            filmeTextView.setTextColor(getActivity().getResources().getColor(R.color.white));
            todosTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_middle));
            todosTextView.setTextColor(getActivity().getResources().getColor(R.color.colorTextView01));
            serieTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_right));
            serieTextView.setTextColor(getActivity().getResources().getColor(R.color.colorTextView01));
            showFilmes();
        }else if(viewSelected==SERIE_SELECTED && typeSelected!=viewSelected){
            filmeTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_left));
            filmeTextView.setTextColor(getActivity().getResources().getColor(R.color.colorTextView01));
            todosTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_middle));
            todosTextView.setTextColor(getActivity().getResources().getColor(R.color.colorTextView01));
            serieTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_right_selected));
            serieTextView.setTextColor(getActivity().getResources().getColor(R.color.white));
            showSeries();
        }
        else if(viewSelected==TODOS_SELECTED && typeSelected!=viewSelected){
            filmeTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_left));
            filmeTextView.setTextColor(getActivity().getResources().getColor(R.color.colorTextView01));
            todosTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_middle_selected));
            todosTextView.setTextColor(getActivity().getResources().getColor(R.color.white));
            serieTextView.setBackground(getActivity().getResources().getDrawable(R.drawable.box_shape_right));
            serieTextView.setTextColor(getActivity().getResources().getColor(R.color.colorTextView01));
            showTodos();
        }
        typeSelected = viewSelected;
    }

    void showSeries(){
        if(typeSelected==FILME_SELECTED)animateFadeOut(filmeLinearLayout);
        else if(typeSelected==TODOS_SELECTED)animateFadeOut(todosLinearLayout);

        animateFadeIn(serieLinearLayout);
    }

    void showFilmes(){
        if(typeSelected==TODOS_SELECTED)animateFadeOut(todosLinearLayout);
        else if(typeSelected==SERIE_SELECTED)animateFadeOut(serieLinearLayout);

        animateFadeIn(filmeLinearLayout);
    }

    void showTodos(){
        if(typeSelected==FILME_SELECTED)animateFadeOut(filmeLinearLayout);
        else if(typeSelected==SERIE_SELECTED)animateFadeOut(serieLinearLayout);

        animateFadeIn(todosLinearLayout);
    }

    void animateFadeOut(final View view){
        view.setAlpha(1f);
        view.animate().alpha(0f).setDuration(TRANSITION_DURATION).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        }).start();
    }

    void animateFadeIn(final View view){
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(TRANSITION_DURATION).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        }).start();
    }

}
