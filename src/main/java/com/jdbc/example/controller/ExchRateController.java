package com.jdbc.example.controller;

import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.service.ExchRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
public class ExchRateController
{
    private ExchRateService exchRateService;

    @Autowired
    public ExchRateController(ExchRateService exchRateService)
    {
        this.exchRateService = exchRateService;
    }

    @GetMapping("/ExchRate")
    public String getAll(Model model)
    {
        List<ExchRate> exchRateList = exchRateService.getAll();

        model.addAttribute("currency", exchRateList);

        return "exchrates";
    }
}
