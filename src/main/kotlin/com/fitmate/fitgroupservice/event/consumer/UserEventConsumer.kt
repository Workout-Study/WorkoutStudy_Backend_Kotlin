package com.fitmate.fitgroupservice.event.consumer

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserEventConsumer(private val userService: UserService) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(UserEventConsumer::class.java)
    }

    /**
     * kafka user create, update event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_CREATE_EVENT, GlobalStatus.KAFKA_TOPIC_USER_UPDATE_EVENT],
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
     * kafka user delete event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_DELETE_EVENT],
        groupId = GlobalStatus.KAFKA_GROUP_ID
    )
    fun userDeleteEvent(userId: String) {
        logger?.info("KafkaListener userDeleteEvent with userDeleteEvent start - user id = {}", userId)

        val userIdInt: Int

        try {
            userIdInt = userId.toInt()
        } catch (exception: Exception) {
            logger?.error("userDeleteEvent consume exception = ", exception)
            throw BadRequestException("user id must be int")
        }

        userService.deleteUser(userIdInt, "kafka")
    }
}