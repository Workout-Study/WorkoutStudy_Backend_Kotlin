package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.filter.FitGroupDetailsResponse
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupDetailResponse
import com.fitmate.fitgroupservice.service.FitGroupFilterService
import jakarta.validation.Valid
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FitGroupFilterController(private val fitGroupFilterService: FitGroupFilterService) {

    /**
     * Get Filtered Fit group inbound api
     *
     * @param fitGroupFilterRequest filter condition and pageable
     * @return content and slice data with response entity
     */
    @GetMapping(GlobalURI.FILTER_ROOT)
    fun getFitGroupListByFilter(@ModelAttribute @Valid fitGroupFilterRequest: FitGroupFilterRequest): ResponseEntity<Slice<FitGroupDetailResponse>> {
        return ResponseEntity.ok().body(fitGroupFilterService.getFitGroupListByFilter(fitGroupFilterRequest))
    }

    /**
     * Get Filtered Fit group by user id inbound api
     *
     * @param userId condition of user id
     * @return content and slice data with response entity
     */
    @GetMapping(GlobalURI.FILTER_ROOT + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE)
    fun getFitGroupListByUserId(
        @PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) userId: String,
    ): ResponseEntity<FitGroupDetailsResponse> {
        return ResponseEntity.ok().body(fitGroupFilterService.getFitGroupListByUserId(userId))
    }
}