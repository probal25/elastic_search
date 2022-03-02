package com.probal.test_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IndexController {
    @ResponseBody
    @RequestMapping("index")
    public String index() {
        return "INDEX";
    }
}
