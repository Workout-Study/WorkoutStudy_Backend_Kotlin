package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FitLeaderRepository : JpaRepository<FitLeader, Long> {
    fun findByFitGroupAndState(fitGroup: FitGroup, state: Boolean): Optional<FitLeader>
    fun findByFitGroupAndFitLeaderUserIdAndState(
        fitGroup: FitGroup,
        requestUserId: Int,
        persistenceNotDeleted: Boolean
    ): Optional<FitLeader>

    fun countByFitLeaderUserIdAndState(userId: Int, state: Boolean): Int
}