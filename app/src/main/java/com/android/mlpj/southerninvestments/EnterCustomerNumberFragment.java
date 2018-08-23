package com.android.mlpj.southerninvestments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnterCustomerNumberFragment extends Fragment {

    private EditText mEtInputCustomerNumber;
    private Button mBtnAddCustomerNumber;
    private SQLLiteHelper sqlLiteHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_customer_number, container, false);

        mEtInputCustomerNumber = view.findViewById(R.id.input_customer_number);
        mBtnAddCustomerNumber = view.findViewById(R.id.add_customer_number);
        sqlLiteHelper = new SQLLiteHelper(getContext());

        mBtnAddCustomerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEtInputCustomerNumber.getText().toString();
                if(!input.equals("")){
                    int customerNo = Integer.parseInt(input);
                    Cursor res = sqlLiteHelper.getCustomerByNo(customerNo);
                    if(res.isAfterLast() == false){
                        String status = res.getString(6);
                        Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
                        if(status.equals("ongoing")){
                            Bundle bundle = new Bundle();
                            bundle.putInt("CUSTOMER_NO", customerNo);

                            Fragment fragment = new RepaymentFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager =getFragmentManager();

                            //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
                        }else if(status.equals("deactive")){
                            Toast.makeText(getContext(), "Customer black listed", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(), "No ongoing loan for this customer", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "No such customer", Toast.LENGTH_LONG).show();
                    }
                }else{
                    mEtInputCustomerNumber.setError("Enter customer no");
                }

            }
        });
        return view;
    }

}
