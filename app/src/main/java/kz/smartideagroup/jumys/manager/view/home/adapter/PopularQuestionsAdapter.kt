package kz.smartideagroup.jumys.manager.view.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.manager.model.response.home.PopularQuestionResponse
import kz.smartideagroup.jumys.manager.view.home.HomeManagerFragment

class PopularQuestionsAdapter : RecyclerView.Adapter<PopularQuestionsAdapter.ViewHolder> {

    private var callback: HomeManagerFragment

    private var popularList: ArrayList<PopularQuestionResponse> = ArrayList()

    constructor(callback: HomeManagerFragment) : super() {
        this.callback = callback
    }

    fun addList(popularList: List<PopularQuestionResponse>) {
        this.popularList.clear()
        this.popularList.addAll(popularList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_advice, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(popular = popularList[position], callback)
    }

    override fun getItemCount(): Int = popularList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val tvTitle = root.findViewById<TextView>(R.id.tv_title_advice)
        private val tvDetail = root.findViewById<TextView>(R.id.tv_description_advice)

        @SuppressLint("SetTextI18n")
        fun bind(popular: PopularQuestionResponse, callback: HomeManagerFragment) {
            tvTitle.text = popular.title
            tvDetail.text = popular.description
        }
    }

}