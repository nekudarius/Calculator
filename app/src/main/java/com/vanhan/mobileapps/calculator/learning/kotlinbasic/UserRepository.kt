package com.vanhan.mobileapps.calculator.learning.kotlinbasic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val api: UserApi
) {

    suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            api.getUser()
        }
    }

    suspend fun getPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            api.getPosts()
        }
    }
}