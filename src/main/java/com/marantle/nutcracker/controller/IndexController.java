package com.marantle.nutcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/")
    String index(){
        return "index";
    }

    // Error page
    @RequestMapping("/error")
    public String error(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("errorCode", response.getStatus());
        model.addAttribute("pageHeader", "Uh oh");
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        String errorMessage = errorAttributes.getErrorAttributes(requestAttributes, false).get("error").toString();
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}