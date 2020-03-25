package com.jdbc.example.service;

import com.jdbc.example.entity.Currency;
import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.mappers.CurrencyMapper;
import com.jdbc.example.mappers.ExchRateMapper;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Service
public class ExchRateService
{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ExchRateService(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void fillExchRate(Date curDate, String charCode, Double value)
    {
        String strQuery = "insert into public.\"exchRates\"(\"DateRate\", \"CurrencyCode\", \"CurrencyRate\") values (?,?,?);";

        try
        {
            jdbcTemplate.update(strQuery, curDate, charCode, value);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public List<ExchRate> getAll()
    {
        String strQuery = "select * from public.\"exchRates\" order by \"DateRate\" desc;";
        List<ExchRate> exchRateList = jdbcTemplate.query(strQuery, new ExchRateMapper());

        return exchRateList;
    }


    public Double calcValue(String currFrom, String currTo, Double value)
    {
        Double resValue = 0.0;

        Double curValFrom;
        Double curValTo;

        ExchRate exchRateFrom;
        ExchRate exchRateTo;

        Currency currencyFrom;
        Currency currencyTo;

        String strQueryExchRate = "select * from public.\"exchRates\" AS RATE\n" +
                "where \"CurrencyCode\" = ? \n" +
                "order by \"DateRate\" desc LIMIT 1;";

        String strQueryCurrency = "select * from public.\"currency\" \n" +
                "where \"CurrencyCode\" = ? ;";


        exchRateFrom = jdbcTemplate.queryForObject(strQueryExchRate, new Object[]{currFrom}, new ExchRateMapper());
        exchRateTo   = jdbcTemplate.queryForObject(strQueryExchRate, new Object[]{currTo}, new ExchRateMapper());

        currencyFrom = jdbcTemplate.queryForObject(strQueryCurrency, new Object[]{currFrom}, new CurrencyMapper());
        currencyTo   = jdbcTemplate.queryForObject(strQueryCurrency, new Object[]{currTo}, new CurrencyMapper());

        //TODO ДОБАВИТЬ ЗА СКОЛЬКО ЕДЕНИЦ!!!
        resValue = value * ((exchRateFrom.getCurrencyRate() / currencyFrom.getNominal()) / (exchRateTo.getCurrencyRate() / currencyTo.getNominal()));

        return DoubleRounder.round(resValue, 4);
    }
}
