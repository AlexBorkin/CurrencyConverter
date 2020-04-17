package com.jdbc.example.mappers;

import com.jdbc.example.entity.HistoryQuery;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryQueryMapper implements RowMapper<HistoryQuery>
{
    @Override
    public HistoryQuery mapRow(ResultSet resultSet, int i) throws SQLException
    {
        HistoryQuery historyQuery = new HistoryQuery();

        historyQuery.setCurrencyCodeFrom(resultSet.getString("currencyCodeFrom"));
        historyQuery.setCurrencyCodeTo(resultSet.getString("currencyCodeTo"));
        historyQuery.setConvertVal(resultSet.getDouble("convertVal"));
        historyQuery.setResultVal(resultSet.getDouble("resultVal"));
        historyQuery.setDateConvert(resultSet.getDate("dateConvert"));

        return historyQuery;
    }
}
