package com.fitmate.fitgroupservice.event.producer

interface FitGroupProducer {
    fun produceFitGroup(fitGroupId: Long)
}