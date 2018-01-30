package com.fib.loaderrecyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fib.loader_recyclerview.LoaderAdapter;
import com.fib.loader_recyclerview.ProgressViewHolder;

import java.util.ArrayList;

/**
 * Created by fibrahim on 1/30/18.
 */

public class MyLocalAdapter extends LoaderAdapter {

    public MyLocalAdapter(ArrayList<Object> data, SwipeRefreshLayout swiper) {
        super(data, swiper);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProgressViewHolder)
            super.setProgressCell(holder);

        else if (holder instanceof MyViewHolder) {

            MyViewHolder lMyViewholder = ((MyViewHolder)holder);
            lMyViewholder.mtv.setText((String) mData.get(position));
            lMyViewholder.setHeadingByPosition(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;

        if (viewType == super.VIEW_PROGRESS_CELL)
        {
            vh = super.getProgressView(parent);
        }
        else {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item, parent, false);
            vh = new MyViewHolder(v);
        }
        return vh;
    }
}
