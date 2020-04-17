package com.jdbc.example.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HistoryQuery
{
    private String currencyCodeFrom;
    private String currencyCodeTo;
    private Double convertVal;
    private Double resultVal;
    private Date   dateConvert;


    public HistoryQuery()
    {
    }

    public String getCurrencyCodeFrom() {
        return currencyCodeFrom;
    }

    public void setCurrencyCodeFrom(String currencyCodeFrom) {
        this.currencyCodeFrom = currencyCodeFrom;
    }

    public String getCurrencyCodeTo() {
        return currencyCodeTo;
    }

    public void setCurrencyCodeTo(String currencyCodeTo) {
        this.currencyCodeTo = currencyCodeTo;
    }

    public Double getConvertVal() {
        return convertVal;
    }

    public void setConvertVal(Double convertVal) {
        this.convertVal = convertVal;
    }

    public Double getResultVal() {
        return resultVal;
    }

    public void setResultVal(Double resultVal) {
        this.resultVal = resultVal;
    }

    public Date getDateConvert() {
        return dateConvert;
    }

    public void setDateConvert(Date dateConvert) {
        this.dateConvert = dateConvert;
    }
}
