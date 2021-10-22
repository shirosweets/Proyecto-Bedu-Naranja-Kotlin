package com.example.myapplication

import org.junit.Test


class LoginCheckTest {
    @Test
    fun `reject null password`() {
        val result = LoginManager.isPasswordValid(null)
        assert(!result)
    }

    @Test
    fun `reject short password`() {
        val result = LoginManager.isPasswordValid("1234567")
        assert(!result)
    }

    @Test
    fun `accept good password`() {
        val result = LoginManager.isPasswordValid("someGoodAndLongPassword")
        assert(result)
    }
}
