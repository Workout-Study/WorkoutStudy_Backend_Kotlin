package com.fitmate.fitgroupservice.dto.group

import jakarta.validation.constraints.NotEmpty

data class RegisterFitGroupRequest(
    @field:NotEmpty val requestUserId: String,
    @field:NotEmpty val fitGroupName: String,
    val penaltyAmount: Int,
    @field:NotEmpty val penaltyAccountBankCode: String,
    @field:NotEmpty val penaltyAccountNumber: String,
    val category: Int,
    val introduction: String?,
    val cycle: Int?,
    val frequency: Int,
    val maxFitMate: Int,
    val multiMediaEndPoints: List<String>?
)
