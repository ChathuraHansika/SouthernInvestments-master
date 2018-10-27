package com.android.mlpj.southerninvestments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyCollectionsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DailyCollectionDetails> mDailyCollectionDetails;
    private DailyCollectionAdapter mDailyCollectionAdapter;
    private ApiInterface mApiInterface;
    private Context mContext;
    private SQLLiteHelper sqlLiteHelper;
    private TextView mTotalAmount;

public DailyCollectionsFragment(){

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setTitle("Daily Summary");



        View view = inflater.inflate(R.layout.fragment_daily_collections, container, false);
        mRecyclerView = view.findViewById(R.id.recycle_customer_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setHasOptionsMenu(true);

        mTotalAmount = view.findViewById(R.id.dailySummary_total);

        //retrieving due_loans data from sqlite
        sqlLiteHelper = new SQLLiteHelper(getContext());
        mDailyCollectionDetails = sqlLiteHelper.getDailyCollection();
        FragmentManager fragmentManager = getFragmentManager();
        mDailyCollectionAdapter = new DailyCollectionAdapter(mDailyCollectionDetails, getActivity(), fragmentManager);
        mRecyclerView.setAdapter(mDailyCollectionAdapter);
       DailyCollectionDetails dailyCollectionDetails = new DailyCollectionDetails();

      mTotalAmount.setText(String.valueOf( dailyCollectionDetails.getTotalAmount()));



        return view;
    }

}
