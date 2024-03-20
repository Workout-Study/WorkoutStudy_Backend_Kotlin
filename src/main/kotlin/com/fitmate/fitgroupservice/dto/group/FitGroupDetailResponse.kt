package com.fitmate.fitgroupservice.dto.group

import java.time.Instant

data class FitGroupDetailResponse(
    val fitGroupId: Long,
    val fitLeaderUserId: String,
    val fitGroupName: String,
    val penaltyAmount: Int,
    val penaltyAccountBankCode: String,
    val penaltyAccountNumber: String,
    val category: Int,
    val introduction: String?,
    val cycle: Int,
    val frequency: Int,
    val createdAt: Instant,
    val maxFitMate: Int,
    val presentFitMateCount: Int,
    val multiMediaEndPoints: List<String>
)