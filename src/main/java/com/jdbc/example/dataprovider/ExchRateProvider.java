package com.jdbc.example.dataprovider;

import com.jdbc.example.entity.ExchRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
public class ExchRateProvider
{
    List<ExchRate> exchRateList;

    @Value("${ExchRateProvider.pathFile}")
    private String pathFile;

    public List<ExchRate> getExchRateList()
    {
        return exchRateList;
    }

    public void setExchRateList(List<ExchRate> exchRateList)
    {
        this.exchRateList = exchRateList;
    }

    public ExchRateProvider() {
    }

    public void fillExchRateList() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder        builder = factory.newDocumentBuilder();

        Document document = builder.parse(pathFile);

        NodeList nodeListValute = document.getDocumentElement().getElementsByTagName("Valute");

        for (int i = 0; i < nodeListValute.getLength(); i++)
        {
            Node valuteNode = nodeListValute.item(i);
        }
    }
}
