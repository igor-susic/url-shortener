package com.demo.urlshortener.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelpController {

    @RequestMapping(path = "/help", method = RequestMethod.GET)
    public String help() {
        return "help.html";
    }
}
