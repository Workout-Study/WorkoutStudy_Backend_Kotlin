package com.fitmate.fitgroupservice.event.listener

import com.fitmate.fitgroupservice.event.event.DeleteFitGroupEvent
import com.fitmate.fitgroupservice.service.FitGroupHistoryService
import com.fitmate.fitgroupservice.service.MultiMediaEndPointService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class FitGroupDeleteEventListener(private val fitGroupHistoryService: FitGroupHistoryService,
                                  private val multiMediaEndPointService: MultiMediaEndPointService) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitGroupDeleteEventListener::class.java)
    }

    /**
     * delete event register fit group history
     *
     * @param deleteFitGroupEvent delete fit group event
     */
    @EventListener
    fun registerFitGroupHistory(deleteFitGroupEvent: DeleteFitGroupEvent) {
        logger?.info("DeleteFitGroupEvent with registerFitGroupHistory start - fit group id = {}", deleteFitGroupEvent.fitGroupId)
        fitGroupHistoryService.registerFitGroupHistory(deleteFitGroupEvent.fitGroupId)
    }

    /**
     * delete event delete multi media
     *
     * @param deleteFitGroupEvent delete fit group event
     */
    @EventListener
    fun deleteMultiMediaEndPoint(deleteFitGroupEvent: DeleteFitGroupEvent) {
        logger?.info("DeleteFitGroupEvent with deleteMultiMediaEndPoint start - fit group id = {}", deleteFitGroupEvent.fitGroupId)
        multiMediaEndPointService.deleteMultiMediaEndPoint(deleteFitGroupEvent.fitGroupId)
    }
}