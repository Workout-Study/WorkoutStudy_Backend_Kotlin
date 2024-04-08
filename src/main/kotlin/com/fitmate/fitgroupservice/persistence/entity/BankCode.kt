package com.fitmate.fitgroupservice.persistence.entity

import com.fitmate.fitgroupservice.common.GlobalStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
class BankCode(
    @Column(nullable = false) var code: String,
    @Column(nullable = false) var codeName: String
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), "system") {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}