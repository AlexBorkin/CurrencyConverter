package com.jdbc.example.controller;

import com.jdbc.example.dataprovider.CurrencyAttribute;
import com.jdbc.example.dataprovider.CurrencyProvider;
import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

//Загрузка курсов валют с сайта
@Component
public class ExchRatesLoader
{
    private CurrencyService currencyService;
    private ExchRateService exchRateService;
    private CurrencyProvider currencyProvider;

    @Autowired
    public ExchRatesLoader(CurrencyService currencyService,
                           ExchRateService  exchRateService,
                           CurrencyProvider currencyProvider)
    {
        this.currencyService = currencyService;
        this.exchRateService = exchRateService;
        this.currencyProvider = currencyProvider;
    }

    //@Scheduled(cron = "0 0/30 20 * * *")//Запуск каждый день в 20-30
    @Scheduled(fixedRate = 15000) //Каждые 5 минут
    public void loadFromCBR()
    {
        Map<String, CurrencyAttribute>  currencyAttributeMap = null;
        Date                            curDate;
        List<ExchRate>                  exchRateList = null;

        try
        {
            currencyAttributeMap = currencyProvider.getMapDataFromSite();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        if (!currencyAttributeMap.isEmpty())
        {
            currencyService.fillCurrency(currencyAttributeMap);

            for (Map.Entry<String, CurrencyAttribute> entry : currencyAttributeMap.entrySet())
            {
                CurrencyAttribute currencyAttribute = entry.getValue();

                exchRateService.fillExchRate(currencyAttribute.getCurDate(), currencyAttribute.getCharCode(), currencyAttribute.getValue());
            }
        }
    }
}
