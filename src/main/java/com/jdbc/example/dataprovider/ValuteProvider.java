package com.jdbc.example.dataprovider;

import com.jdbc.example.entity.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValuteProvider
{
    private List<Currency>                 currencyList;
    private Map<String, ValuteAttribute> currencyAttributeMap = new HashMap<>();
    private Date                           currentDate;

    @Value("${ExchRateProvider.pathFile}")
    private String pathFile;

    public ValuteProvider()
    {
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Map<String, ValuteAttribute> getCurrencyAttributeMap() {
        return currencyAttributeMap;
    }

    public void setCurrencyAttributeMap(Map<String, ValuteAttribute> currencyAttributeMap) {
        this.currencyAttributeMap = currencyAttributeMap;
    }

    public List<Currency> getCurrencyList()
    {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList)
    {
        this.currencyList = currencyList;
    }

    public Map<String, ValuteAttribute> getMapDataFromSite() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder        = factory.newDocumentBuilder();
        Document        document       = builder.parse(pathFile);
        Element         root           = document.getDocumentElement();
        String          currentDateStr;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        currentDateStr = root.getAttributes().getNamedItem("Date").getNodeValue();

        try
        {
            currentDate = simpleDateFormat.parse(currentDateStr);
        }
        catch (ParseException e)
        {
            System.out.println("Ошибка конвертации даты!");
        }

        NodeList nodeListValute = root.getElementsByTagName("Valute");

        for (int i = 0; i < nodeListValute.getLength(); i++)
        {
            ValuteAttribute valuteAttribute = new ValuteAttribute();

            Node            valuteNode     = nodeListValute.item(i);
            String          valuteId       = valuteNode.getAttributes().getNamedItem("ID").getNodeValue();
            NodeList        childList      = valuteNode.getChildNodes();

            valuteAttribute.setNumCode(childList.item(0).getTextContent());
            valuteAttribute.setCharCode(childList.item(1).getTextContent());
            valuteAttribute.setNominal(childList.item(2).getTextContent());
            valuteAttribute.setName(childList.item(3).getTextContent());
            valuteAttribute.setValue(Double.parseDouble(childList.item(4).getTextContent().replace(",", ".")));
            valuteAttribute.setCurDate(currentDate);

            currencyAttributeMap.put(valuteId, valuteAttribute);
        }

        return currencyAttributeMap;
    }
}

