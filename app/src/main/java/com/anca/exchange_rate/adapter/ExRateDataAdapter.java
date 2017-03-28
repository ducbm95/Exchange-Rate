package com.anca.exchange_rate.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.anca.exchange_rate.R;
import com.anca.exchange_rate.api.ExchangeRate;
import com.anca.exchange_rate.utils.ApiUtils;

import java.util.List;

/**
 * Created by ishy159@gmail.com on 11-Mar-17.
 */

public class ExRateDataAdapter extends RecyclerView.Adapter<ExRateDataAdapter.ERDataViewHolder> {

    private List<ExchangeRate> lstExRate;
    private double mValue;

    public ExRateDataAdapter(List<ExchangeRate> data, double value) {
        this.lstExRate = data;
        this.mValue = value;
    }

    public void updateValue(double v) {
        this.mValue = v;
    }

    @Override
    public ERDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exchange_rate, parent, false);
        return new ERDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ERDataViewHolder holder, int position) {
        ExchangeRate exRate = this.lstExRate.get(position);

        holder.tvValue.setText(String.valueOf(ApiUtils.round(exRate.getRate() * mValue, 5)));
        holder.tvUnit.setText(String.valueOf(exRate.getRate()) + " " + exRate.getName());
        holder.tvUnit2.setText(exRate.getName().substring(4, 7));

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(exRate.getName().charAt(4)), Color.RED);
        holder.icon.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return this.lstExRate.size();
    }


    public static class ERDataViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView tvValue;
        TextView tvUnit;
        TextView tvUnit2;

        public ERDataViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            tvValue = (TextView) itemView.findViewById(R.id.item_value);
            tvUnit = (TextView) itemView.findViewById(R.id.item_unit);
            tvUnit2 = (TextView) itemView.findViewById(R.id.item_unit_2);
        }
    }
}
