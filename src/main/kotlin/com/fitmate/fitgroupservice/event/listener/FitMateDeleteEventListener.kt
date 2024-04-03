package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.DeleteFitMateEvent
import com.fitmate.fitgroupservice.event.event.RegisterFitGroupEvent
import com.fitmate.fitgroupservice.event.producer.FitMateProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class FitMateDeleteEventListener(private val fitMateProducer: FitMateProducer) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitMateDeleteEventListener::class.java)
    }

    /**
     * Delete fit mate event
     *
     * @param deleteFitMateEvent delete fit mate event
     */
    @EventListener(RegisterFitGroupEvent::class)
    @Async
    fun produceFitMateEvent(deleteFitMateEvent: DeleteFitMateEvent) {
        logger?.info(
            "DeleteFitMateEvent with produceFitMateEvent start - fit group id = {}, fit mate id = {}",
            deleteFitMateEvent.fitGroupId,
            deleteFitMateEvent.fitMateId
        )
        fitMateProducer.produceFitMateEvent(deleteFitMateEvent.fitGroupId)
    }
}