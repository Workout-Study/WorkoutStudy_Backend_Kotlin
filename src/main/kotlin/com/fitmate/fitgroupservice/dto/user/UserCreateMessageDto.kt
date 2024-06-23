package com.fitmate.fitgroupservice.dto.user

data class UserCreateMessageDto(
    val userId: Int,
    val nickname: String,
    val state: Boolean,
    val createdAt: String,
    val updatedAt: String
)
