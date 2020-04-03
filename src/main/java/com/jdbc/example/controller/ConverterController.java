package com.jdbc.example.controller;

import com.jdbc.example.entity.Currency;
import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ConverterController
{
    public CurrencyService currencyService;
    public ExchRateService exchRateService;

    @Autowired
    public ConverterController(CurrencyService currencyService, ExchRateService exchRateService)
    {
        this.currencyService = currencyService;
        this.exchRateService = exchRateService;
    }

    @GetMapping("/")
    public String mainPage()
    {
        return "main";
    }

    @GetMapping("/converter")
    public String showConverter(Model model)
    {
        List<Currency> currencyList = currencyService.listCurrency();

        Currency currencyDef = currencyList.get(3);

        model.addAttribute("defFromCode",  currencyDef.getCurrencyCode());
        model.addAttribute("defFromDescr", currencyDef.getFullDescription());
        model.addAttribute("defToCode",    currencyDef.getCurrencyCode());
        model.addAttribute("defToDescr",   currencyDef.getFullDescription());

        model.addAttribute("currencyFrom", currencyList);
        model.addAttribute("currencyTo", currencyList);
        model.addAttribute("valueFrom", 1);
        model.addAttribute("retValue", 0);

        return "converter";
    }

    @PostMapping("/converter")
    public String calculate(String currFrom, String currTo, Double valueFrom, Model model)
    {
        Double retVal = 0.0;

        List<Currency> currencyList = currencyService.listCurrency();

        retVal = exchRateService.calcValue(currFrom, currTo, valueFrom);

        Currency currDefFrom = currencyService.getById(currFrom);
        Currency currDefTo   = currencyService.getById(currTo);

        model.addAttribute("defFromCode",  currDefFrom.getCurrencyCode());
        model.addAttribute("defFromDescr", currDefFrom.getFullDescription());
        model.addAttribute("defToCode",    currDefTo.getCurrencyCode());
        model.addAttribute("defToDescr",   currDefTo.getFullDescription());

        model.addAttribute("currencyFrom", currencyList);
        model.addAttribute("currencyTo",   currencyList);
        model.addAttribute("valueFrom",    valueFrom);
        model.addAttribute("retValue",     retVal);

        return "converter";
    }
}
