package com.android.mlpj.southerninvestments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    CardView mCustomerList;
    CardView mDailySummary;
    CardView mLoanRePayment;
    CardView mDueLoans;
    CardView mLoanIssue;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private UserLocalStore mUserLocalStore;
    private String mPassword, mEmail;
    private ProgressDialog mProgressDialog;
    private SQLLiteHelper sqlLiteHelper;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ((MainActivity)getActivity()).setTitle("Home");

        mCustomerList  = view.findViewById(R.id.customerList);
        mDailySummary  = view.findViewById(R.id.dailySummary);
        mLoanRePayment = view.findViewById(R.id.loanRepayment);
        mDueLoans      = view.findViewById(R.id.dueLoans);
        mLoanIssue     = view.findViewById(R.id.loanIssue);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);

        mUserLocalStore = new UserLocalStore(getContext());
        mEmail = mUserLocalStore.getUserDetails().getEmail();
        mPassword = mUserLocalStore.getPassword();

        sqlLiteHelper = new SQLLiteHelper(getContext());

        mCustomerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CustomerListFragment();
                FragmentManager fragmentManager =getFragmentManager();

                //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        mDueLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DueLoansFragment();
                FragmentManager fragmentManager =getFragmentManager();

                //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        mDailySummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DailyCollectionsFragment();
                FragmentManager fragmentManager =getFragmentManager();

                //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });
        mLoanIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new LoanIssueNicSearchFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                Toast.makeText(getContext(), "Still we are working on this", Toast.LENGTH_LONG).show();
            }
        });
        mLoanRePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EnterCustomerNumberFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        clearDB();
                        syncDB();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return view;
    }

    public void clearDB() {
        mUserLocalStore.clearUser();
        mUserLocalStore.setUserLoggedIn(false, "");
        sqlLiteHelper.removeAll();
    }

    public void syncDB() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://www.southernpropertydevelopers.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<LoginResultPOJO> call = client.login(mEmail, mPassword);

        call.enqueue(new Callback<LoginResultPOJO>() {
            @Override
            public void onResponse(Call<LoginResultPOJO> call, Response<LoginResultPOJO> response) {
                if(response.code() == 200){
                    try{
                        LoginResultPOJO loginResultPOJO = response.body();
                        if(!loginResultPOJO.isError()){
                            //inserting customer tuples
                            for(int i = 0; i < loginResultPOJO.getCustomers().size();i++){
                                long result = sqlLiteHelper.insertCustomer(loginResultPOJO.getCustomers().get(i));
                            }

                            //inserting loan tuples
                            for(int i = 0; i < loginResultPOJO.getLoans().size();i++){
                                long result = sqlLiteHelper.insertLoans(loginResultPOJO.getLoans().get(i));
                            }

                            //insert repayments tuples
                            for(int i = 0; i < loginResultPOJO.getRepayments().size();i++){
                                long result = sqlLiteHelper.insertRepayments(loginResultPOJO.getRepayments().get(i));
                            }


                            //saving salesrep details in shared preferences
                            mUserLocalStore.setUserLoggedIn(true, mPassword);
                            mUserLocalStore.setUserDetails(loginResultPOJO.getUser());
                        }

                    } catch (NullPointerException e){
                        Toast.makeText(getContext(), "Null pointer Exception "+ e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                } else if (response.code() == 401){
                    Toast.makeText(getContext(), "Sync failed. Please logout and login again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Sync failed. Please logout and login again. HTTP Error code" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResultPOJO> call, Throwable t) {
                Toast.makeText(getContext(), "Connection Failure " + t.getMessage(), Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        });
    }
}
