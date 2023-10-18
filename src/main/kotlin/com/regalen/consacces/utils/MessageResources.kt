package com.regalen.consacces.utils

import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceAware
import java.util.*

object MessageResources : MessageSourceAware {
    private var msgSource: MessageSource? = null
    val supportedLanguage = mapOf(
        "indonesian" to Locale("in"),
        "english" to Locale("en")
    )

    override fun setMessageSource(messageSource: MessageSource) {
        this.msgSource = messageSource
    }
}