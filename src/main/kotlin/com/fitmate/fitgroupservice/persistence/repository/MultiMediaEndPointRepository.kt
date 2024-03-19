package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.MultiMediaEndPoint
import org.springframework.data.jpa.repository.JpaRepository

interface MultiMediaEndPointRepository : JpaRepository<MultiMediaEndPoint, Long> {
    fun findByFitGroupAndStateOrderByIdAsc(fitGroup: FitGroup, state: Boolean): List<MultiMediaEndPoint>?
}