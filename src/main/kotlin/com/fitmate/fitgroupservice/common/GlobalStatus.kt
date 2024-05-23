package com.fitmate.fitgroupservice.common

class GlobalStatus {

    companion object {
        const val SPRING_PROFILES_ACTIVE = "spring.profiles.active"
        const val SPRING_PROFILES_ACTIVE_DEFAULT = "local"

        const val PERSISTENCE_NOT_DELETED: Boolean = false
        const val PERSISTENCE_DELETED: Boolean = true

        const val KAFKA_TOPIC_FIT_GROUP = "fit-group"
        const val KAFKA_TOPIC_FIT_MATE = "fit-mate"
        const val KAFKA_TOPIC_USER_CREATE_EVENT = "user-create-event"
        const val KAFKA_TOPIC_USER_DELETE_EVENT = "user-delete-event"
        const val KAFKA_TOPIC_USER_UPDATE_EVENT = "user-update-event"

        const val KAFKA_GROUP_ID = "fit-group-service"
    }
}