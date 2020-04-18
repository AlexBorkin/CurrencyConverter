package com.jdbc.example.controller;

import com.jdbc.example.ConverterApplication;
import com.jdbc.example.entity.Currency;
import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.ExchRateService;
import com.jdbc.example.service.HistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class ConverterController
{
    public CurrencyService currencyService;
    public ExchRateService exchRateService;
    public HistoryQueryService historyQueryService;

    @Autowired
    public ConverterController(CurrencyService currencyService, ExchRateService exchRateService, HistoryQueryService historyQueryService)
    {
        this.currencyService = currencyService;
        this.exchRateService = exchRateService;
        this.historyQueryService = historyQueryService;
    }

    @GetMapping({"/", "/converter"})
    public String converter(Model model)
    {
        //if (ConverterApplication.currencyListGlobal = null && !ConverterApplication.currencyListGlobal.isEmpty())
        if (ConverterApplication.currencyListGlobal.isEmpty())
        {
            ConverterApplication.currencyListGlobal = currencyService.listCurrency();

        }
            Currency currencyDef = ConverterApplication.currencyListGlobal.get(0);

            model.addAttribute("defFromCode", currencyDef.getCurrencyCode());
            model.addAttribute("defFromDescr", currencyDef.getFullDescription());
            model.addAttribute("defToCode", currencyDef.getCurrencyCode());
            model.addAttribute("defToDescr", currencyDef.getFullDescription());

            model.addAttribute("currencyFrom", ConverterApplication.currencyListGlobal);
            model.addAttribute("currencyTo", ConverterApplication.currencyListGlobal);
            model.addAttribute("valueFrom", 1);
            model.addAttribute("retValue", 0);


        return "converter";
    }

    @PostMapping({"/","/converter"})
    @Transactional
    public String calculate(String currFrom, String currTo, Double valueFrom, Model model)
    {
        if (ConverterApplication.currencyListGlobal.isEmpty())
        {
            ConverterApplication.currencyListGlobal = currencyService.listCurrency();
        }

        Double retVal = 0.0;

        retVal = exchRateService.calcValue(currFrom, currTo, valueFrom);

        Currency currDefFrom = currencyService.getById(currFrom);
        Currency currDefTo   = currencyService.getById(currTo);

        model.addAttribute("defFromCode",  currDefFrom.getCurrencyCode());
        model.addAttribute("defFromDescr", currDefFrom.getFullDescription());
        model.addAttribute("defToCode",    currDefTo.getCurrencyCode());
        model.addAttribute("defToDescr",   currDefTo.getFullDescription());

        model.addAttribute("currencyFrom", ConverterApplication.currencyListGlobal);
        model.addAttribute("currencyTo",   ConverterApplication.currencyListGlobal);
        model.addAttribute("valueFrom",    valueFrom);
        model.addAttribute("retValue",     retVal);

        historyQueryService.saveRecord(currFrom, currTo, valueFrom, retVal, new Date());

        return "converter";
    }

}
