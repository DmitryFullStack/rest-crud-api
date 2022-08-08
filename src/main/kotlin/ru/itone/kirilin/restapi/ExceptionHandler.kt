package ru.itone.kirilin.restapi

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest


inline fun <reified T> T.logger() : Logger =
    getLogger(T::class.java)

@Slf4j
@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultExceptionHandler(
        ex: Exception,
        req: HttpServletRequest
    ): String? {
        logger().error("${ex.message} at request ${req.requestURI}")
        return "${ex.message} at request ${req.requestURI}"
    }
}
