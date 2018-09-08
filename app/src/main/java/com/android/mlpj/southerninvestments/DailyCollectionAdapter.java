package com.android.mlpj.southerninvestments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.mId.setText(mDailyCollectionDetails.get(position).getId());
        holder.mName.setText(mDailyCollectionDetails.get(position).getName());
        holder.mAmount.setText(mDailyCollectionDetails.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return mDailyCollectionDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public   TextView mId;
        public   TextView mName;
        public   TextView mAmount;


        public ViewHolder(View itemView) {
            super(itemView);

            mId = itemView.findViewById(R.id.dailySummary_id);
            mName= itemView.findViewById(R.id.dailySummary_name);
            mAmount = itemView.findViewById(R.id.dailySummary_amount);

        }
    }
}
