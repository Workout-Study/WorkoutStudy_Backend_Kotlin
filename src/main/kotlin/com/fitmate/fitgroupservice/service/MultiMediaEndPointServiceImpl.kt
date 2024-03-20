package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.MultiMediaEndPointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MultiMediaEndPointServiceImpl(
    private val fitGroupRepository: FitGroupRepository,
    private val multiMediaEndPointRepository: MultiMediaEndPointRepository
) : MultiMediaEndPointService {

    /**
     * delete Multi Media End Point
     *
     * @param fitGroupId target fit group id
     */
    @Transactional
    override fun deleteMultiMediaEndPoint(fitGroupId: Long) {
        val fitGroup = fitGroupRepository.findById(fitGroupId)
            .orElseThrow { ResourceNotFoundException("delete multi media end point event fit group not found") }

        val multiMediaList = multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(
            fitGroup,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )
        multiMediaList?.forEach { it.delete() }
    }
}