package com.fitmate.fitgroupservice.persistence.entity

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.user.UserCreateMessageDto
import com.fitmate.fitgroupservice.dto.user.UserInfoResponse
import jakarta.persistence.*
import java.time.Instant

@Entity
class UserForRead(
    @Column(nullable = false) val userId: Int,
    @Column(nullable = false) var nickname: String,
    @Column(nullable = false) var imageUrl: String,
    createUser: String
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateByResponse(userInfoResponse: UserInfoResponse, eventPublisher: String) {
        this.nickname = userInfoResponse.nickname
        this.state = userInfoResponse.state
        this.imageUrl = userInfoResponse.imageUrl
        this.updatedAt = Instant.now()
        this.updateUser = eventPublisher
    }

    fun updateByUserMessageDto(userCreateMessageDto: UserCreateMessageDto, eventPublisher: String) {
        this.nickname = userCreateMessageDto.nickname
        this.state = userCreateMessageDto.state
        this.imageUrl = userCreateMessageDto.imageUrl
        this.updatedAt = Instant.now()
        this.updateUser = eventPublisher
    }
}