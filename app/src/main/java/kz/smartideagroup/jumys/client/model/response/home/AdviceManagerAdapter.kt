package kz.smartideagroup.jumys.client.model.response.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.specialist.model.response.home.AdviceResponse
import kz.smartideagroup.jumys.client.view.home.HomeClientFragment

class AdviceManagerAdapter : RecyclerView.Adapter<AdviceManagerAdapter.ViewHolder> {

    private var callback: HomeClientFragment

    private var adviceList: ArrayList<AdviceResponse> = ArrayList()

    constructor(callback: HomeClientFragment) : super() {
        this.callback = callback
    }

    fun addList(adviceList: List<AdviceResponse>) {
        this.adviceList.clear()
        this.adviceList.addAll(adviceList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_advice, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(advice = adviceList[position], callback)
    }

    override fun getItemCount(): Int = adviceList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val tvTitle = root.findViewById<TextView>(R.id.tv_title_advice)
        private val tvDescription = root.findViewById<TextView>(R.id.tv_description_advice)
        fun bind(advice: AdviceResponse, callback: HomeClientFragment) {
            tvTitle.text = advice.title
            tvDescription.text = advice.description
        }
    }
}