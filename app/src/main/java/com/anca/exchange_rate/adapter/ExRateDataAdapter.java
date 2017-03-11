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

import java.util.List;

/**
 * Created by ishy159@gmail.com on 11-Mar-17.
 */

public class ExRateDataAdapter extends RecyclerView.Adapter<ExRateDataAdapter.ERDataViewHolder> {

    private List<ExchangeRate> lstExRate;

    public ExRateDataAdapter(List<ExchangeRate> data) {
        this.lstExRate = data;
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

        holder.tvValue.setText(String.valueOf(exRate.getRate()));
        holder.tvUnit.setText(exRate.getName());
    }

    @Override
    public int getItemCount() {
        return this.lstExRate.size();
    }

    public static class ERDataViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView tvValue;
        TextView tvUnit;

        public ERDataViewHolder(View itemView) {
            super(itemView);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect("A", Color.RED);
            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            icon.setImageDrawable(drawable);
            tvValue = (TextView) itemView.findViewById(R.id.item_value);
            tvUnit = (TextView) itemView.findViewById(R.id.item_unit);
        }
    }
}
