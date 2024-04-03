package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.DeleteFitMateEvent
import com.fitmate.fitgroupservice.event.producer.FitMateProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

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
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
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