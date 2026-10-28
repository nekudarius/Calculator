package com.vanhan.mobileapps.calculator.learning.kotlinbasic

class UserApi {
    suspend fun getPosts(): List<Post> {
        return listOf(
            Post("asdjkfa"),
            Post("jangan")
        )
    }

    suspend fun getUser(): User {
        return User(
            username = "darius",
            email = "sample@gmail.com"
        )
    }
}