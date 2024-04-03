package com.fitmate.fitgroupservice.event.producer

interface FitMateProducer {
    fun produceFitMateEvent(fitGroupId: Long)
}