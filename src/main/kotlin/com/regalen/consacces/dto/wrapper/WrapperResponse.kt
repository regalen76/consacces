package com.regalen.consacces.dto.wrapper

import com.fasterxml.jackson.annotation.JsonProperty

data class WrapperResponse<T> (
    @field:JsonProperty("response_schema")
    val responseSchema : T,

    @field:JsonProperty("error_schema")
    val errorSchema: ErrorSchema
) {
    companion object {
        fun <T> successResponse(data: T): WrapperResponse<T> {
            return WrapperResponse(
                data,
                ErrorSchema("200", "OK", "OK")
            )
        }
    }
}