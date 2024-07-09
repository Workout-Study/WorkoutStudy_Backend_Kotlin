package com.fitmate.fitgroupservice.dto.group

import jakarta.validation.constraints.NotEmpty

data class UpdateFitGroupRequest(
    val requestUserId: Int,
    @field:NotEmpty val fitGroupName: String,
    val penaltyAmount: Int,
    val category: Int,
    val introduction: String?,
    val cycle: Int? = 1,
    val frequency: Int,
    val maxFitMate: Int,
    val multiMediaEndPoints: List<String>?
)
