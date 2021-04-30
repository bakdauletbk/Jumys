package kz.smartideagroup.jumys.common.utils

import android.content.Context
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.util.Base64

object UtilsObject {

    fun getCurrentUserPhoneNumber(context: Context): String? {
        val telephonyManager: TelecomManager =
            context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        val phoneAccountHandle: PhoneAccountHandle? = telephonyManager.simCallManager
        return when (phoneAccountHandle != null) {
            true -> telephonyManager.getLine1Number(phoneAccountHandle)
            false -> null
        }
    }

//    fun <T> handleErrorMessage(response: Response<T>): String? {
//
//    }

    fun formatNumber(phone: String): String {
        var num = ""
        var c: Char
        for (i in phone.indices) {
            c = phone[i]
            if (c == '(' || c == ')' || c == ' ' || c == '+' || c == '-') continue
            num += c
        }
        return num
    }

    fun encodeToBase64(string: String): String {
        return Base64.encodeToString(
            formatNumber(string).toByteArray(),
            Base64.NO_WRAP
        )
    }

}