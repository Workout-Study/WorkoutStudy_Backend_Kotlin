package com.fitmate.fitgroupservice.dto.group

import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader

data class FitGroupFilterResponse(
    val fitLeader: FitLeader?,
    val fitGroup: FitGroup,
    val presentFitMateCount: Int
)
