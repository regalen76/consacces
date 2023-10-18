package com.regalen.consacces.utils.exceptions

abstract class DataException (
    override val code: String,
    open val fieldCauseError: String = ""
) : BaseException(fieldCauseError)

open class DataUsedException(
    override val code: String = "409-01",
    override val fieldCauseError: String = ""
) : DataException(fieldCauseError, code)

open class DataNotFoundException(
    override val code: String = "409-02",
    override val fieldCauseError: String = ""
) : DataException(fieldCauseError, code)

open class DataSaveFailedException(
    override val code: String = "409-03",
    override val fieldCauseError: String = ""
) : DataException(fieldCauseError, code)

open class DataUpdateFailedException(
    override val code: String = "409-04",
    override val fieldCauseError: String = ""
) : DataException(fieldCauseError, code)

open class DataDeleteFailedException(
    override val code: String = "409-05",
    override val fieldCauseError: String = ""
) : DataException(fieldCauseError, code)

open class DataFormatException(
    override val code: String = "409-06",
    override val fieldCauseError: String = ""
) : DataException(fieldCauseError, code)
