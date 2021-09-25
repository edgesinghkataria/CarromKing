package com.android.carromking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation

    @GET("auth/otp")
    Call<SendOTPResponseModel> getOtp(@Query("mobileNumber") String mobileNumber);

    @POST("auth")
    Call<VerifyOTPResponseModel> verifyOTP(@Body VerifyOTPBodyModel bodyModel);

}
