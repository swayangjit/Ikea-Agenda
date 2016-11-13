package com.sample.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.app.R;
import com.sample.app.model.Record;

import java.util.List;

/**
 * Created by swayangjit on 10/11/16.
 */

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private Context mContext = null;
    private List<Record> mRecordList = null;

    public AgendaAdapter(Context context, List<Record> recordList) {
        mContext = context;
        mRecordList = recordList;
    }

    @Override
    public AgendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_agenda, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Record record = mRecordList.get(position);
        if (!TextUtils.isEmpty(record.getStartTime()) && !record.getStartTime().equalsIgnoreCase("'null'")) {
            viewHolder.vhtvStartDate.setText(Html.fromHtml("<font color=#4A4A4A>Start Time :- </font>" + record.getStartTime()));
        } else {
            viewHolder.vhtvStartDate.setText(Html.fromHtml("<font color=#4A4A4A>Start Time :- </font>" + "N/A"));
        }
        if (!TextUtils.isEmpty(record.getDuration()) && !record.getDuration().equalsIgnoreCase("'null'")) {
            viewHolder.vhtvDuration.setText(Html.fromHtml("<font color=#4A4A4A>Duration :- </font>" + record.getDuration()));
        } else {
            viewHolder.vhtvDuration.setText(Html.fromHtml("<font color=#4A4A4A>Duration :- </font>" + "N/A"));
        }

        if (!TextUtils.isEmpty(record.getSession()) && !record.getSession().equalsIgnoreCase("'null'")) {
            viewHolder.vhtvSession.setText(Html.fromHtml("<font color=#4A4A4A>Session :- </font>" + record.getSession()));
        } else {
            viewHolder.vhtvSession.setText(Html.fromHtml("<font color=#4A4A4A>Session :- </font>" + "N/A"));
        }
        if (!TextUtils.isEmpty(record.getSESSIONOBJECTIVE()) && !record.getSESSIONOBJECTIVE().equalsIgnoreCase("'null'")) {
            viewHolder.vhtvSessionObjective.setText(Html.fromHtml("<font color=#4A4A4A>Session Objective:- </font>" + record.getSESSIONOBJECTIVE()));
        } else {
            viewHolder.vhtvSessionObjective.setText(Html.fromHtml("<font color=#4A4A4A>Session Objective :- </font>" + "N/A"));
        }

        if (!TextUtils.isEmpty(record.getMeetingRoom()) && !record.getMeetingRoom().equalsIgnoreCase("'null'")) {
            viewHolder.vhtvMeetingRoom.setText(Html.fromHtml("<font color=#4A4A4A>Meeting Room :- </font>" + record.getMeetingRoom()));
        } else {
            viewHolder.vhtvMeetingRoom.setText(Html.fromHtml("<font color=#4A4A4A>Meeting Room :- </font>" + "N/A"));
        }
        if (!TextUtils.isEmpty(record.getSessionLead()) && !record.getSessionLead().equalsIgnoreCase("'null'")) {
            viewHolder.vhtvSessionLead.setText(Html.fromHtml("<font color=#4A4A4A>Session Lead :- </font>" + record.getSessionLead()));
        } else {
            viewHolder.vhtvSessionLead.setText(Html.fromHtml("<font color=#4A4A4A>Session Lead :- </font>" + "N/A"));
        }
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView vhtvStartDate;
        public TextView vhtvDuration;
        public TextView vhtvSession;
        public TextView vhtvSessionObjective;
        public TextView vhtvMeetingRoom;
        public TextView vhtvSessionLead;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            vhtvStartDate = (TextView) itemLayoutView.findViewById(R.id.txt_start_date);
            vhtvDuration = (TextView) itemLayoutView.findViewById(R.id.txt_duration);
            vhtvSession = (TextView) itemLayoutView.findViewById(R.id.txt_session);
            vhtvSessionObjective = (TextView) itemLayoutView.findViewById(R.id.txt_session_objective);
            vhtvMeetingRoom = (TextView) itemLayoutView.findViewById(R.id.txt_meeting_room);
            vhtvSessionLead = (TextView) itemLayoutView.findViewById(R.id.txt_session_lead);
        }
    }


}
