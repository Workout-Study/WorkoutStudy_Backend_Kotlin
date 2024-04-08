package com.fitmate.fitgroupservice.exception

class BadRequestException(override val message: String) : RuntimeException(message) {
}