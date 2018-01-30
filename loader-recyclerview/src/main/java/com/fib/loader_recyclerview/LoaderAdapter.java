package com.fib.loader_recyclerview;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by fibrahim on 1/25/18.
 */

public abstract class LoaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SwipeRefreshLayout mSwiper;
    protected ArrayList<Object> mData;


    private ProgressViewHolder mProgressViewHolder;
    private int mProgressColour = Color.BLUE;

    protected final int VIEW_ITEM_CELL = 1;
    protected final int VIEW_PROGRESS_CELL = 0;

    // The minimum amount of items to have below your current scroll position
    // before isLoadingMoreBusy more.
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;

    private boolean isLoadingMoreBusy = false, loadMoreCompleted = false;
    private LoaderRecyclerViewCallBacks mLoadingCallBacks;


    public LoaderAdapter(ArrayList<Object> data, SwipeRefreshLayout swiper) {
        mData = data;
        mSwiper = swiper;

        mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mLoadingCallBacks != null) {

                    //Reset flags
                    isLoadingMoreBusy = loadMoreCompleted = false;

                    mLoadingCallBacks.onRefresh();
                }
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!isLoadingMoreBusy && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something

                                if (mLoadingCallBacks != null) {
                                    mLoadingCallBacks.onLoadMore();
                                }

                                isLoadingMoreBusy = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position < mData.size() ? VIEW_ITEM_CELL : VIEW_PROGRESS_CELL;
    }

    public void setCallBacks(LoaderRecyclerViewCallBacks loadingCallBacks) {
        this.mLoadingCallBacks = loadingCallBacks;
    }

    public void setProgressCellColour(int color)
    {
        mProgressColour = color;
    }

    public void setPageLoaded() {
        isLoadingMoreBusy = false;
    }

    public void setLoadingFinished() {
        loadMoreCompleted = true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return loadMoreCompleted ? mData.size() : mData.size() + 1;
    }

    protected RecyclerView.ViewHolder getProgressView(ViewGroup parent) {
        if(mProgressViewHolder == null)
            mProgressViewHolder = new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_progress, parent, false));

        return mProgressViewHolder;
    }

    protected void setProgressCell(RecyclerView.ViewHolder holder) {
        ProgressViewHolder lProgressViewholder = ((ProgressViewHolder) holder);

        lProgressViewholder.mProgress.setIndeterminate(true);
        lProgressViewholder.mProgress.getIndeterminateDrawable().setColorFilter( mProgressColour, PorterDuff.Mode.SRC_IN);
    }
}
