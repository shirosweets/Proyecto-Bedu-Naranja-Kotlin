package com.example.myapplication

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class LoginCheckTest {

    @Test
    fun `empty email and empty password return EMPTY_FIELDS`() {
        val result = LoginCheck.checkEmailAndPassword("","")
        assertThat(result).contains("EMPTY_FIELDS")
    }

    @Test
    fun `correct email and correct password return SUCCESSFUL_LOGIN`() {
        val result = LoginCheck.checkEmailAndPassword("george.bluth@reqres.in","george123")
        assertThat(result).contains("SUCCESSFUL_LOGIN")
    }

    @Test
    fun `password characters is less than 8 return PASSWORD_CHARACTERS_IS_LESS_THAN_8`() {
        val result = LoginCheck.checkEmailAndPassword("george.bluth@reqres.in","george")
        assertThat(result).contains("PASSWORD_CHARACTERS_IS_LESS_THAN_8")
    }

    @Test
    fun `empty email and correct password return EMAIL_IS_EMPTY`() {
        val result = LoginCheck.checkEmailAndPassword("","george123")
        assertThat(result).contains("EMAIL_IS_EMPTY")
    }

    @Test
    fun `correct email and empty password return PASSWORD_IS_EMPTY`() {
        val result = LoginCheck.checkEmailAndPassword("george.bluth@reqres.in","")
        assertThat(result).contains("PASSWORD_IS_EMPTY")
    }
}