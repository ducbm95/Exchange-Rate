package com.anca.exchange_rate.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ishy159@gmail.com on 11-Mar-17.
 */

@Root(name = "rate", strict = false)
public class ExchangeRate {

    @Element(name = "Name")
    private String name;
    @Element(name = "Rate")
    private double rate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
