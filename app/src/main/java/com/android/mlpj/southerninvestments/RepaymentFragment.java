package com.android.mlpj.southerninvestments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.text.Layout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    View view;

    private TextView mTvCustomerNo, mTvName, mTvNic, mTvRemainingAmount, mTvInstallmentNo, mEditing;
    private EditText mEtRepaymentAmount, mEtChequeNo;
    private Switch mSwitchCheque;
    private Button mBtnPay;
    private ProgressDialog mProgressDialog;

    private boolean isCheque, isEdit;
    private int loanId, totalNoOfInstallments, repaymentId;
    private String currentDateString;
    private SQLLiteHelper sqlLiteHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_repayment, container, false);

        ((MainActivity)getActivity()).setTitle("Make installment here");


        mTvCustomerNo = view.findViewById(R.id.tv_customer_number);
        mTvName = view.findViewById(R.id.tv_name);
        mTvNic = view.findViewById(R.id.tv_nic);
        mTvRemainingAmount = view.findViewById(R.id.tv_remaining_amount);
        mTvInstallmentNo = view.findViewById(R.id.tv_installment_no);
        mEtRepaymentAmount = view.findViewById(R.id.et_repayment_amount);
        //mEtChequeNo = view.findViewById(R.id.et_cheque_number);
        //mSwitchCheque = view.findViewById(R.id.switch_cheque);
        mBtnPay = view.findViewById(R.id.btn_pay_amount);
        mEditing = view.findViewById(R.id.tv_editing);
        mEditing.setVisibility(View.INVISIBLE);


        mEtRepaymentAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,2)});

        /*mEtChequeNo.setEnabled(false);
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
        });*/

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
        //Toast.makeText(getContext(), "" + customerNo, Toast.LENGTH_LONG).show();

        //get current date in required format
        Date currentDate = Calendar.getInstance().getTime();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        currentDateString = simpleDateFormat.format(new Date());

        Toast.makeText(getContext(), currentDateString, Toast.LENGTH_SHORT).show();

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
        String repaymentDate = resRepayment.getString(4);
        float repaymentAmount = resRepayment.getFloat(5);
        repaymentId = resRepayment.getInt(6);

        //Toast.makeText(getContext(), "" + remainingAmount + " " + installmentCount + " " + loanId , Toast.LENGTH_LONG).show();

        mTvRemainingAmount.setText(Float.toString(remainingAmount));
        mTvInstallmentNo.setText(Integer.toString(totalNoOfInstallments - installmentCount));

        //check whether edit or new payment
        isEdit = repaymentDate.equals(currentDateString);
        if(isEdit){
            //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            mBtnPay.setText("Save edit");
            view.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            mEditing.setVisibility(View.VISIBLE);
            mEtRepaymentAmount.setText(Float.toString(repaymentAmount));
        }
    }

    public void makeRequest(){
        if(isEdit){
            editRepayment();
        }else{
            makeRepaymentForFirstTime();
        }

    }


    public void makeRepaymentForFirstTime(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Processing...");
        mProgressDialog.setMessage("Making installment");
        mProgressDialog.show();

        float cashAmount, bankAmount;
        String chequeNo;
        /*if(!mSwitchCheque.isChecked()){
            cashAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
            bankAmount = 0;
            chequeNo = "0";
        }else{
            cashAmount = 0;
            bankAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
            chequeNo = mEtChequeNo.getText().toString();
        }*/

        cashAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
        bankAmount = 0;
        chequeNo = "";

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
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Failed making installment. Do you want to re try?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    makeRepaymentForFirstTime();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

            @Override
            public void onFailure(Call<RepaymentDoneResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Exception " + t.getMessage(), Toast.LENGTH_LONG).show();
                mBtnPay.setEnabled(true);
                mProgressDialog.dismiss();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Failed making installment. Do you want to re try?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                makeRepaymentForFirstTime();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    public void editRepayment(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Processing...");
        mProgressDialog.setMessage("Editing installment");
        mProgressDialog.show();

        float cashAmount, bankAmount;
        String chequeNo;
        /*if(!mSwitchCheque.isChecked()){
            cashAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
            bankAmount = 0;
            chequeNo = "0";
        }else{
            cashAmount = 0;
            bankAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
            chequeNo = mEtChequeNo.getText().toString();
        }*/

        cashAmount = Float.parseFloat(mEtRepaymentAmount.getText().toString());
        bankAmount = 0;
        chequeNo = "";

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://www.southernpropertydevelopers.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<RepaymentDoneResponse> call = client.editRepayment(repaymentId, cashAmount,currentDateString, bankAmount, chequeNo);

        call.enqueue(new Callback<RepaymentDoneResponse>() {
            @Override
            public void onResponse(Call<RepaymentDoneResponse> call, Response<RepaymentDoneResponse> response) {
                if(response.code() == 200){
                    Toast.makeText(getContext(), "Successfully edited the installment ", Toast.LENGTH_LONG).show();
                    Repayment newRepayment = response.body().getRepayments();
                    sqlLiteHelper.deleteReapymentById(repaymentId);
                    sqlLiteHelper.insertRepayments(newRepayment);
                    mProgressDialog.dismiss();

                    Fragment fragment = new EnterCustomerNumberFragment();
                    FragmentManager fragmentManager =getFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
                }else{
                    Toast.makeText(getContext(), "Failed to edit installment " + response.code(), Toast.LENGTH_LONG).show();
                    mBtnPay.setEnabled(true);
                    mProgressDialog.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Failed editing installment. Do you want to re try?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    editRepayment();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

            @Override
            public void onFailure(Call<RepaymentDoneResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Exception " + t.getMessage(), Toast.LENGTH_LONG).show();
                mBtnPay.setEnabled(true);
                mProgressDialog.dismiss();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Failed editing installment. Do you want to re try?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                editRepayment();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
}
