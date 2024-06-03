package com.fitmate.fitgroupservice.common

import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Configuration
@PropertySource(value = ["classpath:slack-properties.yml"])
@ConfigurationProperties(prefix = "slack")
@Component
@Data
class SlackProperties(
    val token: String? = null
)