package com.fitmate.fitgroupservice.dto.group

import java.time.Instant

data class FitGroupDetailResponse(val fitGroupId: Long,
                                  val fitLeaderUserId: String,
                                  val fitGroupName: String,
                                  val penaltyAmount: Int,
                                  val category: Int,
                                  val introduction: String?,
                                  val cycle: Int,
                                  val frequency: Int,
                                  val createdAt: Instant) {
}