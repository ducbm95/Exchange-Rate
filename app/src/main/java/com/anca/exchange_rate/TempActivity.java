package com.anca.exchange_rate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anca.exchange_rate.api.ERResponse;
import com.anca.exchange_rate.api.ExchangeRateService;
import com.anca.exchange_rate.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        List<String> toCurrency = new ArrayList<>();
        toCurrency.add("EUR");
        String query = ApiUtils.buildQuery("USD", toCurrency);
        Log.d("QUERY", query);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ExchangeRateService.YAHOO_BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        ExchangeRateService service = retrofit.create(ExchangeRateService.class);
        Call<ERResponse> call = service.getExchangeRate(query, ExchangeRateService.YAHOO_ENV);

        call.enqueue(new Callback<ERResponse>() {
            @Override
            public void onResponse(Call<ERResponse> call, Response<ERResponse> response) {
                Log.d("RESPONSE", "response OK");
//                response.body().getLstExchangeRate().get(0).getName().toString();
            }

            @Override
            public void onFailure(Call<ERResponse> call, Throwable t) {
                Log.d("RESPONSE", "response failure" + t.toString());

//                call.
            }
        });
    }
}
