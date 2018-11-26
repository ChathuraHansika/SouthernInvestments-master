package com.android.mlpj.southerninvestments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class PocketMoneyFragment extends Fragment implements AdapterView.OnItemSelectedListener {
   private String item;
   private String amount;
   private Button addPocketMoney;
   private Spinner spinner;
   private EditText Etamount;

    public PocketMoneyFragment() {
        // Required empty public constructor
        //
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_pocket_money, container, false);
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        spinner = view.findViewById(R.id.spinner);
        addPocketMoney = view.findViewById(R.id.add_pocket_money);
        Etamount  = view.findViewById(R.id.input_amount);
        spinner.setOnItemSelectedListener(this);
        final UserLocalStore userLocalStore = new UserLocalStore(getActivity());

        amount = Etamount.getText().toString();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        addPocketMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               amount = Etamount.getText().toString();
             //  Toast.makeText(getContext(), item +" \n" +  Float.valueOf(Etamount.getText().toString()).floatValue()  +"\n" + userLocalStore.getUserDetails().getId()+"  d", Toast.LENGTH_SHORT).show();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://www.southernpropertydevelopers.com/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();

                ApiInterface client = retrofit.create(ApiInterface.class);
                Call<PocketMoneyResponse> call = client.addPocketMoney(Integer.toString(userLocalStore.getUserDetails().getId()),
                                                                 Etamount.getText().toString(),
                                                                 item.toString());

                call.enqueue(new Callback<PocketMoneyResponse>() {
                    @Override
                    public void onResponse(Call<PocketMoneyResponse> call, Response<PocketMoneyResponse> response) {
                        if(response.code() == 200){
                            try {
                                Toast.makeText(getContext(), "Success" +"\n" + response.body().getPocketMoney().getDescription() + response.code(), Toast.LENGTH_LONG).show();

                            }catch (NullPointerException ex){
                                Toast.makeText(getActivity(), "Null pointer Exception "+ ex.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }else{
                            Toast.makeText(getActivity(), "HTTP Error. Please Retry", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<PocketMoneyResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Fail"+ t, Toast.LENGTH_LONG).show();

                    }
                });


            }
        });

        return view;
    }


    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
