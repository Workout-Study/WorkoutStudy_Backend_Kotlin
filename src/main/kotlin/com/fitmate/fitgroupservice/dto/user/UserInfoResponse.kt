package com.fitmate.fitgroupservice.dto.user

import java.time.Instant

data class UserInfoResponse(
    val userId: Int,
    val nickname: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
