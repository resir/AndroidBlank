package com.itguai.restservice;

import com.itguai.models.base.Result;
import com.itguai.models.param.SampleParam;
import com.itguai.models.result.UserBO;
import com.itguai.models.result.UserCommon;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;

public interface UserManagerApi {

    /**
     * 获取用户信息
     */
    @GET("/itguai/router/user_manage/get_user_info")
    void getUserInfo(Callback<Result<UserBO>> callback);

    @GET("/itguai/router/warehouse/pda_common_data/pda_common_data")
    void commonData(Callback<Result<UserCommon>> callback);

    @POST("/itguai/router/warehouse/pda_common_data/pda_common_data")
    void sample(SampleParam param,Callback<Result<UserCommon>> callback);

}