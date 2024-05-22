package com.fitmate.fitgroupservice.persistence.entity

import com.fitmate.fitgroupservice.common.GlobalStatus
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class MultiMediaEndPoint(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fit_group_id", nullable = false)
    val fitGroup: FitGroup,
    val endPoint: String,
    createUser: Int
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), createUser.toString()) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}