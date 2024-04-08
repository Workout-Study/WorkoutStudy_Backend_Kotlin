package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.management.KickFitMateRequest
import com.fitmate.fitgroupservice.service.FitManagementService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(FitManagementController::class)
class FitManagementControllerValidationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var fitManagementService: FitManagementService

    private val userId = "kickedUserId"
    private val requestUserId = "leaderUserId"
    private val fitGroupId = 1L

    @Test
    @DisplayName("[단위][Controller] Fit management fit mate 추방 fit group id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `kick fit mate controller fit group id validation fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(requestUserId)
        val wrongRequestGroupId = "wrongRequestGroupId"

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_MATE_MANAGEMENT_ROOT
                        + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE,
                wrongRequestGroupId, userId
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kickFitMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit management fit mate 추방 fit mate user id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `kick fit mate controller fit mate user id validation fail test`(testFitMateUserId: String) {
        //given
        val kickFitMateRequest = KickFitMateRequest(requestUserId)

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_MATE_MANAGEMENT_ROOT
                        + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE,
                fitGroupId, testFitMateUserId
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kickFitMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isNotFound())
            .andDo(print())
    }
}