package com.android.mlpj.southerninvestments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RepaymentFragment extends Fragment {

    View view;

    private TextView mTvCustomerNo, mTvName, mTvNic, mTvRemainingAmount, mTvInstallmentNo, mEditing;
    private EditText mEtRepaymentAmount, mEtChequeNo;
    private Switch mSwitchCheque;
    private Button mBtnPay, mBtnPrint;
    private ProgressDialog mProgressDialog;

    private boolean isCheque, isRepaymentDone, iseditEnabled;
    private int loanId, totalNoOfInstallments, repaymentId, mInstallmentCount;
    private String currentDateString;
    private SQLLiteHelper sqlLiteHelper;
    private BluetoothSocket mBluetoothSocket;

    private String mCustomerName, mCustomerNo, mLoanStartDate, mLoanEndDate;
    private float paidAmount, remainingAmount, mLoanAmount, mInstallmentAmount;
    private UserLocalStore userLocalStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_repayment, container, false);

        ((MainActivity) getActivity()).setTitle("Make installment here");


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
        mBtnPrint = view.findViewById(R.id.btn_print);

        mEtRepaymentAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});

        userLocalStore = new UserLocalStore(getContext());
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

        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnPay.setEnabled(false);
                makeRequest();
            }
        });

        mBtnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepaymentDone && iseditEnabled) {
                    iseditEnabled = false;
                    updateView();
                }else if(isRepaymentDone && !iseditEnabled){
                    Thread t = new Thread() {
                        public void run() {
                            try {
                                OutputStream os = mBluetoothSocket
                                        .getOutputStream();
                                byte[] cc = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
                                byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
                                byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
                                byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text

                                byte[] PRINT_ALIGN_LEFT = new byte[] { 0x1b, 'a', 0x00 };
                                byte[] PRINT_ALIGN_RIGHT = new byte[] { 0x1b, 'a', 0x02 };
                                byte[] PRINT_ALIGN_CENTER = new byte[] { 0x1b, 'a', 0x01 };

                                os.write(bb3);
                                os.write(PRINT_ALIGN_CENTER);
                                String bill = "Southern Investments\n";
                                os.write(bill.getBytes());
                                os.write(cc);
                                String address = "0773906738\n";
                                os.write(address.getBytes());
                                os.write(PRINT_ALIGN_LEFT);
                                String separator = "-------------------------------\n";
                                os.write(separator.getBytes());
                                String customerNo = "Customer No : " + mCustomerNo + "\n";
                                os.write(customerNo.getBytes());
                                String name = "Customer : " + mCustomerName + "\n";
                                os.write(name.getBytes());  //todo: if name is lengthy
                                String loanAmount = String.format("Loan amt : Rs.%,.2f\n", mLoanAmount);
                                os.write(loanAmount.getBytes());
                                String loanStartDate = String.format("Loan started at : " + mLoanStartDate + "\n");
                                os.write(loanStartDate.getBytes());
                                String loanEndDate = String.format("Loan end date : " + mLoanEndDate + "\n");
                                os.write(loanEndDate.getBytes());
                                String installmentCount = "Installment count : " + mInstallmentCount + "\n";
                                os.write(installmentCount.getBytes());
                                String installmentAmount = String.format("Installment amt : Rs.%,.2f\n", mInstallmentAmount);
                                os.write(installmentAmount.getBytes());
                                String paid = String.format("Installment paid : Rs.%,.2f\n", paidAmount);
                                os.write(paid.getBytes());
                                String remaining = String.format("Due amt : Rs.%,.2f\n", remainingAmount);
                                os.write(remaining.getBytes());
                                os.write(separator.getBytes());
                                String salesRepName = "Salesman name : " + userLocalStore.getUserDetails().getName() + "\n";
                                os.write(salesRepName.getBytes());
                                os.write(bb2);
                                os.write(PRINT_ALIGN_CENTER);
                                String thankYou = "Thank You\n\n\n\n";
                                os.write(thankYou.getBytes());
                            } catch (Exception e) {
                                Log.e("MainActivity", "Exe ", e);
                            }
                        }
                    };
                    t.start();
                }
            }
        });
        updateView();

        return view;
    }

    @Override
    public void onResume() {
        //get BT socket
        super.onResume();
        mBluetoothSocket = ((BaseApplication) getContext().getApplicationContext()).getmBluetoothSocket();
    }

    public void updateView() {
        sqlLiteHelper = new SQLLiteHelper(getContext());

        int customerNo = getArguments().getInt("CUSTOMER_NO");
        mCustomerNo = Integer.toString(customerNo);
        //Toast.makeText(getContext(), "" + customerNo, Toast.LENGTH_LONG).show();

        //get current date in required format
        Date currentDate = Calendar.getInstance().getTime();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        currentDateString = simpleDateFormat.format(new Date());

        Toast.makeText(getContext(), currentDateString, Toast.LENGTH_SHORT).show();

        Cursor res = sqlLiteHelper.getCustomerByNo(customerNo);
        mTvCustomerNo.setText(res.getString(1));
        mCustomerName = res.getString(2);
        mTvName.setText(mCustomerName);
        mTvNic.setText(res.getString(4));

        int customerId = res.getInt(0);
        Cursor resRepayment = sqlLiteHelper.getRepaymentByCustomerId(customerId);
        remainingAmount = resRepayment.getFloat(0);

        int installmentCount = resRepayment.getInt(1);
        loanId = resRepayment.getInt(2);
        totalNoOfInstallments = resRepayment.getInt(3);
        String repaymentDate = resRepayment.getString(4);
        paidAmount = resRepayment.getFloat(5);
        repaymentId = resRepayment.getInt(6);
        mLoanAmount = resRepayment.getFloat(7);
        mLoanStartDate = resRepayment.getString(8);
        mLoanEndDate = resRepayment.getString(9);
        mInstallmentAmount = resRepayment.getFloat(10);
        //Toast.makeText(getContext(), "" + remainingAmount + " " + installmentCount + " " + loanId , Toast.LENGTH_LONG).show();

        mTvRemainingAmount.setText(Float.toString(remainingAmount));
        mInstallmentCount = totalNoOfInstallments - installmentCount;
        mTvInstallmentNo.setText(Integer.toString(mInstallmentCount));

        //check whether edit or new payment
        isRepaymentDone = repaymentDate.equals(currentDateString);
        Toast.makeText(getContext(), repaymentDate, Toast.LENGTH_LONG).show();
        if (!isRepaymentDone) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            mEtRepaymentAmount.requestFocus();
            mBtnPrint.setVisibility(View.INVISIBLE);
            mBtnPay.setText("Pay");
            mBtnPay.setEnabled(true);
            view.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
            mEditing.setVisibility(View.INVISIBLE);
            mEtRepaymentAmount.setText("");
            mEtRepaymentAmount.setEnabled(true);
        }
        if (isRepaymentDone && iseditEnabled) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            mEtRepaymentAmount.requestFocus();
            //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            mBtnPay.setText("Save edit");
            mBtnPay.setEnabled(true);
            mBtnPrint.setText("Cancel Edit");
            mBtnPrint.setVisibility(View.VISIBLE);
            view.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            mEditing.setText("You are going to edit the already entered installment");
            mEditing.setVisibility(View.VISIBLE);
            mEtRepaymentAmount.setText(Float.toString(paidAmount));
            mEtRepaymentAmount.setEnabled(true);
        }
        if (isRepaymentDone && !iseditEnabled) {
            //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            mBtnPay.setText("Edit Installment");
            mBtnPay.setEnabled(true);
            mBtnPrint.setVisibility(View.VISIBLE);
            mBtnPrint.setText("Print");
            view.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            mEditing.setText("You have entered the installment");
            mEditing.setVisibility(View.VISIBLE);
            mEtRepaymentAmount.setText(Float.toString(paidAmount));
            mEtRepaymentAmount.setEnabled(false);
        }
    }

    public void makeRequest() {
        if (isRepaymentDone && iseditEnabled) {
            editRepayment();
        } else if (isRepaymentDone && !iseditEnabled) {
            iseditEnabled = true;
            updateView();
        } else if (!isRepaymentDone) {
            makeRepaymentForFirstTime();
        }

    }


    public void makeRepaymentForFirstTime() {
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

        String stringCashAmount = mEtRepaymentAmount.getText().toString();
        if (!stringCashAmount.equals("")) {
            cashAmount = Float.parseFloat(stringCashAmount);
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
                    if (response.code() == 200) {
                        if (response.body().isError()){
                            Toast.makeText(getContext(), "There is already an installment made today. Please update phone date and sync the app", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Successfully entered the installment ", Toast.LENGTH_LONG).show();
                            Repayment newRepayment = response.body().getRepayments();
                            sqlLiteHelper.insertRepayments(newRepayment);
                            updateView();
                        }
                        mProgressDialog.dismiss();

//                        Fragment fragment = new EnterCustomerNumberFragment();
//                        FragmentManager fragmentManager =getFragmentManager();
//                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
                    } else {
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
        } else {
            mEtRepaymentAmount.setError("Please enter installment amount");
        }
    }

    public void editRepayment() {
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

        String stringCashAmount = mEtRepaymentAmount.getText().toString();
        if (!stringCashAmount.equals("")) {
            cashAmount = Float.parseFloat(stringCashAmount);
            bankAmount = 0;
            chequeNo = "";

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://www.southernpropertydevelopers.com/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiInterface client = retrofit.create(ApiInterface.class);
            Call<RepaymentDoneResponse> call = client.editRepayment(repaymentId, cashAmount, currentDateString, bankAmount, chequeNo);

            call.enqueue(new Callback<RepaymentDoneResponse>() {
                @Override
                public void onResponse(Call<RepaymentDoneResponse> call, Response<RepaymentDoneResponse> response) {
                    if (response.code() == 200) {
                        if(response.body().isError()){
                            Toast.makeText(getContext(), "You can't edit a past installment. Please check your phone date ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Successfully edited the installment ", Toast.LENGTH_LONG).show();
                            Repayment newRepayment = response.body().getRepayments();
                            sqlLiteHelper.deleteReapymentById(repaymentId);
                            sqlLiteHelper.insertRepayments(newRepayment);
                            iseditEnabled = false;
                            updateView();
                        }
                        mProgressDialog.dismiss();

//                        Fragment fragment = new EnterCustomerNumberFragment();
//                        FragmentManager fragmentManager =getFragmentManager();
//                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
                    } else {
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
        } else {
            mEtRepaymentAmount.setError("Please enter installment amount");
        }
    }

}