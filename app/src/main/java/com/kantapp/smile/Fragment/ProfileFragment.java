package com.kantapp.smile.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
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
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private ImageView profile_pics;
    private TextView profile_name;

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
        profile_name=view.findViewById(R.id.profile_name);


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
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
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

    @Override
    public void onResume() {
        super.onResume();
        getImage();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            profile_name.setText(SP.getData(Objects.requireNonNull(getActivity())).getFullname());
        }
    }
}
