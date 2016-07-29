package com.itguai.restservice;

import com.itguai.models.base.Result;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface CheckInApi {

    /**
     * @param userId
     * @param inwareDetailId
     * @param qualifiedQty
     * @param unQualifiedQty
     * @param manufacturerBatchNumber
     * @param inputDate
     * @param dateType                0 生产日期、1截止日期 2生产周期
     * @param callback
     */
    @GET("/itguai/router/warehouse/pda_stock_check_in/save_bill_entry")
    void saveBillEntry(
            @Query("userId") Long userId,
            @Query("inwareDetailId") Integer inwareDetailId,
            @Query("qualifiedQty") Integer qualifiedQty,
            @Query("unQualifiedQty") Integer unQualifiedQty,
            @Query("manufacturerBatchNumber") String manufacturerBatchNumber,
            @Query("inputDate") String inputDate,
            @Query("dateType") Integer dateType,
            Callback<Result> callback);

}