package com.fitmate.fitgroupservice.exception

class ResourceNotFoundException(override val message: String) : RuntimeException(message) {
}