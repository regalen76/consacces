package com.regalen.consacces.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test")
    fun someTest() : String {
        return "Hello Bruh"
    }
}