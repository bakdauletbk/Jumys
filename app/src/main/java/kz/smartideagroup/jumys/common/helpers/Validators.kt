package kz.smartideagroup.jumys.common.helpers

import java.util.regex.Pattern

object Validators {
    private const val MIN_PASSWORD_LENGTH = 5
    private const val MIN_LOGIN_LENGTH = 16
    private const val MIN_SMS_LENGTH = 3
    private const val INN_LENGTH = 11
    private const val DAYS_LENGTH = 1
    private const val YEARS_LENGTH = 3

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

    fun validateInn(inn: String): Boolean {
        return when {
            inn.isBlank() -> false
            inn.length <= INN_LENGTH -> false
            else -> true
        }
    }

    fun validateDays(days: String): Boolean {
        return when {
            days.isBlank() -> false
            days.length <= DAYS_LENGTH -> false
            else -> true
        }
    }

    fun validateYears(years: String): Boolean {
        return when {
            years.isBlank() -> false
            years.length <= YEARS_LENGTH -> false
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