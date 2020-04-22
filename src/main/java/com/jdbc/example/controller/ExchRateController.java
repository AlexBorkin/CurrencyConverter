package com.jdbc.example.controller;

import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExchRateController
{
    private ExchRateService exchRateService;

    @Autowired
    public ExchRateController(ExchRateService exchRateService)
    {
        this.exchRateService = exchRateService;
    }

    @GetMapping("/exchrates")
    public String getAll(Model model)
    {
        List<ExchRate> exchRateList = exchRateService.getAll();

        model.addAttribute("currency", exchRateList);

        return "exchrates";
    }
}
