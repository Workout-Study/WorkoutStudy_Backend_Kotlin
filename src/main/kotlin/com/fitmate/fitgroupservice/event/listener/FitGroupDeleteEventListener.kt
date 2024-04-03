package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.DeleteFitGroupEvent
import com.fitmate.fitgroupservice.event.producer.FitGroupProducer
import com.fitmate.fitgroupservice.service.FitGroupHistoryService
import com.fitmate.fitgroupservice.service.MultiMediaEndPointService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FitGroupDeleteEventListener(
    private val fitGroupHistoryService: FitGroupHistoryService,
    private val multiMediaEndPointService: MultiMediaEndPointService,
    private val fitGroupProducer: FitGroupProducer
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitGroupDeleteEventListener::class.java)
    }

    /**
     * delete event register fit group history
     *
     * @param deleteFitGroupEvent delete fit group event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun registerFitGroupHistory(deleteFitGroupEvent: DeleteFitGroupEvent) {
        logger?.info(
            "DeleteFitGroupEvent with registerFitGroupHistory start - fit group id = {}",
            deleteFitGroupEvent.fitGroupId
        )
        fitGroupHistoryService.registerFitGroupHistory(deleteFitGroupEvent.fitGroupId)
    }

    /**
     * delete event delete multi media
     *
     * @param deleteFitGroupEvent delete fit group event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun deleteMultiMediaEndPoint(deleteFitGroupEvent: DeleteFitGroupEvent) {
        logger?.info(
            "DeleteFitGroupEvent with deleteMultiMediaEndPoint start - fit group id = {}",
            deleteFitGroupEvent.fitGroupId
        )
        multiMediaEndPointService.deleteMultiMediaEndPoint(deleteFitGroupEvent.fitGroupId)
    }

    /**
     * delete event register produce kafka event
     *
     * @param deleteFitGroupEvent delete fit group event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun produceFitGroup(deleteFitGroupEvent: DeleteFitGroupEvent) {
        FitGroupRegisterEventListener.logger?.info(
            "DeleteFitGroupEvent with produceFitGroupDto start - fit group id = {}",
            deleteFitGroupEvent.fitGroupId
        )
        fitGroupProducer.produceFitGroupEvent(deleteFitGroupEvent.fitGroupId)
    }
}