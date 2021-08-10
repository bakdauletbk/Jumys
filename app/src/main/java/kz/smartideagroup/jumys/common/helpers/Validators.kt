package kz.smartideagroup.jumys.common.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.util.*
import java.util.regex.Pattern

object Validators {
    private const val MIN_PASSWORD_LENGTH = 5
    private const val MIN_LOGIN_LENGTH = 16
    private const val MIN_SMS_LENGTH = 3
    private const val INN_LENGTH = 11
    private const val BIN_LENGTH = 11
    private const val REGISTER_NUMBER= 16
    private const val DAYS_LENGTH = 1
    private const val YEARS_LENGTH = 3
    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    private val CORRECT_LOGIN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
    )
    fun validateEmail(email: String): Boolean{
        return when {
            email.isBlank() -> false
            !EMAIL_ADDRESS.matcher(email).matches() -> false
            else -> true
        }
    }

    fun validateLogin(login: String): Boolean {
        return when {
            login.isBlank() -> false
            login.length <= MIN_LOGIN_LENGTH -> false
            else -> true
        }
    }
    fun validateRegisterNumber(number: String): Boolean{
        return when {
            number.isBlank() -> false
            number.length <= REGISTER_NUMBER -> false
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
    fun validateBin(bin: String): Boolean {
        return when {
            bin.isBlank() -> false
            bin.length <= BIN_LENGTH -> false
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