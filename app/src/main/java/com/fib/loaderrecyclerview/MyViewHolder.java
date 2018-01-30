package com.fib.loaderrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fibrahim on 1/25/18.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView mtv;
    private TextView mHeading;

    public MyViewHolder(View cellView)
    {
        super(cellView);

        mtv = cellView.findViewById(R.id.cell_tv);
        mHeading = cellView.findViewById(R.id.cell_tv_heading);
    }

    public void setHeadingByPosition(int position)
    {
        mHeading.setText("Heading # " + position);
    }
}
