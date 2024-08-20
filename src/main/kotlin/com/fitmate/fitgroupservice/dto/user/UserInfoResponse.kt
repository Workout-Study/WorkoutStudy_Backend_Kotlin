package com.fitmate.fitgroupservice.dto.user

import com.fitmate.fitgroupservice.utils.DateParseUtils
import org.springframework.util.StringUtils
import java.time.Instant

data class UserInfoResponse(
    val userId: Int,
    val nickname: String,
    val state: Boolean,
    val imageUrl: String?,
    val createdAt: String,
    val updatedAt: String?
) {
    val createdAtInstant = DateParseUtils.stringToInstant(createdAt)
    val updatedAtInstant: Instant? =
        if (StringUtils.hasText(updatedAt)) DateParseUtils.stringToInstant(updatedAt!!) else null
}
