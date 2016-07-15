package com.marantle.nutcracker.controller;

import com.marantle.nutcracker.repository.NutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class PersonController {

    @Autowired
    NutRepo repo;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String list(Model model){
        //for convenience, use an ordered map to list field and headers for the table
        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("personId", "Person Id");
        headers.put("personName", "Person Name");

        model.addAttribute("pageHeader", "All persons");
        model.addAttribute("headerFields", headers.keySet());
        model.addAttribute("headerTexts", headers.values());
        model.addAttribute("dataList", repo.listPersons());
        return "generictable";
    }
}
