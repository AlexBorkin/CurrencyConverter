package com.jdbc.example.service;

import com.jdbc.example.entity.HistoryQuery;
import com.jdbc.example.mappers.HistoryQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Service
public class HistoryQueryService
{
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public HistoryQueryService(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<HistoryQuery> getAll()
    {
        String strQuery = "SELECT \"currencyCodeFrom\", \"currencyCodeTo\", \"convertVal\", \"resultVal\", \"dateConvert\"\n" +
                            "\tFROM public.\"historyQuery\";";

        return jdbcTemplate.query(strQuery, new HistoryQueryMapper());
    }

    public void saveRecord(String currencyFrom, String currencyTo, Double convertVal, Double resultVal, Date dateConvert)
    {
        String strQuery = "INSERT INTO public.\"historyQuery\"(\n" +
                " \"currencyCodeFrom\", \"currencyCodeTo\", \"convertVal\", \"resultVal\", \"dateConvert\")\n" +
                " VALUES (?, ?, ?, ?, ?);";

        jdbcTemplate.update(strQuery, currencyFrom, currencyTo, convertVal, resultVal, dateConvert);
    }

    public List<HistoryQuery> getByFilter(String currencyCodeFrom, String currencyCodeTo, String dateConvert)
    {
        String strQuery = "SELECT \"currencyCodeFrom\", \"currencyCodeTo\", \"convertVal\", \"resultVal\", \"dateConvert\"" +
                " FROM public.\"historyQuery\" %s;";

        String strFilters = "";

        if (!currencyCodeFrom.isEmpty() && currencyCodeFrom != null)
        {
            strFilters += String.format(" where \"currencyCodeFrom\" = '%s'", currencyCodeFrom);
        }

        if (!currencyCodeTo.isEmpty() && currencyCodeTo != null)
        {
            strFilters += strFilters.length() > 0 ? String.format(" AND \"currencyCodeTo\" = '%s'", currencyCodeTo) :
                                                    String.format(" where \"currencyCodeTo\" = '%s'", currencyCodeTo);
        }

        if (!dateConvert.isEmpty() && dateConvert != null)
        {
            strFilters += strFilters.length() > 0 ? String.format(" AND \"dateConvert\" = '%s'", dateConvert) :
                                                    String.format(" where \"dateConvert\" = '%s'", dateConvert);
        }

        strQuery = String.format(strQuery, strFilters);

        return jdbcTemplate.query(strQuery, new HistoryQueryMapper());
    }
}
