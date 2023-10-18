package com.regalen.consacces.dto.wrapper

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorSchema (
    @JsonProperty("message_en")
    val messageEN: String,

    @JsonProperty("message_in")
    val messageIN: String?,

    val code : String
)