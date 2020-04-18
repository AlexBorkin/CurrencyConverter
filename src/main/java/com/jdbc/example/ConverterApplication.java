package com.jdbc.example;


import com.jdbc.example.entity.Currency;
import com.jdbc.example.service.CurrencyService;
import org.apache.catalina.core.StandardContext;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ConverterApplication
{
	@Autowired
	public static DataSource dataSource;

	public static List<Currency> currencyListGlobal;

	public static void main(String[] args)
	{
		Flyway flyway = Flyway.configure().dataSource(dataSource).load();

		ApplicationContext ctx = SpringApplication.run(ConverterApplication.class, args);

		CurrencyService	currencyServiceApp = (CurrencyService) ctx.getBean("currencyService");
		currencyListGlobal = currencyServiceApp.listCurrency();
	}
}
