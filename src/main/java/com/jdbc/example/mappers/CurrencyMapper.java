package com.jdbc.example.mappers;

import com.jdbc.example.entity.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CurrencyMapper implements RowMapper<Currency>
{
    @Override
    public Currency mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Currency currency = new Currency();

        currency.setCurrencyCode(resultSet.getString("CurrencyCode"));
        currency.setDescription(resultSet.getString("Description"));
        currency.setNominal(resultSet.getInt("Nominal"));
        currency.setNumCode(resultSet.getString("NumCode"));
        currency.setFullDescription(resultSet.getString("Description") + " (" + resultSet.getString("CurrencyCode") + ")");

        return currency;
    }
}
