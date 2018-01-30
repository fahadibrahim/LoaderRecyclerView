package com.fib.loader_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by fibrahim on 1/25/18.
 */

public class ProgressViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar mProgress;

    public ProgressViewHolder(View itemView) {
        super(itemView);

        mProgress = itemView.findViewById(R.id.progressBar1);
    }
}
