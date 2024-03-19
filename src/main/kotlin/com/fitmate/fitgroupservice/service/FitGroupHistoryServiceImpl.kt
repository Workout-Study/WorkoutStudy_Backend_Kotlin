package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.entity.FitGroupHistory
import com.fitmate.fitgroupservice.persistence.repository.FitGroupHistoryRepository
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitGroupHistoryServiceImpl(
        private val fitGroupRepository: FitGroupRepository,
        private val fitGroupHistoryRepository: FitGroupHistoryRepository) : FitGroupHistoryService {

    /**
     * register fit group history
     *
     * @param fitGroupId update fit group id
     */
    @Transactional
    override fun registerFitGroupHistory(fitGroupId: Long) {
        val fitGroup = fitGroupRepository.findById(fitGroupId)
                .orElseThrow { ResourceNotFoundException("fit group history update fit group not found") }

        val fitGroupHistory = FitGroupHistory(fitGroup)

        fitGroupHistoryRepository.save(fitGroupHistory)
    }
}