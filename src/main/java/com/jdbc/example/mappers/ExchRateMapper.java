package com.jdbc.example.mappers;

import com.jdbc.example.entity.ExchRate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchRateMapper implements RowMapper<ExchRate>
{
    @Override
    public ExchRate mapRow(ResultSet resultSet, int i) throws SQLException
    {
        ExchRate exchRate = new ExchRate();
        
        exchRate.setCurrencyCode(resultSet.getString("CurrencyCode"));
        exchRate.setDateRate(resultSet.getDate("DateRate"));
        exchRate.setCurrencyRate(resultSet.getDouble("CurrencyRate"));
        exchRate.setCurrencyDescription(resultSet.getString("CurrencyDescription"));
        exchRate.setFullDescription(resultSet.getString("CurrencyDescription") + " (" + resultSet.getString("CurrencyCode") + ")");

        return exchRate;
    }
}
