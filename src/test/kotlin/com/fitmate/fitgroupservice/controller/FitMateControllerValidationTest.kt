package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.mate.*
import com.fitmate.fitgroupservice.service.FitMateService
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

@WebMvcTest(FitMateController::class)
class FitMateControllerValidationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var fitMateService: FitMateService

    private val requestUserId = "testUserID"
    private val fitGroupId = 1L

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit mate 등록 request user id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `register fit mate controller request user id validation fail test`(testRequestUserId: String) {
        //given
        val registerFitMateRequest = RegisterMateRequest(testRequestUserId, fitGroupId)
        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.MATE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @Test
    @DisplayName("[단위][Controller] Fit mate 목록 조회 - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `get fit mate list controller validation fail test`() {
        //given when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, "wrongRequestGroupId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @Test
    @DisplayName("[단위][Controller] Fit mate 탈퇴 - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `delete fit mate controller validation fail test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)
        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, "wrongRequestGroupId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit mate 탈퇴 request user id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `delete fit mate controller request user id validation fail test`(testRequestUserId: String) {
        //given
        val deleteMateRequest = DeleteMateRequest(testRequestUserId)
        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }
}