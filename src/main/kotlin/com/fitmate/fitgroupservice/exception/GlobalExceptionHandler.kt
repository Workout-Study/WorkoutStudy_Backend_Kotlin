package com.fitmate.fitgroupservice.exception

import com.fitmate.fitgroupservice.utils.SenderUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val senderUtils: SenderUtils
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(badRequestException: BadRequestException): ResponseEntity<String> {
        logger?.info("BadRequestException ", badRequestException)
        return ResponseEntity.badRequest().body(badRequestException.message)
    }

    @ExceptionHandler(NullPointerException::class)
    fun nullPointerException(nullPointerException: NullPointerException): ResponseEntity<String> {
        logger?.error("NullPointerException ", nullPointerException)
        senderUtils.sendSlack("NullPointerException $nullPointerException")
        return ResponseEntity.internalServerError().body(nullPointerException.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(methodArgumentNotValidException: MethodArgumentNotValidException): ResponseEntity<String> {
        logger?.info("MethodArgumentNotValidException", methodArgumentNotValidException)
        return ResponseEntity.badRequest().body(methodArgumentNotValidException.message)
    }

    @ExceptionHandler(ResourceAlreadyExistException::class)
    fun resourceAlreadyExistException(resourceAlreadyExistException: ResourceAlreadyExistException): ResponseEntity<String> {
        logger?.info("ResourceAlreadyExistException", resourceAlreadyExistException)
        return ResponseEntity.badRequest().body(resourceAlreadyExistException.message)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(resourceNotFoundException: ResourceNotFoundException): ResponseEntity<String> {
        logger?.info("ResourceNotFoundException", resourceNotFoundException)
        return ResponseEntity.badRequest().body(resourceNotFoundException.message)
    }

    @ExceptionHandler(NotExpectResultException::class)
    fun notExpectResultException(notExpectResultException: NotExpectResultException): ResponseEntity<String> {
        logger?.info("NotExpectResultException", notExpectResultException)
        return ResponseEntity.badRequest().body(notExpectResultException.message)
    }

    @ExceptionHandler(SendMessageException::class)
    fun sendMessageException(sendMessageException: SendMessageException): ResponseEntity<String> {
        logger?.error("SendMessageException", sendMessageException)
        return ResponseEntity.badRequest().body(sendMessageException.message)
    }

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception): ResponseEntity<String> {
        logger?.error("Exception ", exception)
        senderUtils.sendSlack("Exception $exception")
        return ResponseEntity.internalServerError().body(exception.message)
    }
}