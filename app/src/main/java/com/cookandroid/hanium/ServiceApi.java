package com.cookandroid.hanium;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("user/idchk")
    Call<IdChkResponse> userIdchk(@Body IdChkData data);

    @POST("user/test")
    Call<testResponse> test(@Body testData data);

    @POST("user/RR")
    Call<HashMap<String, String>> userRRInfo(@Body RecommendData data);

    @POST("user/issueTempPassword")
    Call<TempPasswordResponse> issueTempPassword(@Body TempPasswordData data);

    @GET("user/recommendList")
    Call<RecommendResponse> getRecommendList(@Query("id") String id);

    @GET("user/recommendFilteringList")
    Call<RecommendResponse> getRecommendFilteringResultList(@Query("id") String id, @Query("filteringArray") ArrayList<String> filteringArray);

    @GET("user/myInfo")
    Call<HashMap<String, String>> getMyInformation(@Query("id") String id);

    @GET("user/modifyPassword")
    Call<HashMap<String, String>> modifyPassword(@Query("id") String id, @Query("currentPassword") String currentPassword,@Query("newPassword") String newPassword);

    @GET("user/getFixList")
    Call<FixResponse> getFixList(@Query("id") String id);

    @POST("user/sendFix")
    Call<HashMap<String, String>> sendFix(@Body FixData data);
}