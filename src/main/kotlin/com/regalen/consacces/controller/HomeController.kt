package com.regalen.consacces.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    @RequestMapping("/")
    fun index(): String {
        return "redirect:/swagger-ui.html"
    }
}