package com.fitmate.fitgroupservice.dto.mate

import java.time.Instant

data class FitLeaderDetailDto(
    val fitLeaderUserId: Int?,
    val createdAt: Instant?
) {
}