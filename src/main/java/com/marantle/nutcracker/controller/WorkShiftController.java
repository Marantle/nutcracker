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
public class WorkShiftController {

    @Autowired
    NutRepo repo;

    @RequestMapping(value = "/workshifts", method = RequestMethod.GET)
    public String list(Model model) {
        String[] headerFields = {"personId",  "workDate",  "start",       "end"};
        String[] headerTexts =  {"Person Id", "Work date", "Shift start", "Shift end"};

        model.addAttribute("pageHeader", "All workshifts");
        model.addAttribute("headerFields", headerFields);
        model.addAttribute("headerTexts", headerTexts);
        model.addAttribute("dataList", repo.listWorkShifts());

        return "generictable";
    }
}
