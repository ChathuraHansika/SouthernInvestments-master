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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DueLoansFragment extends Fragment  {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DueLoansDetails> mDueLoansDetails;
    private DueLoansAdapter mDueLoansAdapter;
    private ApiInterface mApiInterface;
    private Context mContext;
    private SQLLiteHelper sqlLiteHelper;


    public DueLoansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_due_loans, container, false);
        mRecyclerView = view.findViewById(R.id.recycle_dueLoans_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setHasOptionsMenu(true);


        //retrieving due_loans data from sqlite
        sqlLiteHelper = new SQLLiteHelper(getContext());
        mDueLoansDetails = sqlLiteHelper.getDueLoans();
        FragmentManager fragmentManager = getFragmentManager();
        mDueLoansAdapter = new DueLoansAdapter(mDueLoansDetails, getActivity(), fragmentManager);
        mRecyclerView.setAdapter(mDueLoansAdapter);

        // chkStatus();


        return view;
    }

}
