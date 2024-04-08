package com.fitmate.fitgroupservice

import com.fitmate.fitgroupservice.common.GlobalStatus
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FitGroupServiceApplication

fun main(args: Array<String>) {
    runApplication<FitGroupServiceApplication>(*args) {
        val active = System.getProperty(GlobalStatus.SPRING_PROFILES_ACTIVE)
        if (active == null) {
            System.setProperty(GlobalStatus.SPRING_PROFILES_ACTIVE, GlobalStatus.SPRING_PROFILES_ACTIVE_DEFAULT)
        }
        System.setProperty(
            GlobalStatus.SPRING_PROFILES_ACTIVE,
            System.getProperty(GlobalStatus.SPRING_PROFILES_ACTIVE, GlobalStatus.SPRING_PROFILES_ACTIVE_DEFAULT)
        )
    }
}
