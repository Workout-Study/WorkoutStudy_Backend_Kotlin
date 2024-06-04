package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.user.UserCreateMessageDto

interface UserService {
    fun saveUser(userIdInt: Int, eventPublisher: String)
    fun createUser(userCreateMessageDto: UserCreateMessageDto, eventPublisher: String)
}