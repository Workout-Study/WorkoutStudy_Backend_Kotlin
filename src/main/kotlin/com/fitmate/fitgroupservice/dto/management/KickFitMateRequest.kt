package com.fitmate.fitgroupservice.dto.management

import jakarta.validation.constraints.NotEmpty

data class KickFitMateRequest(@field:NotEmpty val requestUserId: Int)
