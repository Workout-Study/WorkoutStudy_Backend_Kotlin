package com.fitmate.fitgroupservice.event.producer

interface FitGroupProducer {
    fun produceFitGroupEvent(fitGroupId: Long)
}