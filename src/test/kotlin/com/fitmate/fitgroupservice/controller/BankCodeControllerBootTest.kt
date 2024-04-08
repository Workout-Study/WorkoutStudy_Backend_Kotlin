package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BankCodeControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("[통합][Controller] Bank code list 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit group controller success test`() {
        //given when
        val resultActions = mockMvc.perform(
            get(GlobalURI.BANK_CODE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())

    }
}