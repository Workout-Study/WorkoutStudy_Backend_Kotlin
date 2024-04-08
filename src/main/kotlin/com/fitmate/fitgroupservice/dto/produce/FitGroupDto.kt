package com.fitmate.fitgroupservice.dto.produce

data class FitGroupDto(
    val fitGroupId: Long,
    val fitGroupName: String,
    val cycle: Int,
    val frequency: Int,
    val state: Boolean
)
