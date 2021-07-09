package kz.smartideagroup.jumys.common.helpers

import java.util.regex.Pattern

object Validators {
    private const val MIN_PASSWORD_LENGTH = 5
    private const val MIN_LOGIN_LENGTH = 16
    private const val MIN_SMS_LENGTH = 3
    private val CORRECT_LOGIN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
    )

    fun validateLogin(login: String): Boolean {
        return when {
            login.isBlank() -> false
            login.length <= MIN_LOGIN_LENGTH -> false
            else -> true
        }
    }

    fun validateSMS(sms: String): Boolean {
        return when {
            sms.isBlank() -> false
            sms.length <= MIN_SMS_LENGTH -> false
            else -> true
        }
    }

    fun validatePassword(password: String): Boolean {
        return when {
            password.isBlank() -> false
            password.length < MIN_PASSWORD_LENGTH -> false
            else -> true
        }
    }

}