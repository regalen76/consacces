package com.regalen.consacces.utils.exceptions

abstract class BaseException(msg: String): RuntimeException(msg) {
    abstract val code: String
}