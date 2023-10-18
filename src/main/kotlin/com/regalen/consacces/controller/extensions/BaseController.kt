package com.regalen.consacces.controller.extensions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseController {
    protected val logger: Logger = LoggerFactory.getLogger(this.javaClass)
}