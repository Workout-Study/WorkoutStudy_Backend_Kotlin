package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import org.springframework.data.jpa.repository.JpaRepository

interface FitGroupRepository : JpaRepository<FitGroup, Long>, QFitGroupRepository {
}