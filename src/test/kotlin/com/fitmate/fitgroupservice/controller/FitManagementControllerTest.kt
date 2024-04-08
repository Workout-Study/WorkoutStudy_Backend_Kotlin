package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.management.KickFitMateRequest
import com.fitmate.fitgroupservice.dto.management.KickFitMateResponse
import com.fitmate.fitgroupservice.service.FitManagementService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(FitManagementController::class)
@AutoConfigureRestDocs
class FitManagementControllerTest {

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
    @DisplayName("[단위][Controller] Fit management fit mate 추방 - 성공 테스트")
    @Throws(Exception::class)
    fun `kick fit mate controller success test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(requestUserId)
        val kickFitMateResponse = KickFitMateResponse(true)

        Mockito.`when`(fitManagementService.kickFitMate(fitGroupId, userId, kickFitMateRequest))
            .thenReturn(kickFitMateResponse)
        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_MATE_MANAGEMENT_ROOT
                        + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE,
                fitGroupId, userId
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kickFitMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "kick-fit-mate",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID)
                            .description("추방할 Fit group id"),
                        parameterWithName(GlobalURI.PATH_VARIABLE_USER_ID)
                            .description("추방할 Fit mate user id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("추방을 요청한 User id ( fit leader 여야함 )")
                    ),
                    responseFields(
                        fieldWithPath("isKickSuccess").type(JsonFieldType.BOOLEAN)
                            .description("추방 성공 여부")
                    )
                )
            )
    }
}