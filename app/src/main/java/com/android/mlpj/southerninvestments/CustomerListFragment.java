package com.android.mlpj.southerninvestments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment implements SearchView.OnQueryTextListener ,MenuItem.OnActionExpandListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CustomerDetails> mCustomersDetails;
    private CustomerListAdapter mCustomerListAdapter;
    private ApiInterface mApiInterface;
    private Context mContext;
    private SQLLiteHelper sqlLiteHelper;


    public CustomerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setTitle("Customer Details");


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customer_list, container, false);
        mRecyclerView = v.findViewById(R.id.recycle_customer_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setHasOptionsMenu(true);

        //retrieving customer data from sqlite
        sqlLiteHelper = new SQLLiteHelper(getContext());
        mCustomersDetails = sqlLiteHelper.getAllCustomers();
        FragmentManager fragmentManager = getFragmentManager();
        mCustomerListAdapter = new CustomerListAdapter(mCustomersDetails, getActivity(), fragmentManager);
        mRecyclerView.setAdapter(mCustomerListAdapter);


        // chkStatus();


        return v;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        final   MenuItem searchItem = menu.findItem(R.id.action_search);
        final   SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<CustomerDetails> filtermodelList = filter(mCustomersDetails,newText);
                mCustomerListAdapter.upDateList(filtermodelList);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
    private List<CustomerDetails> filter(List<CustomerDetails> cls, String input){
        input = input.toLowerCase();
        final List<CustomerDetails> filtredModel = new ArrayList<>();
        for(CustomerDetails model : cls){
            final String name = model.getName().toLowerCase();
            final String customerNumber = model.getCustomerNo().toLowerCase();
            if(name.contains(input)){
                filtredModel.add(model);
            }else if(customerNumber.contains(input)){
                filtredModel.add(model);
            }
        }
        return filtredModel;
    }
}





