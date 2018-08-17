package com.android.mlpj.southerninvestments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DueLoansAdapter extends RecyclerView.Adapter<DueLoansAdapter.ViewHolder> {
    private List<DueLoansDetails> mDueLoans;
    private Context mContext;

    public DueLoansAdapter(List<DueLoansDetails> mDueLoans, Context mContext) {
        this.mDueLoans = mDueLoans;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.due_loans_card,parent,false);
        return new DueLoansAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Name.setText(mDueLoans.get(position).getName());
        holder.Email.setText(mDueLoans.get(position).getNICNumber());
        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_LONG).
                        show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDueLoans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public   TextView Name;
        public   TextView Email;
        public   CardView Card;

        public ViewHolder(View itemView) {
            super(itemView);

            Name              = itemView.findViewById(R.id.D_Name);
            Email             = itemView.findViewById(R.id.D_Id);
            Card              = itemView.findViewById(R.id.D_card);
        }
        }

//    public void upDateList(List<CustomerDetails> list){
//        mDueLoans = new ArrayList<>();
//        mDueLoans.addAll(list);
//        notifyDataSetChanged();
//    }
    }

