package kz.smartideagroup.jumys.specialist.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.specialist.model.response.home.HistoryResponse
import kz.smartideagroup.jumys.specialist.view.home.HomeSpecialistFragment
import kz.smartideagroup.jumys.common.utils.setImage

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private var callback: HomeSpecialistFragment

    private var historyList: ArrayList<HistoryResponse> = ArrayList()

    constructor(callback: HomeSpecialistFragment) : super() {
        this.callback = callback
    }

    fun addList(historyList: List<HistoryResponse>) {
        this.historyList.clear()
        this.historyList.addAll(historyList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(history = historyList[position], callback)
    }

    override fun getItemCount(): Int = historyList.size

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val ivHistory = root.findViewById<ImageView>(R.id.iv_history)
        fun bind(history: HistoryResponse, callback: HomeSpecialistFragment) {
            ivHistory.setImage(history.img)
        }
    }
}