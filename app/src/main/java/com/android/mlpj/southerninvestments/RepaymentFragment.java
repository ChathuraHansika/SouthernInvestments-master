package com.android.mlpj.southerninvestments;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RepaymentFragment extends Fragment {

    private TextView mTvCustomerNo, mTvName, mTvNic, mTvRemainingAmount, mTvInstallmentNo;
    private EditText mEtRepaymentAmount, mEtChequeNo;
    private Switch mSwitchCheque;
    private Button mBtnPay;
    private ProgressDialog mProgressDialog;

    private boolean isCheque;
    private int loanId, totalNoOfInstallments;
    private SQLLiteHelper sqlLiteHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repayment, container, false);

        ((MainActivity)getActivity()).setTitle("Make installment here");


        mTvCustomerNo = view.findViewById(R.id.tv_customer_number);
        mTvName = view.findViewById(R.id.tv_name);
        mTvNic = view.findViewById(R.id.tv_nic);
        mTvRemainingAmount = view.findViewById(R.id.tv_remaining_amount);
        mTvInstallmentNo = view.findViewById(R.id.tv_installment_no);
        mEtRepaymentAmount = view.findViewById(R.id.et_repayment_amount);
        mEtChequeNo = view.findViewById(R.id.et_cheque_number);
        mSwitchCheque = view.findViewById(R.id.switch_cheque);
        mBtnPay = view.findViewById(R.id.btn_pay_amount);

        mEtRepaymentAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,2)});

        mEtChequeNo.setEnabled(false);
        mSwitchCheque.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEtChequeNo.setEnabled(true);
                    isCheque = true;
                }else{
                    mEtChequeNo.setEnabled(false);
                    isCheque = false;
                }
            }
        });

        updateView();

        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnPay.setEnabled(false);
                makeRequest();
            }
        });

        return view;
    }

    public void updateView(){
        sqlLiteHelper = new SQLLiteHelper(getContext());

        int customerNo = getArguments().getInt("CUSTOMER_NO");
        Toast.makeText(getContext(), "" + customerNo, Toast.LENGTH_LONG).show();

        Cursor res = sqlLiteHelper.getCustomerByNo(customerNo);
        mTvCustomerNo.setText(res.getString(1));
        mTvName.setText(res.getString(2));
        mTvNic.setText(res.getString(4));



        int customerId = res.getInt(0);
        Cursor resRepayment = sqlLiteHelper.getRepaymentByCustomerId(customerId);
        float remainingAmount = resRepayment.getFloat(0);
        int installmentCount = resRepayment.getInt(1);
        loanId = resRepayment.getInt(2);
        totalNoOfInstallments = resRepayment.getInt(3);
        Toast.makeText(getContext(), "" + remainingAmount + " " + installmentCount + " " + loanId , Toast.LENGTH_LONG).show();

        mTvRemainingAmount.setText(Float.toString(remainingAmount));
        mTvInstallmentNo.setText(Integer.toString(totalNoOfInstallments - installmentCount));

    }

    public void makeRequest(){

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Processing...");
        mProgressDialog.setMessage("Making installment");
        mProgressDialog.show();

        float cashAmount, bankAmount;
        String chequeNo;
        if(!mSwitchCheque.isChecked()){
            cashAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
            bankAmount = 0;
            chequeNo = "0";
        }else{
            cashAmount = 0;
            bankAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
            chequeNo = mEtChequeNo.getText().toString();
        }


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://www.southernpropertydevelopers.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<RepaymentDoneResponse> call = client.makeRepayment(loanId, cashAmount, bankAmount, chequeNo);

        call.enqueue(new Callback<RepaymentDoneResponse>() {
            @Override
            public void onResponse(Call<RepaymentDoneResponse> call, Response<RepaymentDoneResponse> response) {
                if(response.code() == 200){
                    Toast.makeText(getContext(), "Successfully entered the installment ", Toast.LENGTH_LONG).show();
                    Repayment newRepayment = response.body().getRepayments();
                    sqlLiteHelper.insertRepayments(newRepayment);
                    mProgressDialog.dismiss();

                    Fragment fragment = new EnterCustomerNumberFragment();
                    FragmentManager fragmentManager =getFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
                }else{
                    Toast.makeText(getContext(), "Failed to make installment", Toast.LENGTH_LONG).show();
                    mBtnPay.setEnabled(true);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RepaymentDoneResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Exception " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
