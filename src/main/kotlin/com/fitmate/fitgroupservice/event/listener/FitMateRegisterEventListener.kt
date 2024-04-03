package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.RegisterFitGroupEvent
import com.fitmate.fitgroupservice.event.event.RegisterFitMateEvent
import com.fitmate.fitgroupservice.event.producer.FitMateProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

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
    @EventListener(RegisterFitGroupEvent::class)
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