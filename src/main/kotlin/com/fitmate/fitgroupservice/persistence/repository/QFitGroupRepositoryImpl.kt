package com.fitmate.fitgroupservice.persistence.repository

import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.QFitGroup.fitGroup
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
    ): List<FitGroup> =
        factory.select(fitGroup)
            .from(fitGroup)
            .where()
            .fetch()
}