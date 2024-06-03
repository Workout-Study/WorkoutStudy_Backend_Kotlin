package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.UserServiceURI
import com.fitmate.fitgroupservice.dto.user.UserInfoResponse
import com.fitmate.fitgroupservice.exception.NotExpectResultException
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import com.fitmate.fitgroupservice.persistence.repository.UserForReadRepository
import com.fitmate.fitgroupservice.utils.SenderUtils
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val senderUtils: SenderUtils,
    private val userForReadRepository: UserForReadRepository
) : UserService {

    @Transactional
    override fun saveUser(userIdInt: Int, eventPublisher: String) {
        val uriEndPoint = "${UserServiceURI.USER_INFO}?userId=${userIdInt}"

        val response: ResponseEntity<UserInfoResponse> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<UserInfoResponse>() {
                })

        val userInfoResponse: UserInfoResponse = response.body ?: throw NotExpectResultException("user info is null")

        val userForRead =
            userForReadRepository.findByUserId(userInfoResponse.userId)
                .orElseGet { UserForRead(userInfoResponse.userId, userInfoResponse.nickname, eventPublisher) }

        userForRead.updateByResponse(userInfoResponse, eventPublisher)

        userForReadRepository.save(userForRead)
    }
}