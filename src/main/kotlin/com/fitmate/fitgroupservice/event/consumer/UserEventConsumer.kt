package com.fitmate.fitgroupservice.event.consumer

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.user.UserCreateMessageDto
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserEventConsumer(
    private val userService: UserService,
    private val objectMapper: ObjectMapper
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(UserEventConsumer::class.java)
    }

    /**
     * kafka user info event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_INFO_EVENT],
        groupId = GlobalStatus.KAFKA_GROUP_ID
    )
    fun userEvent(userId: String) {
        logger?.info("KafkaListener userEvent with userEvent start - user id = {}", userId)

        val userIdInt: Int

        try {
            userIdInt = userId.toInt()
        } catch (exception: Exception) {
            logger?.error("userEvent consume exception = ", exception)
            throw BadRequestException("user id must be int")
        }

        userService.saveUser(userIdInt, "kafka")
    }

    /**
     * kafka user create event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_CREATE_EVENT],
        groupId = GlobalStatus.KAFKA_GROUP_ID
    )
    fun userCreateEvent(message: String) {
        logger?.info("KafkaListener userCreateEvent with userCreateEvent start - message = {}", message)

        val userCreateMessageDto: UserCreateMessageDto

        try {
            userCreateMessageDto = objectMapper.readValue(message, UserCreateMessageDto::class.java)
        } catch (e: JsonProcessingException) {
            logger?.error("JsonProcessingException on userCreateEvent ", e)

            throw e
        } catch (e: JsonMappingException) {
            logger?.error("JsonMappingException on userCreateEvent", e)

            throw e
        }

        userService.createUser(userCreateMessageDto, "kafka")
    }
}