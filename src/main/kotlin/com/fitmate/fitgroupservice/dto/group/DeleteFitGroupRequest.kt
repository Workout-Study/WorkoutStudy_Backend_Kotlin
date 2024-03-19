package com.fitmate.fitgroupservice.dto.group

import jakarta.validation.constraints.NotEmpty

data class DeleteFitGroupRequest(@field:NotEmpty val requestUserId: String)