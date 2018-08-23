package com.android.mlpj.southerninvestments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private UserLocalStore userLocalStore;

    private CircleImageView profImage;
    private TextView tvName, tvRoute,tvNic, tvTelephone, tvEmail, tvAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        profImage = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tv_sales_rep_name);
        tvRoute = view.findViewById(R.id.tv_route);
        tvNic = view.findViewById(R.id.tv_nic);
        tvTelephone = view.findViewById(R.id.tv_telephone);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAddress = view.findViewById(R.id.tv_address);

        userLocalStore = new UserLocalStore(getContext());
        SalesRep currUser = userLocalStore.getUserDetails();

        //set prof pic here

        tvName.setText(currUser.getName());
        //tvRoute.setText();
        tvNic.setText(currUser.getNicNo());
        //tvTelephone.setText(currUser);
        tvEmail.setText(currUser.getEmail());
        tvAddress.setText(currUser.getAddLine1() + ", " + currUser.getAddLine2());

        return view;
    }

}
