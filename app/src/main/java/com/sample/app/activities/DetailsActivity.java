package com.sample.app.activities;

import android.os.Bundle;
import android.util.Log;
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
import com.sample.app.model.Record;
import com.sample.app.model.RecordList;

import java.util.List;

/**
 * Created by swayangjit on 9/11/16.
 */

public class DetailsActivity extends BaseActivity {
    private TableLayout mTableLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mTableLayout = (TableLayout) findViewById(R.id.tableRowContents);
//        CircleProgressBar progress1 = (CircleProgressBar) findViewById(R.id.progress1);
//        progress1.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        String tag_string_req = "string_req";

        String url = "https://ikeacoworkervisit1.mybluemix.net/DayController?action=day1";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MainActivity", response.toString());
                Gson gson = new Gson();

// 1. JSON to Java object, read it from a file.
                RecordList recordList = gson.fromJson(response.toString(), RecordList.class);
                renderTableLayout(recordList);
                Log.d("MainActivity", recordList.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MainActivity", "Error: " + error.getMessage());
            }
        });

        App.getInstance().addToRequestQueue(strReq, tag_string_req);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

        }
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
            records.set(0, newRecord);
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);
                TableRow tableRow = new TableRow(this);

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
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.layout_text, null);
        textView.setLayoutParams(new TableRow.LayoutParams(coloumn));
        if (position % 2 != 0 && position != 0) {
            textView.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        textView.setText(data);
        return textView;
    }
}
