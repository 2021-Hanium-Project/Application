package com.cookandroid.hanium;

import com.google.gson.annotations.SerializedName;

class IdChkResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("id")
    private int id;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return id;
    }

}
