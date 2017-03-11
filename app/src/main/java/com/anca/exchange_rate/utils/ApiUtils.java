package com.anca.exchange_rate.utils;

import java.util.List;

/**
 * Created by ishy159@gmail.com on 11-Mar-17.
 */

public class ApiUtils {

    public static String buildQuery(String fromCurrency, List<String> toCurrency) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select * from yahoo.finance.xchange where pair in (");
        for (int i = 0; i < toCurrency.size(); i++) {
            buffer.append("\"");
            buffer.append(fromCurrency);
            buffer.append(toCurrency.get(i));
            if (i == toCurrency.size() - 1)
                buffer.append("\"");
            else
                buffer.append("\",");
        }
        buffer.append(")");
        return buffer.toString();
    }
}
