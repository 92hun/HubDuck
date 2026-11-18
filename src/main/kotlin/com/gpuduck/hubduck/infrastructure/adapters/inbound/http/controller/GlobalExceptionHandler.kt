package com.gpuduck.hubduck.infrastructure.adapters.inbound.http.controller

import com.gpuduck.hubduck.domain.exception.ClusterNotFoundException
import com.gpuduck.hubduck.domain.exception.DuplicateClusterNameException
import com.gpuduck.hubduck.infrastructure.adapters.inbound.http.controller.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(ClusterNotFoundException::class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	fun handleNotFound(e: ClusterNotFoundException) = ErrorResponse(e.message.orEmpty())

	@ExceptionHandler(DuplicateClusterNameException::class)
	@ResponseStatus(HttpStatus.CONFLICT)
	fun handleDuplicate(e: DuplicateClusterNameException) = ErrorResponse(e.message.orEmpty())

	@ExceptionHandler(IllegalArgumentException::class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	fun handleBadRequest(e: IllegalArgumentException) = ErrorResponse(e.message.orEmpty())
}
