package com.marantle.nutcracker.controller;

import com.marantle.nutcracker.repository.NutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Marko on 14.7.2016.
 */
@Controller
public class PersonController {

    @Autowired
    NutRepo repo;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String list(Model model){
        String[] headerFields = {"personId",    "personName"};
        String[] headerTexts =  {"Person Id",   "Person Name"};

        model.addAttribute("pageHeader", "All persons");
        model.addAttribute("headerFields", headerFields);
        model.addAttribute("headerTexts", headerTexts);
        model.addAttribute("dataList", repo.listPersons());
        return "generictable";
    }
}
