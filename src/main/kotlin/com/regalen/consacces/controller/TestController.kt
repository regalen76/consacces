package com.regalen.consacces.controller

import com.regalen.consacces.controller.extensions.BaseController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController : BaseController() {
    @GetMapping("/test")
    fun someTest() : String {
        logger.info("Test dulu")
        return "hello world"
    }
}