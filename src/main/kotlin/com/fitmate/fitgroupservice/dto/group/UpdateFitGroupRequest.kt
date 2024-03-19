package com.fitmate.fitgroupservice.dto.group

import jakarta.validation.constraints.NotEmpty

data class UpdateFitGroupRequest(@field:NotEmpty val requestUserId: String,
                                 @field:NotEmpty val fitGroupName: String,
                                 val penaltyAmount: Int,
                                 val category: Int,
                                 val introduction: String?,
                                 val cycle: Int?,
                                 val frequency: Int)
