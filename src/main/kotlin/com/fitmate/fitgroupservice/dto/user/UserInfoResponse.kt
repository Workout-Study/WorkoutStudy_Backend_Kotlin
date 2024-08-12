package com.fitmate.fitgroupservice.dto.user

import com.fitmate.fitgroupservice.utils.DateParseUtils
import java.time.Instant

data class UserInfoResponse(
    val userId: Int,
    val nickname: String,
    val state: Boolean,
    private val createdAtInstant: Instant,
    private val updatedAtInstant: Instant
) {
    val createdAt: String = DateParseUtils.instantToString(createdAtInstant)
    val updatedAt: String = DateParseUtils.instantToString(updatedAtInstant)
}
