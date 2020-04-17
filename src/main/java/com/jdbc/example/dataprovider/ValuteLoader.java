package com.jdbc.example.dataprovider;

import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.service.CurrencyService;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//Загрузка курсов валют с сайта Центробанка
@Component
public class ValuteLoader
{
    private CurrencyService currencyService;
    private ExchRateService exchRateService;
    private ValuteProvider valuteProvider;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public ValuteLoader(CurrencyService currencyService,
                        ExchRateService  exchRateService,
                        ValuteProvider currencyProvider)
    {
        this.currencyService = currencyService;
        this.exchRateService = exchRateService;
        this.valuteProvider = currencyProvider;
    }

    @Scheduled(cron = "0 0/30 23 * * *")//Запуск каждый день в 23-30
    //@Scheduled(fixedRate = 60000) //Запуск каждую минуту
    public void loadFromCBR()
    {
        Map<String, ValuteAttribute>  currencyAttributeMap = null;
        Date                            curDate;
        List<ExchRate>                  exchRateList = null;

        try
        {
            currencyAttributeMap = valuteProvider.getMapDataFromSite();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        if (!currencyAttributeMap.isEmpty())
        {
            currencyService.fillCurrency(currencyAttributeMap);

            for (Map.Entry<String, ValuteAttribute> entry : currencyAttributeMap.entrySet())
            {
                ValuteAttribute valuteAttribute = entry.getValue();

                exchRateService.fillExchRate(valuteAttribute.getCurDate(), valuteAttribute.getCharCode(), valuteAttribute.getValue());
            }
        }

        System.out.println("Справочник валют загружен на дату: " + dateFormat.format(new Date()));
    }
}
