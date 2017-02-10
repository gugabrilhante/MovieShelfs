package gustavo.brilhante.movieshelfs.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gustavo.brilhante.movieshelfs.R;

/**
 * Created by Gustavo on 05/02/17.
 */

public class EmptyHolder extends RecyclerView.ViewHolder  {
    public EmptyHolder(View itemView) {
        super(itemView);
    }
    public void bind(){

    }

    public static EmptyHolder build(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_empty_holder, parent, false);
        EmptyHolder holder = new EmptyHolder(view);
        return holder;
    }

}
