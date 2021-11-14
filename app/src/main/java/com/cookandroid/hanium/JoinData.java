package com.cookandroid.hanium;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("id")
    String id;

    @SerializedName("pw")
    String pw;

    @SerializedName("pwChk")
    String pwChk;

    @SerializedName("email")
    String email;

    @SerializedName("nickname")
    String nickname;

    public JoinData(String id, String pw, String pwChk, String email, String nickname) {
        this.id = id;
        this.pw = pw;
        this.pwChk = pwChk;
        this.email = email;
        this.nickname = nickname;
    }
}