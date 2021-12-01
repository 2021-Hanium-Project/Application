package com.cookandroid.hanium;

import java.util.ArrayList;

class FixResponse {
    private String code;
    private String message;
    private ArrayList<FixData> data;

    public FixResponse(String code, String message, ArrayList<FixData> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<FixData> getData() {
        return data;
    }

    public void setData(ArrayList<FixData> data) {
        this.data = data;
    }
}
