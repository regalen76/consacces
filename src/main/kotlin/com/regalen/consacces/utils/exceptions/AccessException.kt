package com.regalen.consacces.utils.exceptions

abstract class AccessException(
    override val code: String,
    open val fieldCauseError: String = ""
) : BaseException(fieldCauseError)

open class CredentialsException(
    override val code: String = "400-01",
    override val fieldCauseError: String = ""
) : AccessException(fieldCauseError, code)

open class EmptyCredentialsException(
    override val code: String = "400-02",
    override val fieldCauseError: String = ""
) : AccessException(fieldCauseError, code)

open class JwtExpiredException(
    override val code: String = "400-03",
    override val fieldCauseError: String = ""
) : AccessException(fieldCauseError, code)

open class InvalidJwtSignatureException(
    override val code: String = "400-04",
    override val fieldCauseError: String = ""
) : AccessException(fieldCauseError, code)

open class InvalidJwtTokenException(
    override val code: String = "400-05",
    override val fieldCauseError: String = ""
) : AccessException(fieldCauseError, code)

open class JwtUnsupportedException(
    override val code: String = "400-06",
    override val fieldCauseError: String = ""
) : AccessException(fieldCauseError, code)

open class IllegalJwtClaimsArgumentException(
    override val code: String = "400-07",
    override val fieldCauseError: String = ""
) : AccessException(fieldCauseError, code)



