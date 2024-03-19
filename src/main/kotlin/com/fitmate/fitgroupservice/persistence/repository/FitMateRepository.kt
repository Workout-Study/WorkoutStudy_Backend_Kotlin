package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitMate
import org.springframework.data.jpa.repository.JpaRepository

interface FitMateRepository : JpaRepository<FitMate, Long> {
    fun findByFitGroupAndState(fitGroup: FitGroup, state: Boolean): List<FitMate>?
    fun countByFitGroupAndState(fitGroup: FitGroup, state: Boolean): Int?
}