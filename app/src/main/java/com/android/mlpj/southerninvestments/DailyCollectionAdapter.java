package com.android.mlpj.southerninvestments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class DailyCollectionAdapter extends RecyclerView.Adapter<DailyCollectionAdapter.ViewHolder> {

    private List<DailyCollectionDetails> mDailyCollectionDetails;
    private Context mContext;
    private FragmentManager fragmentManager;

    public DailyCollectionAdapter(List<DailyCollectionDetails> mDailyCollectionDetails, Context mContext, FragmentManager fragmentManager) {
        this.mDailyCollectionDetails = mDailyCollectionDetails;
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.daily_collection_card, parent, false);
        return new DailyCollectionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
