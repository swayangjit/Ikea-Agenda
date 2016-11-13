package com.sample.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by swayangjit on 9/11/16.
 */

public class Record {

    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("SESSION_OBJECTIVE")
    @Expose
    private String sESSIONOBJECTIVE;
    @SerializedName("meeting_room")
    @Expose
    private String meetingRoom;
    @SerializedName("sessionLead")
    @Expose
    private String sessionLead;

    /**
     * @return The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return The session
     */
    public String getSession() {
        return session;
    }

    /**
     * @param session The session
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * @return The sESSIONOBJECTIVE
     */
    public String getSESSIONOBJECTIVE() {
        return sESSIONOBJECTIVE;
    }

    /**
     * @param sESSIONOBJECTIVE The SESSION_OBJECTIVE
     */
    public void setSESSIONOBJECTIVE(String sESSIONOBJECTIVE) {
        this.sESSIONOBJECTIVE = sESSIONOBJECTIVE;
    }

    /**
     * @return The meetingRoom
     */
    public String getMeetingRoom() {
        return meetingRoom;
    }

    /**
     * @param meetingRoom The meeting_room
     */
    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    /**
     * @return The sessionLead
     */
    public String getSessionLead() {
        return sessionLead;
    }

    /**
     * @param sessionLead The sessionLead
     */
    public void setSessionLead(String sessionLead) {
        this.sessionLead = sessionLead;
    }

    @Override
    public String toString() {
        return "Record{" +
                "startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                ", session='" + session + '\'' +
                ", sESSIONOBJECTIVE='" + sESSIONOBJECTIVE + '\'' +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", sessionLead='" + sessionLead + '\'' +
                '}';
    }
}


