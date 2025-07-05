package vn.com.unit.ep2p.admin.service;

import retrofit2.Call;
import retrofit2.http.*;
import vn.com.unit.ep2p.admin.dto.LoginApiReq;
import vn.com.unit.ep2p.admin.dto.SendSMSApiReq;
import vn.com.unit.ep2p.core.dto.AccountApiDto;
import vn.com.unit.ep2p.core.dto.SendSmsApiRes;


public interface RetrofitAPI {

    @POST("")
    Call<AccountApiDto> loadChanges(@Body LoginApiReq loginApiReq, @Url String url);

    @POST("")
    Call<SendSmsApiRes> sendSMS(@Body SendSMSApiReq loginApiReq, @Url String url);
}
