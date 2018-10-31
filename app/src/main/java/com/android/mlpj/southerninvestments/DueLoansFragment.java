package com.android.mlpj.southerninvestments;


import android.content.Context;
import android.os.Bundle;
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
public class DueLoansFragment extends Fragment  implements SearchView.OnQueryTextListener ,MenuItem.OnActionExpandListener  {

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

        ((MainActivity)getActivity()).setTitle("Loan Progress");

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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        final   MenuItem searchItem = menu.findItem(R.id.action_search);
        final   SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search...");
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
                final List<DueLoansDetails> filtermodelList = filter(mDueLoansDetails,newText);
                mDueLoansAdapter.upDateList(filtermodelList);
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
    private List<DueLoansDetails> filter(List<DueLoansDetails> cls, String input){
        input = input.toLowerCase();
        final List<DueLoansDetails> filtredModel = new ArrayList<>();
        for(DueLoansDetails model : cls){
            final String NIC = model.getNIC().toLowerCase();
            final String Name = model.getName().toLowerCase();
            final String CNum = model.getId().toLowerCase();
            if(NIC.startsWith(input)){
                filtredModel.add(model);
            }else if(Name.startsWith(input)){
                filtredModel.add(model);
            }else if(CNum.startsWith(input)){
                filtredModel.add(model);
            }
        }
        return filtredModel;
    }
}
