package com.fitmate.fitgroupservice.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

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
        logger?.info("NullPointerException ", nullPointerException)
        return ResponseEntity.badRequest().body(nullPointerException.message)
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
}