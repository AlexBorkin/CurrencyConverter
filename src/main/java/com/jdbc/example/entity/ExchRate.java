package com.jdbc.example.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExchRate
{
    private Date dateRate;
    private Double currencyRate;
    private String currencyCode;

    public ExchRate()
    {
    }

    public Date getDateRate() {
        return dateRate;
    }

    public void setDateRate(Date dateRate) {
        this.dateRate = dateRate;
    }

    public Double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
