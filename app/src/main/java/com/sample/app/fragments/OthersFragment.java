package com.sample.app.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.app.R;

/**
 * Created by swayangjit on 12/11/16.
 */

public class OthersFragment extends Fragment implements View.OnClickListener {

    public static OthersFragment newInstance() {
        OthersFragment fragment = new OthersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        view.findViewById(R.id.layout_hotel).setOnClickListener(this);
        view.findViewById(R.id.layout_office).setOnClickListener(this);
        view.findViewById(R.id.layout_dinner).setOnClickListener(this);
        view.findViewById(R.id.layout_contacts).setOnClickListener(this);
        view.findViewById(R.id.layout_cab).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_hotel:
                showLocationinMap(12.960144, 77.648492, "Hotel");
                break;
            case R.id.layout_office:
                showLocationinMap(13.041394, 77.620543, "Office");
                break;
            case R.id.layout_dinner:
                showLocationinMap(13.044658, 77.626287, "Dinner Venue");
                break;
            case R.id.layout_contacts:
                break;
            case R.id.layout_cab:
                break;
        }
    }

    private void showLocationinMap(final double lat, final double lng, final String title) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String strUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + title + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                startActivity(intent);
            }
        }, 500);


    }
}
