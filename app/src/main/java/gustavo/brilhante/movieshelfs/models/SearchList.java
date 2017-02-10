package gustavo.brilhante.movieshelfs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gustavo on 04/02/17.
 */

public class SearchList implements Serializable{

    @SerializedName("Search")
    @Expose
    public List<Search> search = null;
    @SerializedName("totalResults")
    @Expose
    public String totalResults;
    @SerializedName("Response")
    @Expose
    public String response;

    public List<Search> getSearch() {
        return search;
    }

    public void setSearch(List<Search> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
