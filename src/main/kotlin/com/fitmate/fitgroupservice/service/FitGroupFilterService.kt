package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.filter.FitGroupDetailsResponse
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupDetailResponse
import org.springframework.data.domain.Slice

interface FitGroupFilterService {


    /**
     * Get Filtered Fit group service
     *
     * @param fitGroupFilterRequest filter condition and pageable
     * @return content and slice data
     */
    fun getFitGroupListByFilter(fitGroupFilterRequest: FitGroupFilterRequest): Slice<FitGroupDetailResponse>

    /**
     * Get Filtered Fit group by user id service
     *
     * @param userId condition of user id
     * @return content and slice data
     */
    fun getFitGroupListByUserId(userId: String): FitGroupDetailsResponse
}