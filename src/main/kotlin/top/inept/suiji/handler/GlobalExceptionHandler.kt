package top.inept.suiji.handler

import org.slf4j.LoggerFactory
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import top.inept.suiji.base.ValidationError
import top.inept.suiji.extensions.get


@RestControllerAdvice
class GlobalExceptionHandler(
    private val messages: MessageSourceAccessor
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    fun exceptionHandler(ex: Exception): ResponseEntity<String> {
        logger.error(ex.message, ex)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.message ?: messages["message.common.unknown_error"])
    }

    @ExceptionHandler
    fun exceptionHandler(ex: HttpMessageNotReadableException): ResponseEntity<String> {
        logger.error(ex.message, ex)

        /*        //json解析时缺少字段报错
                if (ex.cause is MissingKotlinParameterException) {
                    return ApiResponse.error(messages["message.common.missing_json_field", ex.message ?: "Null"])
                }*/
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.message ?: messages["message.common.unknown_error"])
    }

    /**
     * Validated的验证错误
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun exceptionHandler(ex: MethodArgumentNotValidException): ResponseEntity<List<ValidationError>> {
        val errors = ex.bindingResult.fieldErrors.map { fe ->
            ValidationError(
                field = fe.field,
                message = messages[fe.defaultMessage ?: "message.common.illegal_parameters"],
            )
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errors)
    }
}