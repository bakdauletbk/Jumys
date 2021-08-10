package kz.smartideagroup.jumys.common.helpers

import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import kz.smartideagroup.jumys.common.utils.MAX_DAY
import kz.smartideagroup.jumys.common.utils.MAX_MONTH
import kz.smartideagroup.jumys.common.utils.MAX_YEAR
import kz.smartideagroup.jumys.common.utils.MAX_YEAR_LAST



fun EditText.validateDays(string: String, errorText: String) : Boolean{
    return if (Validators.validateDays(string) && string.toInt() <= MAX_DAY) {
        true
    } else {
        this.error = "Напишите дату вашего рождения"
        false
    }
}

fun EditText.validateMonth(string: String, errorText:String): Boolean{
    return if (Validators.validateDays(string) && string.toInt() <= MAX_MONTH ){
        true
    } else {
        this.error = "Введите ваш месяц рождения"
        false
    }
}

fun EditText.validateYear(string: String, errorText: String) : Boolean{
    return if (Validators.validateYears(string) &&  MAX_YEAR_LAST <= string.toInt() && string.toInt() <= MAX_YEAR) {
        true
    } else {
        this.error = "Напишите свой год рождения"
        false
    }
}

fun EditText.validateIin(string: String,errorText : String) : Boolean{
    return if (Validators.validateInn(string)) {
        true
    } else {
        this.error = errorText
        false
    }
}
fun EditText.validateBin(string:String,errorText: String): Boolean{
    return if (Validators.validateBin(string)) {
        true
    } else {
        this.error = errorText
        false
    }
}
fun EditText.validateRegisterNumber(string:String,errorText: String): Boolean{
    return if (Validators.validateRegisterNumber(string)){
        true
    } else {
        this.error = errorText
        false
    }
}
fun EditText.validateEmail(string: String,errorText: String): Boolean{
    return if (Validators.validateEmail(string)){
        true
    } else {
        this.error = errorText
        false
    }
}
