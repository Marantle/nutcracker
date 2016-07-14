package com.marantle.nutcracker.controller;

import com.marantle.nutcracker.repository.NutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Marko on 14.7.2016.
 */
@Controller
public class SalaryController {

    @Autowired
    NutRepo repo;

    @RequestMapping(value = "/salaries", method = RequestMethod.GET)
    public String list(Model model) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("personId", "Person Id");
        map.put("workDate", "Work Date");
        map.put("totalSalaryFormatted", "Days salary");
        map.put("regularSalaryFormatted", "Base salary");
        map.put("eveningSalaryFormatted", "Evening salary");
        map.put("eveningCompensationFormatted", "Evening compensation");
        map.put("overtimeSalaryFormatted", "Overtime salary");
        map.put("overtimeCompensationFormatted", "Overtime compensation");
        map.put("totalUncompensatedSalaryFormatted", "Salary w/o compensation");


        map.keySet().forEach(System.err::println);
        map.values().forEach(System.err::println);
        model.addAttribute("headers", map);
        model.addAttribute("pageHeader", "All daily salaries");
        model.addAttribute("headerFields", map.keySet());
        model.addAttribute("headerTexts", map.values());
        model.addAttribute("dataList", repo.listSalaries());

        return "generictable";
    }
}
