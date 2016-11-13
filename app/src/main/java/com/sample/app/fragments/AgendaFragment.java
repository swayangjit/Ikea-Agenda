package com.sample.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sample.app.App;
import com.sample.app.R;
import com.sample.app.adapters.AgendaAdapter;
import com.sample.app.constants.RestApi;
import com.sample.app.customview.CircleProgressBar;
import com.sample.app.model.Record;
import com.sample.app.model.RecordList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swayangjit on 10/11/16.
 */

public class AgendaFragment extends Fragment implements View.OnClickListener {
    private String mTitle;
    private String mParam;
    private CircleProgressBar mCircuarProgress = null;
    private RecyclerView mRecyclerView_Agenda = null;
    private FloatingActionButton mFloatingActionButton = null;
    private TextView mTxtTitle = null;
    private List<Record> mRecordList = null;

    public static AgendaFragment newInstance(String param, String title) {
        AgendaFragment fragmentFirst = new AgendaFragment();
        Bundle args = new Bundle();
        args.putString("day", param);
        args.putString("date", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public static void loadSlideUpAnim(Context context, RecyclerView recyclerView) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.slide_up_in);
        LayoutAnimationController mController = new LayoutAnimationController(animation);
        mController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mController.setDelay((float) 0.3);
        recyclerView.setLayoutAnimation(mController);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam = getArguments().getString("day");
        mTitle = getArguments().getString("date");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        mCircuarProgress = (CircleProgressBar) view.findViewById(R.id.circularProgressBar);
        mCircuarProgress.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
        mTxtTitle.setText(mTitle);

        mRecyclerView_Agenda = (RecyclerView) view.findViewById(R.id.recyclerview_content);
        mRecyclerView_Agenda.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView_Agenda.setHasFixedSize(true);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_feedback);
        mFloatingActionButton.setOnClickListener(this);
        mRecyclerView_Agenda.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && mFloatingActionButton.isShown()) {
                    mFloatingActionButton.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mFloatingActionButton.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
//        if(NetworkUtil.isNetworkAvailable(getActivity())){
        invokeApi(mParam);
//        }

        return view;
    }

    private void invokeApi(String param) {
        String tag_string_req = "string_req";
        String url = RestApi.BASE_URL + "DayController?action=" + param;
        mCircuarProgress.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                RecordList recordList = gson.fromJson(response.toString(), RecordList.class);
                mRecordList = recordList.getRecords();
                mRecyclerView_Agenda.setAdapter(new AgendaAdapter(getActivity(), mRecordList));
                loadSlideUpAnim(getActivity(), mRecyclerView_Agenda);
                mCircuarProgress.setVisibility(View.GONE);
                mFloatingActionButton.setVisibility(View.VISIBLE);
                Log.d("MainActivity", recordList.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mCircuarProgress.setVisibility(View.GONE);
                VolleyLog.d("MainActivity", "Error: " + error.getMessage());
            }
        });

        App.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void invokeFeedbackApi(String string) {
        String url = RestApi.BASE_URL + RestApi.FEEDCONTROLLER + RestApi.FEEDBACKCACTION + string;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (getView() != null) {
                    Snackbar.make(getView(), "Thank you for giving Feedback!!!", Snackbar.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MainActivity", "Error: " + error.getMessage());
            }
        });


        App.getInstance().addToRequestQueue(strReq);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_feedback:
                showFeedBackDialog();
                break;
        }
    }

    private void showFeedBackDialog() {
        if (mRecordList != null) {
            List<String> timeList = new ArrayList<>();
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < mRecordList.size(); i++) {
                timeList.add(mRecordList.get(i).getStartTime());
                nameList.add(mRecordList.get(i).getSession());
            }
            MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Feedback")
                    .customView(R.layout.dialog_feedback, true).autoDismiss(false)
                    .positiveText("Send").negativeText("Cancel").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.cooridinatorLayout);
                            EditText edt_Name = (EditText) dialog.getCustomView().findViewById(R.id.edt_name);
                            EditText edt_FeedBack = (EditText) dialog.getCustomView().findViewById(R.id.edt_feedback);
                            Spinner spinner_Name = (Spinner) dialog.getCustomView().findViewById(R.id.spinner_session_name);
                            if (TextUtils.isEmpty(edt_Name.getText().toString())) {
                                Snackbar.make(coordinatorLayout, "Please enter Name", Snackbar.LENGTH_LONG).show();
                            } else if (TextUtils.isEmpty(edt_FeedBack.getText().toString())) {
                                Snackbar.make(coordinatorLayout, "Please enter Feedback", Snackbar.LENGTH_LONG).show();
                            } else {
                                Record selectedRecord = selectedRecord(spinner_Name.getSelectedItem().toString());
                                try {
                                    invokeFeedbackApi("&day=" + mParam + "&start_time=" + selectedRecord.getStartTime() + "&session=" + URLEncoder.encode(selectedRecord.getSession(), "UTF-8") + "&sessionLead=" + URLEncoder.encode(selectedRecord.getSessionLead()) + "&feedback=" + edt_FeedBack.getText().toString().trim() + "&name=" + URLEncoder.encode(edt_Name.getText().toString(), "UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

//
                                dialog.dismiss();
                            }
                        }
                    }).onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            View view = dialog.getCustomView();
            Spinner spinner_Name = (Spinner) view.findViewById(R.id.spinner_session_name);



            ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameList);

            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner_Name.setAdapter(nameAdapter);
            EditText edt_FeedBack = (EditText) view.findViewById(R.id.edt_feedback);
            edt_FeedBack.setHorizontallyScrolling(false);
            edt_FeedBack.setMaxLines(Integer.MAX_VALUE);

        }
    }

    private Record selectedRecord(String name) {
        if (mRecordList != null) {
            for (int i = 0; i < mRecordList.size(); i++) {
                Record record = mRecordList.get(i);
                if (record.getSession().equalsIgnoreCase(name)) {
                    return record;
                }
            }
        }
        return null;
    }

}
