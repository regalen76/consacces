package com.regalen.consacces.utils


enum class DateTime(getFormatString: String) {
    DATE("dd-MM-yyyy"),
    UI_SHORT_DATE("dd MMM yy"),
    LONG_DATE_TIME("dd-MMM-yy HH:mm:ss")
}

enum class Regex(val getRegex: kotlin.text.Regex) {
    COL_NAME_FORMAT("^[a-zA-Z_0-9]*$".toRegex()),
    ORDER_BY_QUERY("""^(\w+(\.\w*)?)$""".toRegex())
}

enum class Paging(val getMaxPage: Int) {
    NO_PAGING(Int.MAX_VALUE)
}