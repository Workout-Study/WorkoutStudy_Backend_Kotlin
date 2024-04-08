package com.fitmate.fitgroupservice.event.producer

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FitMateProducerImpl(
    private val fitGroupRepository: FitGroupRepository,
    private val fitGroupDtoKafkaTemplate: KafkaTemplate<String, String>
) : FitMateProducer {

    @Transactional(readOnly = true)
    override fun produceFitMateEvent(fitGroupId: Long) {
        val fitGroup = fitGroupRepository.findById(fitGroupId)
            .orElseThrow { ResourceNotFoundException("produce fit mate on fit group not found") }

        fitGroupDtoKafkaTemplate.send(GlobalStatus.KAFKA_TOPIC_FIT_MATE, fitGroup.id.toString())
    }
}