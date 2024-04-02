package com.fitmate.myfit.adapter.`in`.web.common

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.util.ContentCachingResponseWrapper

class ResponseWrapper(response: HttpServletResponse) : ContentCachingResponseWrapper(response) {
}