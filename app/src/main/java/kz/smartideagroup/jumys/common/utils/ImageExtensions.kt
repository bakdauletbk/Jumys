package kz.smartideagroup.jumys.common.utils

import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImage(url: String?) {
    Glide
        .with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun convertDpToPixel(dp: Float): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}