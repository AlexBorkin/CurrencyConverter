package com.jdbc.example.service;

import com.jdbc.example.dataprovider.ValuteAttribute;
import com.jdbc.example.entity.Currency;
import com.jdbc.example.mappers.CurrencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.*;

@Service
public class CurrencyService
{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CurrencyService(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CurrencyService()
    {
    }

    public void create(Currency currency)
    {
        String sqlQuery = "insert into Currency (CurrencyCode, Description) values (?,?);";

        jdbcTemplate.update(sqlQuery, currency.getCurrencyCode(), currency.getDescription());
    }

    public Currency getCurrency(String currencyCode)
    {
        Currency currency;

        String sqlQuery = "select * from Currency where CurrencyCode = ?;";

        currency = jdbcTemplate.queryForObject(sqlQuery, new Object[]{currencyCode}, new CurrencyMapper());

        return currency;
    }

    public List<Currency> listCurrency()
    {
        List<Currency> currencyList;

        String sqlQuery = "select * from Currency order by \"Description\";";

        currencyList = jdbcTemplate.query(sqlQuery, new CurrencyMapper());

        return currencyList;
    }

    public Currency getById(String curr)
    {
        Currency currency;

        String strQuery = "select * from currency where \"CurrencyCode\" = ?;";

        currency = jdbcTemplate.queryForObject(strQuery, new Object[]{curr}, new CurrencyMapper());

        return currency;
    }

    public void fillCurrency(Map<String, ValuteAttribute> map)
    {
        ArrayList<ValuteAttribute> currencyListAttribute;

        ValuteAttribute curAttr;

        String strQuery = "insert into currency(\"CurrencyCode\", \"Description\", \"Nominal\", \"NumCode\") values (?,?,?,?);";

        for (Map.Entry<String, ValuteAttribute> curEntry: map.entrySet())
        {
            curAttr = curEntry.getValue();

            try
            {
                jdbcTemplate.update(strQuery, curAttr.getCharCode(), curAttr.getName(), Integer.parseInt(curAttr.getNominal()), curAttr.getNumCode());
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
