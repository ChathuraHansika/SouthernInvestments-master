package com.android.mlpj.southerninvestments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private List<CustomerDetails> mCustomersDetails;
    private Context mContext;
    private FragmentManager fragmentManager;
    private String phoneNum;

    public CustomerListAdapter(List<CustomerDetails> customerDetails, Context context, FragmentManager fragmentManager) {
        this.mCustomersDetails = customerDetails;
        this.mContext = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.customer_card, parent, false);
        return new ViewHolder(v);


    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //set status(active...)
        String status = mCustomersDetails.get(position).getStatus().toUpperCase();
        holder.mName.setText(mCustomersDetails.get(position).getName());
        holder.mStatus.setText(status);
        holder.mCustomerNo.setText(mCustomersDetails.get(position).getCustomerNo());
        holder.mPhoneNo.setText(mCustomersDetails.get(position).getPhoneNo());
        // change card's text view status's condition
        if(status.equals("ONGOING")){
            holder.mStatus.setTextColor(Color.parseColor("#009900"));
        }else if(status.equals("ACTIVE")){
            holder.mStatus.setTextColor(Color.parseColor("#3333ff"));
        }else {
           // holder.mName.setTextColor(Color.parseColor("#cc0000"));
            holder.mStatus.setTextColor(Color.parseColor("#cc0000"));
          //  holder.mCustomerNo.setTextColor(Color.parseColor("#cc0000"));
          //  holder.mPhoneNo.setTextColor(Color.parseColor("#cc0000"));
          //  holder.cardView.setCardBackgroundColor(Color.parseColor("#ffcccc"));

        }
        phoneNum =  mCustomersDetails.get(position).getPhoneNo().toString();
/*


        holder.popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_LONG).
                        show();
                InsertPaidAmountFragment insertPaidAmountFragment = new InsertPaidAmountFragment();
                //commentFragment.setArguments(bundle);

                insertPaidAmountFragment.show(fragmentManager, "InsertPaidAmountDialog");

            }
        });
*/

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +phoneNum ));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(callIntent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return  mCustomersDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      public   TextView mName;
      public   TextView mStatus;
      public   TextView mCustomerNo;
      public   TextView mPhoneNo;
      public   ImageButton call;
      public   CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            mName           = itemView.findViewById(R.id.tv_c_name);
            mStatus         = itemView.findViewById(R.id.tv_status);
            mCustomerNo     = itemView.findViewById(R.id.tv_c_no);
            mPhoneNo        = itemView.findViewById(R.id.tv_phoneNumber);
            call            = itemView.findViewById(R.id.btn_call);
            cardView        = itemView.findViewById(R.id.card);
        }
    }

    public void upDateList(List<CustomerDetails> list){
        mCustomersDetails = new ArrayList<>();
        mCustomersDetails.addAll(list);
        notifyDataSetChanged();
}
}
