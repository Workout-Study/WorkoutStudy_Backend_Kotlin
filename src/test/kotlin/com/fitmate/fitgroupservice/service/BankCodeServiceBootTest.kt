package com.fitmate.fitgroupservice.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class BankCodeServiceBootTest {

    @Autowired
    private lateinit var bankCodeService: BankCodeService

    @Test
    @DisplayName("[통합][Service] Register fit group - 성공 테스트")
    fun `register fit group service success test`() {
        //given when then
        Assertions.assertDoesNotThrow { bankCodeService.getBankCodes() }
    }
}