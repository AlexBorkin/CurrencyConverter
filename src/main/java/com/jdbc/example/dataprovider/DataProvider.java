package com.jdbc.example.dataprovider;

import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

public class DataProvider
{
    private CurrencyAttribute currencyAttribute;
    private Map<String, CurrencyAttribute> mapData;
    private Date curDate;

    @Autowired
    private CurrencyProvider currencyProvider;
    @Autowired
    private ExchRateProvider exchRateProvider;
    @Autowired
    private CurrencyService  currencyService;
    @Autowired
    private ExchRateService  exchRateService;

    public DataProvider()
    {
    }

    public DataProvider(CurrencyProvider currencyProvider, ExchRateProvider exchRateProvider)
    {
        this.currencyProvider = currencyProvider;
        this.exchRateProvider = exchRateProvider;
    }

    public void create()
    {
        Map<String, CurrencyAttribute> currencyAttributeMap;
        Date        curDate;

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
    }
}
