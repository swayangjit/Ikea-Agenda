package com.sample.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swayangjit on 9/11/16.
 */

public class RecordList {

    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("Records")
    @Expose
    private List<Record> records = new ArrayList<>();

    /**
     * @return The result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result The Result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return The records
     */
    public List<Record> getRecords() {
        return records;
    }

    /**
     * @param records The Records
     */
    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "RecordList{" +
                "result='" + result + '\'' +
                ", records=" + records +
                '}';
    }
}
