package com.fitmate.fitgroupservice.dto.group

import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import com.fitmate.fitgroupservice.utils.DateParseUtils

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
    val category: Int = fitGroup.category
    val introduction: String? = fitGroup.introduction
    val cycle: Int = fitGroup.cycle
    val frequency: Int = fitGroup.frequency
    val createdAt: String = DateParseUtils.instantToString(fitGroup.createdAt)
    val maxFitMate: Int = fitGroup.maxFitMate
    val state: Boolean = fitGroup.state
}