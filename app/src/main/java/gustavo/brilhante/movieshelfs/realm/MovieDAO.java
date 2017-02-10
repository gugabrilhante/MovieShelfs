package gustavo.brilhante.movieshelfs.realm;

import android.content.Context;

import gustavo.brilhante.movieshelfs.models.Movie;
import gustavo.brilhante.movieshelfs.models.MovieList;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.internal.IOException;

/**
 * Created by Gustavo on 31/01/17.
 */

public class MovieDAO {
    Realm realm;
    MovieList movieList;

    public MovieDAO(Context context) {
        initRealm(context);
    }

    void initRealm(Context context){
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public void beginTransaction(){
        realm.beginTransaction();
    }

    public void saveMovieList(Movie movie) throws Exception{
        try {
            realm.copyToRealmOrUpdate(movie);
            realm.commitTransaction();
        } catch (IOException ex) {
            throw new Exception("Erro ao realizar ação com banco de dados");
        }
    }

    public RealmResults<Movie> getMovieList(){
        return realm.where(Movie.class).findAll();
    }
}
