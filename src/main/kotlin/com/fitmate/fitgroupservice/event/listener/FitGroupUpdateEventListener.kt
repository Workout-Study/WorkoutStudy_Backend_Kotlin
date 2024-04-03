package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.UpdateFitGroupEvent
import com.fitmate.fitgroupservice.event.producer.FitGroupProducer
import com.fitmate.fitgroupservice.service.FitGroupHistoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

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
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
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
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun produceFitGroup(updateFitGroupEvent: UpdateFitGroupEvent) {
        FitGroupRegisterEventListener.logger?.info(
            "UpdateFitGroupEvent with produceFitGroupDto start - fit group id = {}",
            updateFitGroupEvent.fitGroupId
        )
        fitGroupProducer.produceFitGroupEvent(updateFitGroupEvent.fitGroupId)
    }
}