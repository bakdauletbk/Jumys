package kz.smartideagroup.jumys.client.view.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.setImage
import kz.smartideagroup.jumys.client.model.response.home.RecommendedSpecialistsResponse
import kz.smartideagroup.jumys.client.view.home.HomeClientFragment

class RecommendedSpecialistsAdapter :
    RecyclerView.Adapter<RecommendedSpecialistsAdapter.ViewHolder> {

    private var callback: HomeClientFragment

    private var recommendedList: ArrayList<RecommendedSpecialistsResponse> = ArrayList()

    constructor(callback: HomeClientFragment) : super() {
        this.callback = callback
    }

    fun addList(recommendedList: List<RecommendedSpecialistsResponse>) {
        this.recommendedList.clear()
        this.recommendedList.addAll(recommendedList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recommended_specialists, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommended = recommendedList[position], callback)
    }

    override fun getItemCount(): Int = recommendedList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {

        private val ivRecommended = root.findViewById<ImageView>(R.id.iv_recommended)
        private val tvName = root.findViewById<TextView>(R.id.tv_name)
        private val tvDescription = root.findViewById<TextView>(R.id.tv_description)
        private val tvRating = root.findViewById<TextView>(R.id.tv_rating)

        @SuppressLint("SetTextI18n")
        fun bind(recommended: RecommendedSpecialistsResponse, callback: HomeClientFragment) {
            ivRecommended.setImage(recommended.img)
            tvName.text = recommended.name
            tvDescription.text = recommended.description
            tvRating.text = recommended.rating.toString()
        }
    }

}