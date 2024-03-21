package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupFilterResponse
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.QFitGroup.fitGroup
import com.fitmate.fitgroupservice.persistence.entity.QFitLeader.fitLeader
import com.fitmate.fitgroupservice.persistence.entity.QFitMate.fitMate
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class QFitGroupRepositoryImpl(jpaQueryFactory: JPAQueryFactory) : QuerydslRepositorySupport(FitGroup::class.java),
    QFitGroupRepository {

    private var factory: JPAQueryFactory = jpaQueryFactory

    override fun filterFitGroup(
        fitGroupFilterRequest: FitGroupFilterRequest,
        pageable: PageRequest
    ): List<FitGroupFilterResponse> =
        factory.select(
            Projections.constructor(
                FitGroupFilterResponse::class.java,
                fitLeader,
                fitGroup,
                fitMate.count().coalesce(0L)
                    .castToNum(Int::class.java).`as`("presentFitMateCount")
            )
        ).from(fitGroup)
            .leftJoin(fitLeader)
            .on(
                fitLeader.fitGroup.id.eq(fitGroup.id)
                    .and(fitLeader.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED))
            )
            .leftJoin(fitMate)
            .on(
                fitMate.fitGroup.id.eq(fitGroup.id)
                    .and(fitMate.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED))
            )
            .where(
                categoryCondition(fitGroupFilterRequest.category)
            )
            .groupBy(fitGroup.id)
            .having(conditionWithMaxGroup(fitGroupFilterRequest.withMaxGroup))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(
                fitMate.count().coalesce(0L).desc()
            )
            .fetch()

    private fun conditionWithMaxGroup(withMaxGroup: Boolean): Predicate? {
        return if (!withMaxGroup) {
            fitGroup.maxFitMate.lt(fitMate.count().coalesce(0L))
        } else null
    }

    private fun categoryCondition(category: Int?): Predicate? =
        category?.let { return fitGroup.category.eq(category) }
}