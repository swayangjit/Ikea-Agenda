package com.sample.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sample.app.App;
import com.sample.app.R;
import com.sample.app.Record;
import com.sample.app.RecordList;
import com.sample.app.customview.CircleProgressBar;

import java.util.List;

/**
 * Created by swayangjit on 10/11/16.
 */

public class AgendaFragment extends Fragment {
    private String mTitle;
    private String mParam;
    private TableLayout mTableLayout = null;
    private CircleProgressBar mCircuarProgress = null;
    private TextView mTxt_AgendaName = null;

    public static AgendaFragment newInstance(String param, String title) {
        AgendaFragment fragmentFirst = new AgendaFragment();
        Bundle args = new Bundle();
        args.putString("someInt", param);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam = getArguments().getString("someInt");
        mTitle = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        mTableLayout = (TableLayout) view.findViewById(R.id.tableRowContents);
        mCircuarProgress = (CircleProgressBar) view.findViewById(R.id.circularProgressBar);
        mCircuarProgress.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mTxt_AgendaName = (TextView) view.findViewById(R.id.txt_agenda_name);
        mTxt_AgendaName.setText(mTitle);
        invokeApi(mParam);
        return view;
    }

    private void invokeApi(String param) {
        String tag_string_req = "string_req";

        String url = "https://ikeacoworkervisit1.mybluemix.net/DayController?action=" + param;
        Log.i("Url::", "Url::::" + url);
        mCircuarProgress.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MainActivity", response.toString());
                Gson gson = new Gson();

                RecordList recordList = gson.fromJson(response.toString(), RecordList.class);
                renderTableLayout(recordList);
                mCircuarProgress.setVisibility(View.GONE);
                Log.d("MainActivity", recordList.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MainActivity", "Error: " + error.getMessage());
            }
        });

        App.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void renderTableLayout(RecordList recordList) {
        if (null != recordList && null != recordList.getRecords()) {
            Record newRecord = new Record();
            newRecord.setStartTime("Start Time");
            newRecord.setDuration("Duration");
            newRecord.setSession("Session");
            newRecord.setSESSIONOBJECTIVE("SESSION OBJECTIVE");
            newRecord.setMeetingRoom("Meeting Room");
            newRecord.setSessionLead("Session Lead");
            List<Record> records = recordList.getRecords();
            records.add(0, newRecord);
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);
                TableRow tableRow = new TableRow(getActivity());

                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(inflateText(record.getStartTime(), 1, i));
                tableRow.addView(inflateText(record.getDuration(), 2, i));
                tableRow.addView(inflateText(record.getSession(), 3, i));
                tableRow.addView(inflateText(record.getSESSIONOBJECTIVE(), 4, i));
                tableRow.addView(inflateText(record.getMeetingRoom(), 5, i));
                tableRow.addView(inflateText(record.getSessionLead(), 6, i));
                mTableLayout.addView(tableRow);

            }
        }


    }

    private TextView inflateText(String data, int coloumn, int position) {
        TextView textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.layout_text, null);
        textView.setLayoutParams(new TableRow.LayoutParams(coloumn));
        if (position % 2 != 0 && position != 0) {
            textView.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        textView.setText(data);
        return textView;
    }
}
