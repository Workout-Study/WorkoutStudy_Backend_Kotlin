package com.fitmate.fitgroupservice.dto.mate

import com.fitmate.fitgroupservice.utils.DateParseUtils
import java.time.Instant

data class FitLeaderDetailDto(
    val fitLeaderUserId: Int?,
    val fitLeaderUserNickname: String?,
    private val createdAtInstant: Instant?
) {
    val createdAt: String = DateParseUtils.instantToString(createdAtInstant)
}