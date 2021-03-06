package com.jdbc.example.controller;

import com.jdbc.example.dataprovider.ValuteProvider;
import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CurrencyController
{
    private CurrencyService  currencyService;
    private ExchRateService  exchRateService;
    private ValuteProvider   currencyProvider;

    @Autowired
    public CurrencyController(CurrencyService currencyService,
                              ExchRateService  exchRateService,
                              ValuteProvider currencyProvider)
    {
        this.currencyService  = currencyService;
        this.exchRateService  = exchRateService;
        this.currencyProvider = currencyProvider;
    }
}
