package com.fitmate.fitgroupservice.dto.mate

import com.fitmate.fitgroupservice.utils.DateParseUtils
import java.time.Instant

data class FitMateDetailDto(
    val fitMateId: Long,
    val fitMateUserId: Int,
    val fitMateUserNickname: String?,
    private val createdAtInstant: Instant
) {
    val createdAt: String = DateParseUtils.instantToString(createdAtInstant)
}
