package com.fitmate.fitgroupservice.dto.mate

import jakarta.validation.constraints.NotEmpty

data class RegisterMateRequest(
    @field:NotEmpty val requestUserId: String,
    val fitGroupId: Long
)
