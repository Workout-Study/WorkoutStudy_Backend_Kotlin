package com.fitmate.fitgroupservice.event.producer

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.produce.FitGroupDto
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FitGroupProducerImpl(
    private val fitGroupRepository: FitGroupRepository,
    private val fitGroupDtoKafkaTemplate: KafkaTemplate<String, FitGroupDto>
) : FitGroupProducer {

    @Transactional(readOnly = true)
    override fun produceFitGroup(fitGroupId: Long) {
        val fitGroup = fitGroupRepository.findById(fitGroupId)
            .orElseThrow { ResourceNotFoundException("produce fit group not found") }

        val fitGroupDto =
            FitGroupDto(fitGroup.id!!, fitGroup.fitGroupName, fitGroup.cycle, fitGroup.frequency, fitGroup.state)

        fitGroupDtoKafkaTemplate.send(GlobalStatus.KAFKA_TOPIC_FIT_GROUP, fitGroupDto)
    }
}