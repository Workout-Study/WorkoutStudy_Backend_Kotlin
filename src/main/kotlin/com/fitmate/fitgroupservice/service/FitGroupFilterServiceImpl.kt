package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.filter.FitGroupDetailsResponse
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupDetailResponse
import com.fitmate.fitgroupservice.dto.group.FitGroupFilterResponse
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.MultiMediaEndPointRepository
import com.fitmate.fitgroupservice.persistence.repository.UserForReadRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class FitGroupFilterServiceImpl(
    private val fitGroupRepository: FitGroupRepository,
    private val multiMediaEndPointRepository: MultiMediaEndPointRepository,
    private val userForReadRepository: UserForReadRepository
) : FitGroupFilterService {

    /**
     * Get Filtered Fit group service
     *
     * @param fitGroupFilterRequest filter condition and pageable
     * @return content and slice data
     */
    @Transactional(readOnly = true)
    override fun getFitGroupListByFilter(fitGroupFilterRequest: FitGroupFilterRequest): Slice<FitGroupDetailResponse> {
        val pageable = PageRequest.of(fitGroupFilterRequest.pageNumber, fitGroupFilterRequest.pageSize)

        var fitGroupList = fitGroupRepository.filterFitGroup(fitGroupFilterRequest, pageable)

        val hasNext: Boolean = fitGroupList.size > fitGroupFilterRequest.pageSize

        if (hasNext) fitGroupList = fitGroupList.dropLast(1)

        val fitGroupDetailResponseList: List<FitGroupDetailResponse> = getFitGroupDetailResponses(fitGroupList)

        return SliceImpl(
            fitGroupDetailResponseList,
            PageRequest.of(fitGroupFilterRequest.pageNumber, fitGroupFilterRequest.pageSize),
            hasNext
        )
    }

    @Transactional(readOnly = true)
    override fun getFitGroupListByUserId(userId: Int): FitGroupDetailsResponse {
        val fitGroupList = fitGroupRepository.filterFitGroupByUserId(userId)

        val fitGroupDetailResponseList: List<FitGroupDetailResponse> = getFitGroupDetailResponses(fitGroupList)

        return FitGroupDetailsResponse(fitGroupDetailResponseList)
    }

    private fun getFitGroupDetailResponses(
        fitGroupList: List<FitGroupFilterResponse>,
    ): List<FitGroupDetailResponse> {
        val fitGroupDetailResponseList: List<FitGroupDetailResponse> = fitGroupList.map {
            var fitLeaderUserForRead: UserForRead? = null

            if (it.fitLeader != null) fitLeaderUserForRead = userForReadRepository.findByUserIdAndState(
                it.fitLeader.fitLeaderUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            ).getOrNull()

            FitGroupDetailResponse(
                it.fitLeader,
                it.fitGroup,
                fitLeaderUserForRead,
                it.presentFitMateCount,
                findMultiMediaEndPointsAndGet(it.fitGroup)
            )
        }.toList()
        return fitGroupDetailResponseList
    }

    private fun findMultiMediaEndPointsAndGet(fitGroup: FitGroup): List<String> {
        val multiMediaEndpoints = multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(
            fitGroup,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )
        return multiMediaEndpoints?.map { it.endPoint } ?: listOf()
    }
}