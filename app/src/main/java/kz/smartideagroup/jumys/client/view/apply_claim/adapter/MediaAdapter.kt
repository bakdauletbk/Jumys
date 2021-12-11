package kz.smartideagroup.jumys.client.view.apply_claim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_media.view.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.FOUR
import kz.smartideagroup.jumys.common.utils.MP4
import kz.smartideagroup.jumys.common.utils.ONE
import kz.smartideagroup.jumys.client.view.apply_claim.PlacingOrderFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class MediaAdapter : RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private var callback: PlacingOrderFragment

    private var mediaList: ArrayList<String> = ArrayList()

    constructor(callback: PlacingOrderFragment) : super() {
        this.callback = callback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(mediaList: List<String>) {
        this.mediaList.clear()
        notifyDataSetChanged()
        val arrayList = ArrayList<String>()
        arrayList.addAll(mediaList)
        arrayList.add("")//Не убрать last item (add media button)
        this.mediaList.addAll(arrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(media = mediaList[position], callback, position, mediaList.size - ONE)
        holder.itemView.iv_remove.onClick {
            callback.removeItemMediaList(position)
        }
    }

    override fun getItemCount(): Int = mediaList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val ivMedia = root.findViewById<ImageView>(R.id.iv_media)
        private val ivRemove = root.findViewById<ImageView>(R.id.iv_remove)
        private val ivVideoPlay = root.findViewById<ImageView>(R.id.iv_video_play)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(media: String, callback: PlacingOrderFragment, position: Int, mediaListSize: Int) {
            when (position == mediaListSize) {
                true -> {
                    ivMedia.background = callback.resources.getDrawable(R.drawable.ic_add_media)
                    ivRemove.visibility = View.GONE
                    itemView.onClick {
                        callback.navigateToCamera()
                    }
                }
                false -> {
                    if (media.takeLast(FOUR) == MP4) ivVideoPlay.visibility =
                        View.VISIBLE else ivVideoPlay.visibility = View.GONE
                    Glide.with(callback.requireActivity()).load(media).into(ivMedia)
                    itemView.onClick {
                        callback.previewMediaAlert(media)
                    }
                }
            }

        }
    }

}