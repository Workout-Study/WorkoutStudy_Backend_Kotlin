package com.fitmate.fitgroupservice.service

interface UserService {
    fun saveUser(userIdInt: Int, eventPublisher: String)
}