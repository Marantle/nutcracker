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
public class WorkShiftController {

    @Autowired
    NutRepo repo;

    @RequestMapping(value = "/workshifts", method = RequestMethod.GET)
    public String list(Model model) {
        //for convenience, use an ordered map to list field and headers for the table
        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("personId", "Person Id");
        headers.put("workDate", "Work date");
        headers.put("start", "Shift start");
        headers.put("end", "Shift end");

        model.addAttribute("pageHeader", "All workshifts");
        model.addAttribute("headerFields", headers.keySet());
        model.addAttribute("headerTexts", headers.values());
        model.addAttribute("dataList", repo.listWorkShifts());

        return "generictable";
    }
}
