package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.persistence.entity.BankCode
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BankCodeRepository : JpaRepository<BankCode, Long> {
    fun findByCode(code: String): Optional<BankCode>
}