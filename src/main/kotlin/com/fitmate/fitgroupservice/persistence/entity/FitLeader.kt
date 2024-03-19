package com.fitmate.fitgroupservice.persistence.entity

import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitLeader(@ManyToOne(fetch = FetchType.LAZY)
                @JoinColumn(name = "fit_group_id", nullable = false)
                val fitGroup: FitGroup,
                @Column(nullable = false) val fitLeaderUserId: String,
                state: Boolean,
                createdAt: Instant,
                createUser: String) : BaseEntity(state, createdAt, createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}