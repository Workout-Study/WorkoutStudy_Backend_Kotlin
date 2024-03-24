package com.fitmate.fitgroupservice.persistence.entity

import jakarta.persistence.*
import lombok.EqualsAndHashCode
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitGroupHistory(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fit_group_id", nullable = false) val fitGroup: FitGroup
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    val fitGroupName: String = fitGroup.fitGroupName

    @Column(nullable = false)
    val penaltyAmount: Int = fitGroup.penaltyAmount

    @Column(nullable = false)
    var penaltyAccountBankCode: String = fitGroup.penaltyAccountBankCode.code

    @Column(nullable = false)
    var penaltyAccountNumber: String = fitGroup.penaltyAccountNumber

    @Column(nullable = false)
    var maxFitMate: Int = fitGroup.maxFitMate

    @Column(nullable = false)
    val category: Int = fitGroup.category

    val introduction: String? = fitGroup.introduction

    @Column(nullable = false)
    // cycle - ( 1:week, 2:month, 3:year )
    val cycle: Int = fitGroup.cycle

    @Column(nullable = false)
    val frequency: Int = fitGroup.frequency

    @Column
    @Convert(converter = Jsr310JpaConverters.InstantConverter::class)
    var updatedAt: Instant? = fitGroup.updatedAt

    @Column
    var updateUser: String? = fitGroup.updateUser
}