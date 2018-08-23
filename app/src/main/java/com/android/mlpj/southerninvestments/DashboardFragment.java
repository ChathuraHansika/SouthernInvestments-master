package com.android.mlpj.southerninvestments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    CardView mCustomerList;
    CardView mDailySummary;
    CardView mLoanRePayment;
    CardView mDueLoans;
    CardView mLoanIssue;


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
                Fragment fragment = new LoanIssueNicSearchFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).setBackPressedOnce();
    }
}
