package com.marantle.nutcracker.controller;

/**
 * Created by Marko on 13.7.2016.
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    String index(){
        return "index";
    }
}