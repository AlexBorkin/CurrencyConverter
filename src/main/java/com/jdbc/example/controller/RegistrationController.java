package com.jdbc.example.controller;

import com.jdbc.example.entity.User;
import com.jdbc.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model)
    {
        model.addAttribute("error", " ");
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(String userName, String password, Model model)
    {
        String retValueEndPoint = "main";
        User userExist = null;

        if (userName == null || userName.isEmpty())
        {
            model.addAttribute("error", "Не указан пароль!");
            retValueEndPoint = "registration";
        }
        else
        {
            userExist = userService.getUserByUserName(userName);

            if (userExist == null)
            {
                try
                {
                    userService.create(userName, password);
                    retValueEndPoint = "redirect:/login";
                }
                catch (Exception exceptionMessage)
                {
                    model.addAttribute("error", exceptionMessage);
                    retValueEndPoint = "registration";
                }
            }
            else
            {
                model.addAttribute("error", "Пользователь уже существует");
                retValueEndPoint = "registration";
            }
        }

        return retValueEndPoint;
    }
}
