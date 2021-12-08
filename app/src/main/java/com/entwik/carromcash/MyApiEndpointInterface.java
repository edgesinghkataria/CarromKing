package com.entwik.carromcash;

import com.entwik.carromcash.models.account.AccountDetailsModel;
import com.entwik.carromcash.models.account.AccountResponseModel;
import com.entwik.carromcash.models.debit.DebitInitRequestModel;
import com.entwik.carromcash.models.debit.DebitResponseModel;
import com.entwik.carromcash.models.home.HomeResponseModel;
import com.entwik.carromcash.models.lobby.JoinRequestModel;
import com.entwik.carromcash.models.lobby.JoinResponseModel;
import com.entwik.carromcash.models.otp.SendOTPResponseModel;
import com.entwik.carromcash.models.otp.VerifyOTPBodyModel;
import com.entwik.carromcash.models.otp.VerifyOTPResponseModel;
import com.entwik.carromcash.models.paytm.PaytmRequestInitModel;
import com.entwik.carromcash.models.paytm.PaytmResponseModel;
import com.entwik.carromcash.models.profile.ProfileResponseModel;
import com.entwik.carromcash.models.wallet.TransactionResponseModel;
import com.entwik.carromcash.models.wallet.WalletResponseModel;

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

    @GET("user/profile")
    Call<ProfileResponseModel> getProfileData();

    @GET("home/games")
    Call<HomeResponseModel> getHomeData();

    @GET("user/wallet")
    Call<WalletResponseModel> getWalletData();

    @GET("user/transaction")
    Call<TransactionResponseModel> getTransactionData();

    @POST("user/transaction/deposit/init")
    Call<PaytmResponseModel> initPaytmTxn(@Body PaytmRequestInitModel amount);

    @POST("lobby/join")
    Call<JoinResponseModel> joinLobby(@Body JoinRequestModel lobbyId);

    @POST("user/transaction/debit/init")
    Call<DebitResponseModel> initDebit(@Body DebitInitRequestModel amount);

    @POST("user/bank")
    Call<AccountResponseModel> accountDetails(@Body AccountDetailsModel details);

}
