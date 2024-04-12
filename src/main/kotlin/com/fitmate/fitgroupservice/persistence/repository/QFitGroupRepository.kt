package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupFilterResponse
import org.springframework.data.domain.PageRequest

interface QFitGroupRepository {
    fun filterFitGroup(
        fitGroupFilterRequest: FitGroupFilterRequest,
        pageable: PageRequest
    ): List<FitGroupFilterResponse>

    fun filterFitGroupByUserId(userId: String): List<FitGroupFilterResponse>
}