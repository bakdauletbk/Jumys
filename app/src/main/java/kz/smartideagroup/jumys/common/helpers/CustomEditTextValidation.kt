package kz.smartideagroup.jumys.common.helpers

import android.widget.EditText

fun EditText.validateDays(string: String) : Boolean{
    return if (Validators.validateDays(string)) {
        true
    } else {
        this.error = ""
        false
    }
}

fun EditText.validateYear(string: String) : Boolean{
    return if (Validators.validateYears(string)) {
        true
    } else {
        this.error = ""
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