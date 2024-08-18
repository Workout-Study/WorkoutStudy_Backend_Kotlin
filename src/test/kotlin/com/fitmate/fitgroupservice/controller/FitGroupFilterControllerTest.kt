package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.filter.FitGroupDetailsResponse
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.dto.group.FitGroupDetailResponse
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import com.fitmate.fitgroupservice.service.FitGroupFilterService
import com.fitmate.fitgroupservice.utils.SenderUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.SliceImpl
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.util.UriComponentsBuilder

@WebMvcTest(FitGroupFilterController::class)
@AutoConfigureRestDocs
class FitGroupFilterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var fitGroupFilterService: FitGroupFilterService

    @MockBean
    private lateinit var senderUtils: SenderUtils

    private val withMaxGroup = false
    private val category = 1
    private val pageNumber = 1
    private val pageSize = 5
    private val pageRequest = PageRequest.of(pageNumber, pageSize)
    private val fitGroupNameSearch = "헬창"

    private val requestUserId = 11422
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val fitGroupId = 1L
    private val fitLeaderId = 3L
    private val maxFitMate = 20
    private val presentFitMateCount = 7
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @Test
    @DisplayName("[단위][Controller] Fit group filter 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `fit group filter controller empty condition success test`() {
        //given
        val fitGroupFilterRequest = FitGroupFilterRequest()

        val fitGroupDetailResponseList = getFitGroupDetailResponses(fitGroupFilterRequest)
        val fitGroupDetailResponse = SliceImpl(fitGroupDetailResponseList, pageRequest, true)

        Mockito.`when`(fitGroupFilterService.getFitGroupListByFilter(fitGroupFilterRequest))
            .thenReturn(fitGroupDetailResponse)
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FILTER_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[단위][Controller] Fit group filter full condition 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `fit group filter controller full condition success test`() {
        //given
        val fitGroupFilterRequest =
            FitGroupFilterRequest(withMaxGroup, category, fitGroupNameSearch, pageNumber, pageSize)

        val fitGroupDetailResponseList = getFitGroupDetailResponses(fitGroupFilterRequest)

        val fitGroupDetailResponse = SliceImpl(fitGroupDetailResponseList, pageRequest, true)

        whenever(fitGroupFilterService.getFitGroupListByFilter(any<FitGroupFilterRequest>()))
            .thenReturn(fitGroupDetailResponse)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("withMaxGroup", fitGroupFilterRequest.withMaxGroup)
            .queryParam("category", fitGroupFilterRequest.category)
            .queryParam("pageNumber", fitGroupFilterRequest.pageNumber)
            .queryParam("pageSize", fitGroupFilterRequest.pageSize)
            .queryParam("fitGroupNameSearch", fitGroupFilterRequest.fitGroupNameSearch)
            .build()
            .encode()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FILTER_ROOT + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-group-filter",
                    queryParameters(
                        parameterWithName("pageNumber").description("조회할 fit group slice 페이지 번호 ( null일 경우 기본값 0 )"),
                        parameterWithName("pageSize").description("조회할 fit group slice size ( null일 경우 기본값 5 )"),
                        parameterWithName("withMaxGroup")
                            .description("인원이 다 찬 방도 포함할 건지"),
                        parameterWithName("category").description("조회할 fit group의 카테고리 ( null일 경우 전체 )"),
                        parameterWithName("fitGroupNameSearch").description("조회할 fit group의 제목으로 검색 ( null일 경우 전체 )")
                    ),
                    responseFields(
                        fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("fit group List"),
                        fieldWithPath("content[].fitGroupId").type(JsonFieldType.NUMBER).description("fit group의 id"),
                        fieldWithPath("content[].fitLeaderUserId").type(JsonFieldType.NUMBER)
                            .description("fit group의 leader user id"),
                        fieldWithPath("content[].fitGroupLeaderUserNickname").type(JsonFieldType.STRING)
                            .description("fit group의 leader user nickname"),
                        fieldWithPath("content[].fitGroupName").type(JsonFieldType.STRING).description("fit group의 이름"),
                        fieldWithPath("content[].penaltyAmount").type(JsonFieldType.NUMBER)
                            .description("fit group의 패널티 금액"),
                        fieldWithPath("content[].category").type(JsonFieldType.NUMBER)
                            .description("fit group의 카테고리 ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 전체 )"),
                        fieldWithPath("content[].introduction").type(JsonFieldType.STRING)
                            .description("fit group의 소개글"),
                        fieldWithPath("content[].cycle").type(JsonFieldType.NUMBER)
                            .description("fit group의 운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )"),
                        fieldWithPath("content[].frequency").type(JsonFieldType.NUMBER)
                            .description("fit group의 주기별 인증 필요 횟수"),
                        fieldWithPath("content[].maxFitMate").type(JsonFieldType.NUMBER)
                            .description("fit group최대 인원 수"),
                        fieldWithPath("content[].presentFitMateCount").type(JsonFieldType.NUMBER)
                            .description("fit group현재 인원 수"),
                        fieldWithPath("content[].multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("fit group 멀티 미디어 end point list ( 주어진 index 순으로 return )"),
                        fieldWithPath("content[].state").type(JsonFieldType.BOOLEAN)
                            .description("fit group의 상태 (false: 정상, true: 삭제)"),
                        fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("fit group 생성 일자"),
                        fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageable object"),
                        fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                        fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("조회 한 size"),
                        fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("sort object"),
                        fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("sort 요청 여부"),
                        fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort 여부"),
                        fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsort 여부"),
                        fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("대상 시작 번호"),
                        fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                        fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("List 크기"),
                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                        fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort object"),
                        fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort 요청 여부"),
                        fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort 여부"),
                        fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsort 여부"),
                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                        fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("처음인지 여부"),
                        fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막인지 여부"),
                        fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("비어있는지 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Controller] Fit group filter by user id 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `fit group filter by user id controller success test`() {
        //given
        val fitGroupFilterRequest =
            FitGroupFilterRequest(withMaxGroup, category, fitGroupNameSearch, pageNumber, pageSize)

        val fitGroupDetailResponseList = getFitGroupDetailResponses(fitGroupFilterRequest)

        Mockito.`when`(fitGroupFilterService.getFitGroupListByUserId(requestUserId))
            .thenReturn(FitGroupDetailsResponse(fitGroupDetailResponseList))

        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FILTER_ROOT + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE, requestUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-group-filter-by-user-id",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_USER_ID)
                            .description("fit group 목록을 조회할 user id")
                    ),
                    responseFields(
                        fieldWithPath("fitGroupDetails[]").type(JsonFieldType.ARRAY).description("fit group List"),
                        fieldWithPath("fitGroupDetails[].fitGroupId").type(JsonFieldType.NUMBER)
                            .description("fit group의 id"),
                        fieldWithPath("fitGroupDetails[].fitLeaderUserId").type(JsonFieldType.NUMBER)
                            .description("fit group의 leader user id"),
                        fieldWithPath("fitGroupDetails[].fitGroupLeaderUserNickname").type(JsonFieldType.STRING)
                            .description("Fit Leader User nickname"),
                        fieldWithPath("fitGroupDetails[].fitGroupName").type(JsonFieldType.STRING)
                            .description("fit group의 이름"),
                        fieldWithPath("fitGroupDetails[].penaltyAmount").type(JsonFieldType.NUMBER)
                            .description("fit group의 패널티 금액"),
                        fieldWithPath("fitGroupDetails[].category").type(JsonFieldType.NUMBER)
                            .description("fit group의 카테고리 ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 전체 )"),
                        fieldWithPath("fitGroupDetails[].introduction").type(JsonFieldType.STRING)
                            .description("fit group의 소개글"),
                        fieldWithPath("fitGroupDetails[].cycle").type(JsonFieldType.NUMBER)
                            .description("fit group의 운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )"),
                        fieldWithPath("fitGroupDetails[].frequency").type(JsonFieldType.NUMBER)
                            .description("fit group의 주기별 인증 필요 횟수"),
                        fieldWithPath("fitGroupDetails[].maxFitMate").type(JsonFieldType.NUMBER)
                            .description("fit group최대 인원 수"),
                        fieldWithPath("fitGroupDetails[].presentFitMateCount").type(JsonFieldType.NUMBER)
                            .description("fit group현재 인원 수"),
                        fieldWithPath("fitGroupDetails[].multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("fit group 멀티 미디어 end point list ( 주어진 index 순으로 return )"),
                        fieldWithPath("fitGroupDetails[].state").type(JsonFieldType.BOOLEAN)
                            .description("fit group의 상태 (false: 정상, true: 삭제)"),
                        fieldWithPath("fitGroupDetails[].createdAt").type(JsonFieldType.STRING)
                            .description("fit group 생성 일자"),
                    )
                )
            )
    }

    private fun getFitGroupDetailResponses(fitGroupFilterRequest: FitGroupFilterRequest): MutableList<FitGroupDetailResponse> {
        val fitGroupDetailResponseList = mutableListOf<FitGroupDetailResponse>()

        for (i in 1..fitGroupFilterRequest.pageSize) {
            val fitGroup = FitGroup(
                fitGroupName + i,
                penaltyAmount + i,
                category,
                introduction + i,
                cycle ?: 1,
                frequency,
                maxFitMate + i,
                (requestUserId + i).toString()
            )

            fitGroup.id = fitGroupId + i

            val fitLeader = FitLeader(fitGroup, requestUserId + i, (requestUserId + i).toString())

            fitLeader.id = fitLeaderId + i

            val userForRead = UserForRead(requestUserId + i, "testUser" + i, multiMediaEndPoint[0], "testUser" + i)

            userForRead.id = fitLeaderId + i + i

            fitGroupDetailResponseList.add(
                FitGroupDetailResponse(
                    fitLeader,
                    fitGroup,
                    userForRead,
                    presentFitMateCount + i,
                    multiMediaEndPoint
                )
            )
        }
        return fitGroupDetailResponseList
    }
}