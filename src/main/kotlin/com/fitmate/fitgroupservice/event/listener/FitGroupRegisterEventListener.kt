package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.RegisterFitGroupEvent
import com.fitmate.fitgroupservice.event.producer.FitGroupProducer
import com.fitmate.fitgroupservice.service.FitGroupHistoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class FitGroupRegisterEventListener(
    private val fitGroupHistoryService: FitGroupHistoryService,
    private val fitGroupProducer: FitGroupProducer
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitGroupRegisterEventListener::class.java)
    }

    /**
     * register event register fit group history
     *
     * @param registerFitGroupEvent register fit group event
     */
    @EventListener(RegisterFitGroupEvent::class)
    @Async
    fun registerFitGroupHistory(registerFitGroupEvent: RegisterFitGroupEvent) {
        logger?.info(
            "RegisterFitGroupEvent with registerFitGroupHistory start - fit group id = {}",
            registerFitGroupEvent.fitGroupId
        )
        fitGroupHistoryService.registerFitGroupHistory(registerFitGroupEvent.fitGroupId)
    }

    /**
     * register event register produce kafka event
     *
     * @param registerFitGroupEvent register fit group event
     */
    @EventListener(RegisterFitGroupEvent::class)
    @Async
    fun produceFitGroup(registerFitGroupEvent: RegisterFitGroupEvent) {
        logger?.info(
            "RegisterFitGroupEvent with produceFitGroupDto start - fit group id = {}",
            registerFitGroupEvent.fitGroupId
        )
        fitGroupProducer.produceFitGroup(registerFitGroupEvent.fitGroupId)
    }
}