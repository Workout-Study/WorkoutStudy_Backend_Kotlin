package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.mate.*
import com.fitmate.fitgroupservice.service.FitMateService
import com.fitmate.fitgroupservice.utils.SenderUtils
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
import java.time.Instant

@WebMvcTest(FitMateController::class)
@AutoConfigureRestDocs
class FitMateControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var fitMateService: FitMateService

    @MockBean
    private lateinit var senderUtils: SenderUtils

    private val requestUserId = 11422
    private val fitGroupId = 1L

    @Test
    @DisplayName("[단위][Controller] Fit mate 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit mate controller success test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(requestUserId, fitGroupId)
        val registerFitMateResponse = RegisterMateResponse(true)

        Mockito.`when`(fitMateService.registerFitMate(registerFitMateRequest)).thenReturn(registerFitMateResponse)
        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.MATE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
            .andDo(
                document(
                    "register-fit-mate",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("Fit mate로 등록을 요청한 User id"),
                        fieldWithPath("fitGroupId").type(JsonFieldType.NUMBER)
                            .description("Fit group id")
                    ),
                    responseFields(
                        fieldWithPath("isRegisterSuccess").type(JsonFieldType.BOOLEAN)
                            .description("등록 성공 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Controller] Fit mate 목록 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get fit mate list controller success test`() {
        //given
        val fitMateDetailsResponse = FitMateDetailsResponse(
            fitGroupId,
            FitLeaderDetailDto(7, "testUserId", Instant.now()),
            listOf(
                FitMateDetailDto(1L, 8, "testUserId8", Instant.now()),
                FitMateDetailDto(2L, 9, "testUserId9", Instant.now())
            )
        )

        Mockito.`when`(fitMateService.getFitMateListByFitGroup(fitGroupId)).thenReturn(fitMateDetailsResponse)
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "get-fit-mate-list",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID)
                            .description("조회할 Fit group id")
                    ),
                    responseFields(
                        fieldWithPath("fitGroupId").type(JsonFieldType.NUMBER).description("조회한 fit group id"),
                        fieldWithPath("fitLeaderDetail").type(JsonFieldType.OBJECT).description("fit group의 리더 detail"),
                        fieldWithPath("fitLeaderDetail.fitLeaderUserId").type(JsonFieldType.NUMBER)
                            .description("fit group 리더의 user id"),
                        fieldWithPath("fitLeaderDetail.fitLeaderUserNickname").type(JsonFieldType.STRING)
                            .description("fit group 리더의 user nickname"),
                        fieldWithPath("fitLeaderDetail.createdAt").type(JsonFieldType.STRING)
                            .description("fit group 리더의 선정일시"),
                        fieldWithPath("fitMateDetails").type(JsonFieldType.ARRAY).description("fit mate list"),
                        fieldWithPath("fitMateDetails[].fitMateId").type(JsonFieldType.NUMBER)
                            .description("fit mate id"),
                        fieldWithPath("fitMateDetails[].fitMateUserId").type(JsonFieldType.NUMBER)
                            .description("fit mate 의 user id"),
                        fieldWithPath("fitMateDetails[].fitMateUserNickname").type(JsonFieldType.STRING)
                            .description("fit mate 의 user nickname"),
                        fieldWithPath("fitMateDetails[].createdAt").type(JsonFieldType.STRING)
                            .description("fit mate 등록일시")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Controller] Fit mate 탈퇴 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit mate controller success test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)
        val deleteMateResponse = DeleteMateResponse(true)

        Mockito.`when`(fitMateService.deleteFitMate(fitGroupId, deleteMateRequest)).thenReturn(deleteMateResponse)
        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "delete-fit-mate",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID)
                            .description("탈퇴할 Fit group id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("탈퇴를 요청한 User id")
                    ),
                    responseFields(
                        fieldWithPath("isDeleteSuccess").type(JsonFieldType.BOOLEAN).description("삭제 성공 여부")
                    )
                )
            )
    }
}