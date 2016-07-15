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


@Controller
public class SalaryController {

    @Autowired
    NutRepo repo;

    @RequestMapping(value = "/salaries", method = RequestMethod.GET)
    public String salaries(Model model) {
        //for convenience, use an ordered map to list field and headers for the table
        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("personId", "Person Id");
        headers.put("workDate", "Work Date");
        headers.put("totalSalaryFormatted", "Days salary");
        headers.put("regularSalaryFormatted", "Base salary");
        headers.put("eveningSalaryFormatted", "Evening salary");
        headers.put("overtimeSalaryFormatted", "Overtime salary");
        headers.put("totalUncompensatedSalaryFormatted", "Salary w/o compensation");
        headers.put("eveningCompensationFormatted", "Evening compensation");
        headers.put("overtimeCompensationFormatted", "Overtime compensation");

        model.addAttribute("headers", headers);
        model.addAttribute("pageHeader", "All daily salaries");
        model.addAttribute("headerFields", headers.keySet());
        model.addAttribute("headerTexts", headers.values());
        model.addAttribute("dataList", repo.listSalaries());

        return "generictable";
    }

    @RequestMapping(value = "/salaries/monthly", method = RequestMethod.GET)
    public String monthlySalaries(Model model) {
        //for convenience, use an ordered map to list field and headers for the table
        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("personId", "Person Id");
        headers.put("personName", "Person name");
        headers.put("monthOfYear", "Month");
        headers.put("totalSalaryFormatted", "Days salary");
        headers.put("regularSalaryFormatted", "Base salary");
        headers.put("eveningSalaryFormatted", "Evening salary");
        headers.put("overtimeSalaryFormatted", "Overtime salary");
        headers.put("totalUncompensatedSalaryFormatted", "Salary w/o compensation");
        headers.put("eveningCompensationFormatted", "Evening compensation");
        headers.put("overtimeCompensationFormatted", "Overtime compensation");

        model.addAttribute("headers", headers);
        model.addAttribute("pageHeader", "Monthly salaries");
        model.addAttribute("headerFields", headers.keySet());
        model.addAttribute("headerTexts", headers.values());
        model.addAttribute("dataList", repo.listMonthlySalaries());

        return "generictable";
    }
}
