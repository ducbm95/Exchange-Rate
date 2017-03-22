package com.anca.exchange_rate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.anca.exchange_rate.R;
import com.anca.exchange_rate.adapter.ExRateDataAdapter;
import com.anca.exchange_rate.api.ERResponse;
import com.anca.exchange_rate.api.ExchangeRate;
import com.anca.exchange_rate.api.ExchangeRateService;
import com.anca.exchange_rate.utils.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String BASE_CURRENCY_UNIT = "USD";

    private TextView tvCurrentUnit;
    private FloatingActionButton btnChangeUnit;
    private FloatingActionButton fab;
    private EditText edtValue;

    private List<ExchangeRate> lstExRate = new ArrayList<>();
    private ExRateDataAdapter mAdapter;
    private double mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvCurrentUnit = (TextView) findViewById(R.id.tv_current_unit);
        btnChangeUnit = (FloatingActionButton) findViewById(R.id.btn_change_unit);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        edtValue = (EditText) findViewById(R.id.edt_value);

        btnChangeUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeCurrencyUnitDialog();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadExchangeRate();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        mAdapter = new ExRateDataAdapter(lstExRate, Double.valueOf(edtValue.getText().toString()));
        rv.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_switch) {
            Intent intent = new Intent(this, ExchangeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.open_anim, R.anim.no_change_anim);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ExchangeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.open_anim, R.anim.no_change_anim);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showChangeCurrencyUnitDialog() {
        List<String> lstCurrencyUnit = new ArrayList<>();
        String[] lstCurrency = getResources().getStringArray(R.array.lst_currency);
        lstCurrencyUnit.addAll(Arrays.asList(lstCurrency));

        MaterialDialog.ListCallbackSingleChoice callback = new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (text != null) {
                    tvCurrentUnit.setText(text);
                    BASE_CURRENCY_UNIT = text.toString();
                }
                return true;
            }
        };

        new MaterialDialog.Builder(this)
                .title("Choose base currency unit")
                .items(lstCurrencyUnit)
                .itemsCallbackSingleChoice(-1, callback)
                .positiveText("Change")
                .show();

    }

    private void loadExchangeRate() {

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Loading data...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ExchangeRateService.YAHOO_BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        ExchangeRateService service = retrofit.create(ExchangeRateService.class);

        List<String> toCurrency = new ArrayList<>();
        String[] lstCurrency = getResources().getStringArray(R.array.lst_currency);
        toCurrency.addAll(Arrays.asList(lstCurrency));

        String query = ApiUtils.buildQuery(BASE_CURRENCY_UNIT, toCurrency);
        final Call<ERResponse> lstExchangeRate = service.getExchangeRate(query, ExchangeRateService.YAHOO_ENV);

        lstExchangeRate.enqueue(new Callback<ERResponse>() {
            @Override
            public void onResponse(Call<ERResponse> call, Response<ERResponse> response) {
                Log.d("RESPONSE", "response OK");
                lstExRate.clear();
                lstExRate.addAll(response.body().getLstExchangeRate());
                mAdapter.updateValue(Double.valueOf(edtValue.getText().toString()));
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ERResponse> call, Throwable t) {
                Log.d("RESPONSE", "response failure");
            }
        });
    }

}
