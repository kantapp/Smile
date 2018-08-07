package com.kantapp.smile.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.kantapp.smile.Activity.NameChangeActivity;
import com.kantapp.smile.Activity.PasswordChangeActivity;
import com.kantapp.smile.Activity.ProfilePicChange;
import com.kantapp.smile.R;
import com.kantapp.smile.Utils.GlideApp;
import com.kantapp.smile.Utils.SP;
import com.kantapp.smile.Utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private ImageView profile_pics;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static final String TAG="ProfileFragment.java";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_pics=view.findViewById(R.id.profile_pics);

        getImage();


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

    private void getImage() {
        AndroidNetworking.get(URL.USER_DETAILS+SP.getData(getActivity()).getToken())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            String imageURL=response.getString("profile");
                            GlideApp.with(getActivity()).load(imageURL)
                                    .skipMemoryCache(true)
                                    .apply(RequestOptions.circleCropTransform())
                                    .thumbnail(Glide.with(getActivity()).load(R.drawable.loading).apply(RequestOptions.centerCropTransform()))
                                    .into(profile_pics);
                            Log.d(TAG, "onResponse: "+imageURL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                            anError.printStackTrace();
                    }
                });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getImage();
//        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
//    }
}
