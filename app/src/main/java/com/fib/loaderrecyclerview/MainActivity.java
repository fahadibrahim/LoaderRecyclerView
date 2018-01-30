package com.fib.loaderrecyclerview;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.fib.loader_recyclerview.LoaderRecyclerViewCallBacks;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRv;
    SwipeRefreshLayout mSwiper;
    ArrayList<Object> mGData;
    MyLocalAdapter mMyAdapter;

    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGData = new ArrayList<>();
        handler = new Handler();

        loadData();

        mRv = findViewById(R.id.my_recycler_view);
        mSwiper = findViewById(R.id.my_swipe_view);

        mSwiper.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.custom_gray));

        mSwiper.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        mMyAdapter = new MyLocalAdapter(mGData, mSwiper);

        mMyAdapter.setProgressCellColour(R.color.custom_gray);

        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.setAdapter(mMyAdapter);

        mMyAdapter.setCallBacks(new LoaderRecyclerViewCallBacks() {
            @Override
            public void onLoadMore() {
                loadMore();
            }

            @Override
            public void onRefresh() {
                localRefresh();
            }
        });

    }

    private void loadMore() {
        Toast.makeText(MainActivity.this, "Load More called!", Toast.LENGTH_SHORT).show();
        //add null , so the adapter will check view_type and show progress bar at bottom
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //   remove progress item
                //   add items one by one
                int start = mGData.size();
                int end = start + 20;

                for (int i = start + 1; i <= end; i++) {
                    mGData.add("New USer ");
                    mMyAdapter.notifyItemInserted(mGData.size());
                }
                mMyAdapter.setPageLoaded();


                //mMyAdapter.setLoadingFinished();
                //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    private void localRefresh() {

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mGData.clear();
                for (int i = 0; i < 2; i++)
                {
                    mGData.add("Default Description " + i);
                }

                //mGData.add(0, "New object " + mGData.size() );

                mMyAdapter.notifyDataSetChanged();

                mSwiper.setRefreshing(false);
            }
        }, 3000);
    }

    // load initial data
    private void loadData() {

        for (int i = 0; i < 2; i++)
        {
            mGData.add("Default Description " + i);
        }
    }
}
