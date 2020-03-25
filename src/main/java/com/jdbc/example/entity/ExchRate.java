package com.jdbc.example.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExchRate
{
    private Date DateRate;
    private Double CurrencyRate;
    private String CurrencyCode;

    public ExchRate()
    {
    }

    public Date getDateRate() {
        return DateRate;
    }

    public void setDateRate(Date dateRate) {
        DateRate = dateRate;
    }

    public Double getCurrencyRate() {
        return CurrencyRate;
    }

    public void setCurrencyRate(Double currencyRate) {
        CurrencyRate = currencyRate;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }
}
