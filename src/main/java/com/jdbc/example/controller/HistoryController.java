package com.jdbc.example.controller;

import com.jdbc.example.ConverterApplication;
import com.jdbc.example.entity.Currency;
import com.jdbc.example.entity.HistoryQuery;
import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.HistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jws.WebParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HistoryController
{
    private HistoryQueryService historyQueryService;
    private CurrencyService     currencyService;

    @Autowired
    public HistoryController(HistoryQueryService historyQueryService, CurrencyService currencyService)
    {
        this.historyQueryService = historyQueryService;
        this.currencyService = currencyService;
    }

    @GetMapping("/historyQuery")
    public String getAll(Model model)
    {
        model.addAttribute("history", historyQueryService.getAll());

        Currency currency = ConverterApplication.currencyListGlobal.get(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");

        model.addAttribute("FromCode", currency.getCurrencyCode());
        model.addAttribute("FromDescr", currency.getFullDescription());
        model.addAttribute("ToCode", currency.getCurrencyCode());
        model.addAttribute("ToDescr", currency.getFullDescription());

        model.addAttribute("convertDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        model.addAttribute("currencyFrom", ConverterApplication.currencyListGlobal);
        model.addAttribute("currencyTo", ConverterApplication.currencyListGlobal);

        return "historyQuery";
    }


    @PostMapping("/historyQuery")
    public String getByFilter(
             String currencyCodeFrom,
             String currencyCodeTo,
             String convertDate,
             Model model) throws ParseException
    {
        List<HistoryQuery> listHistoryQuery = historyQueryService.getByFilter(currencyCodeFrom, currencyCodeTo, convertDate);

        model.addAttribute("history", listHistoryQuery);

        Currency currDefFrom = currencyService.getById(currencyCodeFrom);
        Currency currDefTo   = currencyService.getById(currencyCodeTo);

        model.addAttribute("FromCode",  currDefFrom.getCurrencyCode());
        model.addAttribute("FromDescr", currDefFrom.getFullDescription());
        model.addAttribute("ToCode",    currDefTo.getCurrencyCode());
        model.addAttribute("ToDescr",   currDefTo.getFullDescription());

        model.addAttribute("convertDate", convertDate);

        model.addAttribute("currencyFrom", ConverterApplication.currencyListGlobal);
        model.addAttribute("currencyTo", ConverterApplication.currencyListGlobal);

        return "historyQuery";
    }
}


