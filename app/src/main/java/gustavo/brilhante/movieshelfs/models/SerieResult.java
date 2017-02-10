package gustavo.brilhante.movieshelfs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gustavo on 03/02/17.
 */

public class SerieResult {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(String totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public List<Search> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Search> episodes) {
        this.episodes = episodes;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
