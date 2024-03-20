package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupDetailResponse
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.FitMateRepository
import com.fitmate.fitgroupservice.persistence.repository.MultiMediaEndPointRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitGroupFilterServiceImpl(
    private val fitMateRepository: FitMateRepository,
    private val fitGroupRepository: FitGroupRepository,
    private val fitLeaderRepository: FitLeaderRepository,
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
                findFitLeaderAndGet(it),
                it,
                getFitMateCountByFitGroup(it),
                findMultiMediaEndPointsAndGet(it)
            )
        }.toList()

        return SliceImpl(fitGroupDetailResponseList, pageable, hasNext)
    }

    private fun findFitLeaderAndGet(fitGroup: FitGroup): FitLeader? {
        val optionalFitLeader =
            fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        return if (optionalFitLeader.isPresent) optionalFitLeader.get()
        else null
    }

    private fun findMultiMediaEndPointsAndGet(fitGroup: FitGroup): List<String> {
        val multiMediaEndpoints = multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(
            fitGroup,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )
        return multiMediaEndpoints?.map { it.endPoint } ?: listOf()
    }

    private fun getFitMateCountByFitGroup(fitGroup: FitGroup): Int {
        return fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED) ?: 0
    }
}