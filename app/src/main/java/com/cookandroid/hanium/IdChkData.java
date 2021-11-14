package com.cookandroid.hanium;

import com.google.gson.annotations.SerializedName;

class IdChkData {
    @SerializedName("id")
    String id;

    @SerializedName("code")
    private int code;

    public int getCode() {
        return code;
    }

    public IdChkData(String id) {
        this.id = id;
    }

}
