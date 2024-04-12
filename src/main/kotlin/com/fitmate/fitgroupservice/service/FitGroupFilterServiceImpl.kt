package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.filter.FitGroupDetailsResponse
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupDetailResponse
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.MultiMediaEndPointRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitGroupFilterServiceImpl(
    private val fitGroupRepository: FitGroupRepository,
    private val multiMediaEndPointRepository: MultiMediaEndPointRepository
) : FitGroupFilterService {

    /**
     * Get Filtered Fit group service
     *
     * @param fitGroupFilterRequest filter condition and pageable
     * @return content and slice data
     */
    @Transactional(readOnly = true)
    override fun getFitGroupListByFilter(fitGroupFilterRequest: FitGroupFilterRequest): Slice<FitGroupDetailResponse> {
        val pageable = PageRequest.of(fitGroupFilterRequest.pageNumber, fitGroupFilterRequest.pageSize + 1)

        var fitGroupList = fitGroupRepository.filterFitGroup(fitGroupFilterRequest, pageable)

        val hasNext: Boolean = fitGroupList.size > fitGroupFilterRequest.pageSize

        if (hasNext) fitGroupList = fitGroupList.dropLast(1)

        val fitGroupDetailResponseList: List<FitGroupDetailResponse> = fitGroupList.map {
            FitGroupDetailResponse(
                it.fitLeader,
                it.fitGroup,
                it.presentFitMateCount,
                findMultiMediaEndPointsAndGet(it.fitGroup)
            )
        }.toList()

        return SliceImpl(
            fitGroupDetailResponseList,
            PageRequest.of(fitGroupFilterRequest.pageNumber, fitGroupFilterRequest.pageSize),
            hasNext
        )
    }

    @Transactional(readOnly = true)
    override fun getFitGroupListByUserId(userId: String): FitGroupDetailsResponse {
        val fitGroupList = fitGroupRepository.filterFitGroupByUserId(userId)

        val fitGroupDetailResponseList: List<FitGroupDetailResponse> = fitGroupList.map {
            FitGroupDetailResponse(
                it.fitLeader,
                it.fitGroup,
                it.presentFitMateCount,
                findMultiMediaEndPointsAndGet(it.fitGroup)
            )
        }.toList()

        return FitGroupDetailsResponse(fitGroupDetailResponseList)
    }

    private fun findMultiMediaEndPointsAndGet(fitGroup: FitGroup): List<String> {
        val multiMediaEndpoints = multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(
            fitGroup,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )
        return multiMediaEndpoints?.map { it.endPoint } ?: listOf()
    }
}