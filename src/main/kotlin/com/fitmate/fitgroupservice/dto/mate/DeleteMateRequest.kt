package com.fitmate.fitgroupservice.dto.mate

import jakarta.validation.constraints.NotEmpty

data class DeleteMateRequest(@field:NotEmpty val requestUserId: Int)
