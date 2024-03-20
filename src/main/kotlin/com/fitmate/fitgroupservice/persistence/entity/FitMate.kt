package com.fitmate.fitgroupservice.persistence.entity

import com.fitmate.fitgroupservice.common.GlobalStatus
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitMate(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fit_group_id", nullable = false)
    val fitGroup: FitGroup,
    @Column(nullable = false) val fitMateUserId: String,
    createUser: String
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), createUser) {

    fun kick(kicker: String) {
        this.updatedAt = Instant.now()
        this.updateUser = kicker
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}