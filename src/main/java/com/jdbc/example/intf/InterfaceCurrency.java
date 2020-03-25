package com.jdbc.example.intf;

import com.jdbc.example.entity.Currency;

import javax.sql.DataSource;
import java.util.List;

public interface InterfaceCurrency
{
    public void setDataSource(DataSource dataSource);

    public void create(Currency currency);

    public Currency getCurrency(String currencyCode);

    public List<Currency> listCurrency();
}
