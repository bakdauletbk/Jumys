package kz.smartideagroup.jumys.manager.view.apply_claim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.manager.view.apply_claim.PlacingOrderFragment

class MediaAdapter : RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private var callback: PlacingOrderFragment

    private var mediaList: ArrayList<String> = ArrayList()

    constructor(callback: PlacingOrderFragment) : super() {
        this.callback = callback
    }

    fun addList(mediaList: List<String>) {
        this.mediaList.clear()
        this.mediaList.addAll(mediaList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mediaList = mediaList[position], callback)
    }

    override fun getItemCount(): Int = mediaList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val ivMedia = root.findViewById<ImageView>(R.id.iv_media)

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(mediaList: String, callback: PlacingOrderFragment) {
            Glide.with(callback.requireActivity()).load(mediaList).into(ivMedia)
        }
    }

}