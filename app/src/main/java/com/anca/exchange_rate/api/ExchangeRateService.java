package com.anca.exchange_rate.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ishy159@gmail.com on 11-Mar-17.
 */

public interface ExchangeRateService {

    String YAHOO_BASE_URL = "http://query.yahooapis.com";
    String YAHOO_ENV = "store://datatables.org/alltableswithkeys";

    @GET("/v1/public/yql")
    Call<ERResponse> getExchangeRate(
            @Query("q") String query, @Query("env") String env);


}
