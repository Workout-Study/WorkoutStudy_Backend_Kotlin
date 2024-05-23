package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserForReadRepository : JpaRepository<UserForRead, Int> {
    fun findByUserIdAndState(userIdInt: Int, state: Boolean): Optional<UserForRead>
}