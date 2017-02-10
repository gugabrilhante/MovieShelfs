package gustavo.brilhante.movieshelfs.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Gustavo on 05/02/17.
 */

public class MovieShelfApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME).build();
        Realm.setDefaultConfiguration(config);
    }
}
