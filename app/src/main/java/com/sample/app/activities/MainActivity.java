package com.sample.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.sample.app.App;
import com.sample.app.R;
import com.sample.app.constants.RestApi;
import com.sample.app.fragments.AgendaFragment;
import com.sample.app.fragments.OthersFragment;
import com.sample.app.util.PreferenceUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        insertFcmToken();
        initView();
    }

    private void insertFcmToken() {
        Log.i("Token", PreferenceUtil.getInstance().getFcmToken());
        String token = PreferenceUtil.getInstance().getFcmToken();
        if (!TextUtils.isEmpty(token)) {
            String url = RestApi.BASE_URL + RestApi.NOTIFICATIONCONTROLLER + RestApi.INSERTTOKENCACTION + "&token=" + token;
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    VolleyLog.d(TAG, "FCM token inserted");


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });


            App.getInstance().addToRequestQueue(strReq);
        }
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("extra_text", "");
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 6;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AgendaFragment.newInstance("day1", "Day 1 , 5th December");
                case 1:
                    return AgendaFragment.newInstance("day2", "Day 2 , 6th December");
                case 2:
                    return AgendaFragment.newInstance("day3", "Day 3 , 7th December");
                case 3:
                    return AgendaFragment.newInstance("day4", "Day 4 , 8th December");
                case 4:
                    return AgendaFragment.newInstance("day5", "Day 5 , 9th December");
                case 5:
                    return OthersFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Day 1 Bengaluru";
                case 1:
                    return "Day 2 Bengaluru";
                case 2:
                    return "Day 3 Bengaluru";
                case 3:
                    return "Day 4 Kolkata";
                case 4:
                    return "Day 5 Kolkata";
                case 5:
                    return "Others";
                default:
                    return null;
            }
        }

    }
}
