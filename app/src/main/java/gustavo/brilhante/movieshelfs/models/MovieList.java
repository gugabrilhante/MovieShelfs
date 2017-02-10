package gustavo.brilhante.movieshelfs.models;

import java.util.ArrayList;

/**
 * Created by Gustavo on 31/01/17.
 */

public class MovieList {

    private String label;

    private ArrayList<Movie> movies;

    public void createList(){
        movies = new ArrayList<>();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
