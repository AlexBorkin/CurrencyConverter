package com.jdbc.example.controller;

import com.jdbc.example.dataprovider.CurrencyAttribute;
import com.jdbc.example.dataprovider.CurrencyProvider;
import com.jdbc.example.entity.Currency;
import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyController
{
    private CurrencyService  currencyService;
    private ExchRateService  exchRateService;
    private CurrencyProvider currencyProvider;

    @Autowired
    public CurrencyController(CurrencyService currencyService,
                              ExchRateService  exchRateService,
                              CurrencyProvider currencyProvider)
    {
        this.currencyService = currencyService;
        this.exchRateService = exchRateService;
        this.currencyProvider = currencyProvider;
    }

//    @PostMapping(value = "/createCurrency")
//    public void create(@RequestBody Currency currency)
//    {
//        currencyService.create(currency);
//    }

    //Инициализация данных в таблицах
   // @GetMapping(value = "/")
    //@Scheduled(fixedDelayString = "")
 //   public List<ExchRate> create()
    //@Scheduled(cron = "0 0/30 20 * * *")//Запуск каждый день в 20-30
    public void loadFromCBR()
    {
        Map<String, CurrencyAttribute> currencyAttributeMap;
        Date        curDate;

        List<ExchRate> exchRateList = null;

        //TODO Проверка на существование данных!!!

//        try
//        {
//            currencyProvider.fillExchRateList();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }

        currencyAttributeMap = currencyProvider.getCurrencyAttributeMap();

        curDate = currencyProvider.getCurrentDate();

        //Заполнение справочника валют
        //TODO РАССКОМЕНТАРИТЬ!!!
        currencyService.fillCurrency(currencyAttributeMap);

        //Заполнение таблицы курсов
        for (Map.Entry<String, CurrencyAttribute> entry: currencyAttributeMap.entrySet())
        {
            CurrencyAttribute currencyAttribute = entry.getValue();

            exchRateService.fillExchRate(curDate, currencyAttribute.getCharCode(), currencyAttribute.getValue());
        }

      //  return exchRateList;
    }
}
