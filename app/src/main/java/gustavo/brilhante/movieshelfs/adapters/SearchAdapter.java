package gustavo.brilhante.movieshelfs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import gustavo.brilhante.movieshelfs.SearchListener;
import gustavo.brilhante.movieshelfs.adapters.holders.EmptyHolder;
import gustavo.brilhante.movieshelfs.adapters.holders.SearchHolder;
import gustavo.brilhante.movieshelfs.models.Search;

/**
 * Created by Gustavo on 04/02/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Search> searchArrayList;
    SearchListener listener;

    int extraCell = 1;

    public static int HEADER_HOLDER = 1;
    public static int SEARCH_HOLDER = 2;
    public static int EMPTY_HOLDER = 3;


    public SearchAdapter(Context context, ArrayList<Search> searchArrayList) {
        this.context = context;
        this.searchArrayList = searchArrayList;
    }

    public void setListener(SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==SEARCH_HOLDER) return SearchHolder.build(parent);
        else return EmptyHolder.build(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==SEARCH_HOLDER) {
            Search search = searchArrayList.get(position);
            SearchHolder searchHolder = (SearchHolder) holder;
            searchHolder.bind(search);
            searchHolder.setListener(this.listener);
        }else{
            EmptyHolder emptyHolder = (EmptyHolder) holder;
            emptyHolder.bind();
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position == (searchArrayList.size()+extraCell-1) ){
            return EMPTY_HOLDER;
        }else{
            return SEARCH_HOLDER;
        }

    }

    @Override
    public int getItemCount() {
        return searchArrayList.size()+extraCell;
    }


}
