package gustavo.brilhante.movieshelfs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gustavo on 05/02/17.
 */

public class Season {

    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("Season")
    @Expose
    public String season;
    @SerializedName("totalSeasons")
    @Expose
    public String totalSeasons;
    @SerializedName("Episodes")
    @Expose
    public List<Search> episodes = null;
    @SerializedName("Response")
    @Expose
    public String response;

}
