package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.RegisterFitMateEvent
import com.fitmate.fitgroupservice.event.producer.FitMateProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FitMateRegisterEventListener(private val fitMateProducer: FitMateProducer) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitMateRegisterEventListener::class.java)
    }

    /**
     * register fit mate event
     *
     * @param registerFitMateEvent register fit mate event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun produceFitMateEvent(registerFitMateEvent: RegisterFitMateEvent) {
        logger?.info(
            "RegisterFitMateEvent with produceFitMateEvent start - fit group id = {}, fit mate id = {}",
            registerFitMateEvent.fitGroupId,
            registerFitMateEvent.fitMateId
        )
        fitMateProducer.produceFitMateEvent(registerFitMateEvent.fitGroupId)
    }
}