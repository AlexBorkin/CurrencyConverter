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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HistoryController
{
    private HistoryQueryService historyQueryService;
    private CurrencyService     currencyService;

    public static final Currency currencyAllElt = new Currency();

    static
    {
        currencyAllElt.setCurrencyCode("");
        currencyAllElt.setFullDescription("Все");
    }

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

        List<Currency> retsultList = new ArrayList<Currency>();
       // List<Currency> retsultListTo = new ArrayList<Currency>();
        retsultList.add(currencyAllElt);

        if (ConverterApplication.currencyListGlobal.isEmpty())
        {
            ConverterApplication.currencyListGlobal = currencyService.listCurrency();
        }

        Currency currency = currencyAllElt;//ConverterApplication.currencyListGlobal.get(0);

        model.addAttribute("FromCode", currency.getCurrencyCode());
        model.addAttribute("FromDescr", currency.getFullDescription());
        model.addAttribute("ToCode", currency.getCurrencyCode());
        model.addAttribute("ToDescr", currency.getFullDescription());

//        model.addAttribute("FromCode", "");
//        model.addAttribute("FromDescr", "Все");
//        model.addAttribute("ToCode", "");
//        model.addAttribute("ToDescr", "Все");

        model.addAttribute("convertDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        retsultList = ConverterApplication.currencyListGlobal.stream().filter(x-> !x.getCurrencyCode().equals(currency.getCurrencyCode())).collect(Collectors.toList());
       // retsultListTo = ConverterApplication.currencyListGlobal.stream().filter(x-> !x.getCurrencyCode().equals(currencyCodeTo)).collect(Collectors.toList());

        model.addAttribute("currencyFrom", retsultList); //  ConverterApplication.currencyListGlobal);
        model.addAttribute("currencyTo", retsultList); // ConverterApplication.currencyListGlobal);

        return "historyQuery";
    }

    @PostMapping("/historyQuery")
    public String getByFilter(
             String currencyCodeFrom,
             String currencyCodeTo,
             String convertDate,
             Model model) throws ParseException
    {
        List<Currency> retsultListFrom = new ArrayList<Currency>();
        List<Currency> retsultListTo   = new ArrayList<Currency>();

        if (ConverterApplication.currencyListGlobal.isEmpty())
        {
            ConverterApplication.currencyListGlobal = currencyService.listCurrency();
        }

        retsultListFrom.add(currencyAllElt);
        retsultListFrom.addAll(ConverterApplication.currencyListGlobal);
        retsultListTo.add(currencyAllElt);
        retsultListTo.addAll(ConverterApplication.currencyListGlobal);

        retsultListFrom = retsultListFrom.stream().filter(x-> !x.getCurrencyCode().equals(currencyCodeFrom)).collect(Collectors.toList());
        retsultListTo   = retsultListTo.stream().filter(x-> !x.getCurrencyCode().equals(currencyCodeTo)).collect(Collectors.toList());

        List<HistoryQuery> listHistoryQuery = historyQueryService.getByFilter(currencyCodeFrom, currencyCodeTo, convertDate);

        model.addAttribute("history", listHistoryQuery);

        Currency currDefFrom = currencyService.getById(currencyCodeFrom);

        model.addAttribute("FromCode", currDefFrom.getCurrencyCode());
        model.addAttribute("FromDescr", currDefFrom.getFullDescription());

        Currency currDefTo   = currencyService.getById(currencyCodeTo);

        model.addAttribute("ToCode",    currDefTo.getCurrencyCode());
        model.addAttribute("ToDescr",   currDefTo.getFullDescription());

        model.addAttribute("convertDate", convertDate);

        model.addAttribute("currencyFrom", retsultListFrom);//ConverterApplication.currencyListGlobal);
        model.addAttribute("currencyTo", retsultListTo);//ConverterApplication.currencyListGlobal);

        return "historyQuery";
    }
}


