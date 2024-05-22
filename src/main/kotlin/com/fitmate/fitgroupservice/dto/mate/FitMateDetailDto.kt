package com.fitmate.fitgroupservice.dto.mate

import java.time.Instant

data class FitMateDetailDto(
    val fitMateId: Long,
    val fitMateUserId: Int,
    val createdAt: Instant
)
