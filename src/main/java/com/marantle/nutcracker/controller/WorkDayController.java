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
public class WorkDayController {
    @Autowired
    NutRepo repo;

    @RequestMapping(value = "/workdays", method = RequestMethod.GET)
    public String list(Model model){
        String[] headerFields ={
            "personId",      "workDate",      "allHours",
            "regularHours",  "eveningHours",  "overtimeHours"
        };
        String[] headerTexts ={
            "Person Id",     "Work date",     "Total hours",
            "Regular hours", "Evening hours", "Overtime hours"
        };

        model.addAttribute("pageHeader", "All workdays");
        model.addAttribute("headerFields", headerFields);
        model.addAttribute("headerTexts", headerTexts);
        model.addAttribute("dataList", repo.listWorkDays());

        return "generictable";
    }
}
