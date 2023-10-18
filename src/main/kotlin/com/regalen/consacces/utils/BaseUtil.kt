package com.regalen.consacces.utils

import com.regalen.consacces.utils.exceptions.DataFormatException
import org.springframework.context.MessageSource
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.util.*

/**
 *  Error code message mapping
 *  @param code internal (AP-) error code message mapping
 *  @param message exception message thrown, if code not available
 */
fun MessageSource.getErrorMsg(code: String, message: String? = null) = MessageResources.supportedLanguage.map {
        (language, locale) -> if (!message.isNullOrBlank()) {
    language to "${getMessage(code, null, locale)} $message"
} else { language to getMessage(code, null, locale) }
}.toMap()

/**
 * @return SQL order query based on pageable
 *
 * sample "ORDER BY columname ASC"
 * */
fun Pageable?.getSortQuery(defaultColumnName: String = "", defaultSortDirection: Sort.Direction): String {
    val orderQuery = defaultColumnName.trim().uppercase(Locale.getDefault()).let{
        if (it.matches(Regex.ORDER_BY_QUERY.getRegex))
            " ORDER BY $it ${defaultSortDirection.name} "
        else
            ""
    }

    return when{
        this == null -> {
            orderQuery
        }
        this.sort == Sort.unsorted() -> {
            orderQuery
        }
        else -> {
            val order = this.sort.toList()[0]
            if (!Regex.COL_NAME_FORMAT.getRegex.matches(order.property)) throw DataFormatException(fieldCauseError = order.property)

            "ORDER BY ${order.property} ${order.direction.name} "
        }
    }
}
fun Pageable?.queryPagination(): String{
    if (this == null || (this.pageSize == Paging.NO_PAGING.getMaxPage && this.pageNumber == Paging.NO_PAGING.getMaxPage))
        return ""
    return "OFFSET ${this.offset} ROWS FETCH NEXT ${this.pageSize} ROWS ONLY "
}