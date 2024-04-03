package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.UpdateFitGroupEvent
import com.fitmate.fitgroupservice.event.producer.FitGroupProducer
import com.fitmate.fitgroupservice.service.FitGroupHistoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class FitGroupUpdateEventListener(
    private val fitGroupHistoryService: FitGroupHistoryService,
    private val fitGroupProducer: FitGroupProducer
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitGroupUpdateEventListener::class.java)
    }

    /**
     * update event register fit group history
     *
     * @param updateFitGroupEvent update fit group event
     */
    @EventListener(UpdateFitGroupEvent::class)
    @Async
    fun registerFitGroupHistory(updateFitGroupEvent: UpdateFitGroupEvent) {
        logger?.info(
            "UpdateFitGroupEvent with registerFitGroupHistory start - fit group id = {}",
            updateFitGroupEvent.fitGroupId
        )
        fitGroupHistoryService.registerFitGroupHistory(updateFitGroupEvent.fitGroupId)
    }

    /**
     * update event register produce kafka event
     *
     * @param updateFitGroupEvent update fit group event
     */
    @EventListener(UpdateFitGroupEvent::class)
    @Async
    fun produceFitGroup(updateFitGroupEvent: UpdateFitGroupEvent) {
        FitGroupRegisterEventListener.logger?.info(
            "UpdateFitGroupEvent with produceFitGroupDto start - fit group id = {}",
            updateFitGroupEvent.fitGroupId
        )
        fitGroupProducer.produceFitGroupEvent(updateFitGroupEvent.fitGroupId)
    }
}