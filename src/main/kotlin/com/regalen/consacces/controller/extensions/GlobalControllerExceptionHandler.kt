package com.regalen.consacces.controller.extensions

import com.regalen.consacces.dto.wrapper.ErrorSchema
import com.regalen.consacces.dto.wrapper.WrapperResponse
import com.regalen.consacces.utils.exceptions.AccessException
import com.regalen.consacces.utils.exceptions.DataException
import com.regalen.consacces.utils.getErrorMsg
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerExceptionHandler(val msgSource: MessageSource) {

    @ExceptionHandler(DataException::class)
    @ResponseBody
    fun conflictException(e: DataException): ResponseEntity<WrapperResponse<Any?>> {
        val errorEn = msgSource.getErrorMsg(e.code)["english"] ?: throw NullPointerException()
        val errorIn = msgSource.getErrorMsg(e.code)["indonesian"] ?: throw NullPointerException()
        val errorSchema = ErrorSchema(errorEn, errorIn, e.code)

        e.printStackTrace()

        return ResponseEntity(WrapperResponse(null, errorSchema), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AccessException::class)
    @ResponseBody
    fun accessException(e: AccessException): ResponseEntity<WrapperResponse<Any?>> {
        val errorEn = msgSource.getErrorMsg(e.code)["english"] ?: throw NullPointerException()
        val errorIn = msgSource.getErrorMsg(e.code)["indonesian"] ?: throw NullPointerException()
        val errorSchema = ErrorSchema(errorEn, errorIn, e.code)
        e.printStackTrace()
        return ResponseEntity(WrapperResponse(null, errorSchema), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(e: Exception): ResponseEntity<WrapperResponse<Any?>> {
        val errorSchema = ErrorSchema(
            e.message ?: throw NullPointerException(),
            e.message,
            HttpStatus.INTERNAL_SERVER_ERROR.value().toString()
        )
        e.printStackTrace()
        return ResponseEntity(WrapperResponse(null, errorSchema), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}