package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.persistence.entity.FitGroupHistory
import org.springframework.data.jpa.repository.JpaRepository

interface FitGroupHistoryRepository : JpaRepository<FitGroupHistory, Long> {
    fun findByFitGroupId(fitGroupId: Long): List<FitGroupHistory>?
}