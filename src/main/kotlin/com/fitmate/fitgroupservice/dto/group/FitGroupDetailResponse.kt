package com.fitmate.fitgroupservice.dto.group

import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import java.time.Instant

data class FitGroupDetailResponse(
    private val fitLeader: FitLeader?,
    private val fitGroup: FitGroup,
    private val userForRead: UserForRead?,
    val presentFitMateCount: Int,
    val multiMediaEndPoints: List<String>
) {

    val fitGroupId: Long = fitGroup.id!!
    val fitLeaderUserId: Int? = fitLeader?.fitLeaderUserId
    val fitGroupLeaderUserNickname: String? = userForRead?.nickname
    val fitGroupName: String = fitGroup.fitGroupName
    val penaltyAmount: Int = fitGroup.penaltyAmount
    val penaltyAccountBankCode: String = fitGroup.penaltyAccountBankCode.code
    val penaltyAccountNumber: String = fitGroup.penaltyAccountNumber
    val category: Int = fitGroup.category
    val introduction: String? = fitGroup.introduction
    val cycle: Int = fitGroup.cycle
    val frequency: Int = fitGroup.frequency
    val createdAt: Instant = fitGroup.createdAt
    val maxFitMate: Int = fitGroup.maxFitMate
    val state: Boolean = fitGroup.state
}