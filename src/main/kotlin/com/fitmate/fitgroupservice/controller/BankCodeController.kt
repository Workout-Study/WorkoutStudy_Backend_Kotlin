package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.bank.BankCodesResponse
import com.fitmate.fitgroupservice.service.BankCodeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BankCodeController(private val bankCodeService: BankCodeService) {

    /**
     * Get Bank code list and code name Inbound api
     *
     * @return bank code and code name list with response entity
     */
    @GetMapping(GlobalURI.BANK_CODE_ROOT)
    fun getBankCodes(): ResponseEntity<BankCodesResponse> {
        return ResponseEntity.ok().body(bankCodeService.getBankCodes())
    }
}