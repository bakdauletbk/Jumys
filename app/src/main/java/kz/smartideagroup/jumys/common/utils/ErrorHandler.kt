package kz.smartideagroup.jumys.common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import kz.smartideagroup.jumys.R
import retrofit2.Response

class ErrorHandler<T> {

    private var context: Context? = null
    private var response: Response<T>? = null

    fun ErrorHandler(context: Context, response: Response<T>) {
        this.context = context
        this.response = response
        checkErrorResponse()
    }

    private fun checkErrorResponse() {
        when (response?.code()) {
            RESPONSE_UPDATE_APP -> {
                context?.getString(R.string.our_application_has_been_updated_please_update)?.let {
                    showAlertDialog(context!!,
                        it
                    )
                }
            }
            RESPONSE_UNAUTHORIZED -> {
                Toast.makeText(context,
                    context?.getString(R.string.you_are_logged_in_under_your_account_on_another_device),
                    Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun showAlertDialog(
        context: Context,
        message: String,
    ) {
        val builder = MaterialDialog.Builder(context)
            .title(message)
            .dividerColor(context.resources.getColor(R.color.green))
            .positiveText("OK")
            .positiveColorRes(R.color.green)
        builder.cancelable(false)
        builder.onAny { dialog: MaterialDialog?, which: DialogAction ->
            if (which == DialogAction.POSITIVE) {
                val appPackageName =
                    context.packageName
                try {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(URI_PLAY_MARKET + appPackageName)
                        )
                    )
                    (context as Activity).finish()
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(URI_APP + appPackageName)
                        )
                    )
                    (context as Activity).finish()
                }
            }
        }

        builder.show()
    }

}