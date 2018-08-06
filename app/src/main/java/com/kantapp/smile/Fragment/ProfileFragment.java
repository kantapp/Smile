package com.kantapp.smile.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.kantapp.smile.Activity.NameChangeActivity;
import com.kantapp.smile.Activity.PasswordChangeActivity;
import com.kantapp.smile.Activity.ProfilePicChange;
import com.kantapp.smile.R;
import com.kantapp.smile.Utils.GlideApp;
import com.kantapp.smile.Utils.SP;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView profile_pics=view.findViewById(R.id.profile_pics);
        GlideApp.with(view).load(SP.getData(getActivity()).getProfile())
                .apply(RequestOptions.circleCropTransform())
                .thumbnail(Glide.with(view).load(R.drawable.loading).apply(RequestOptions.centerCropTransform()))
                .into(profile_pics);

        CardView changeProfileName=view.findViewById(R.id.changeProfileName);
        CardView changeProfilePic=view.findViewById(R.id.changeProfilePic);
        CardView changeProfilePassword=view.findViewById(R.id.changeProfilePassword);

        changeProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NameChangeActivity.class));
            }
        });

        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfilePicChange.class));
            }
        });

        changeProfilePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PasswordChangeActivity.class));
            }
        });
        return view;
    }

}
