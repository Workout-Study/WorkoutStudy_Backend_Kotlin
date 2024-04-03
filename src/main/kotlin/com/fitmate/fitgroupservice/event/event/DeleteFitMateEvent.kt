package com.fitmate.fitgroupservice.event.event

data class DeleteFitMateEvent(
    val fitGroupId: Long,
    val fitMateId: Long
)
