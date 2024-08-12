package com.fitmate.fitgroupservice.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateParseUtils {

    companion object {
        fun instantToString(instant: Instant?): String {
            instant ?: return ""

            val zonedDateTime = instant.atZone(ZoneId.systemDefault())

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX")

            val formattedDate = zonedDateTime.format(formatter)

            return formattedDate
        }
    }
}