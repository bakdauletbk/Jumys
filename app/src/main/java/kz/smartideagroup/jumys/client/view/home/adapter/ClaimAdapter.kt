package kz.smartideagroup.jumys.client.view.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.client.model.response.home.ClaimResponse
import kz.smartideagroup.jumys.client.view.home.HomeClientFragment

class ClaimAdapter : RecyclerView.Adapter<ClaimAdapter.ViewHolder> {

    private var callback: HomeClientFragment

    private var claimList: ArrayList<ClaimResponse> = ArrayList()

    constructor(callback: HomeClientFragment) : super() {
        this.callback = callback
    }

    fun addList(claimList: List<ClaimResponse>) {
        this.claimList.clear()
        this.claimList.addAll(claimList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_claim, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(claim = claimList[position], callback)
    }

    override fun getItemCount(): Int = claimList.size


    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val tvTitle = root.findViewById<TextView>(R.id.tv_title)
        private val tvDetail = root.findViewById<TextView>(R.id.tv_detail)
        private val tvDescriptionClaim = root.findViewById<TextView>(R.id.tv_description_claim)
        private val tvPriceClaim = root.findViewById<TextView>(R.id.tv_price_claim)
        private val llShowHide = root.findViewById<LinearLayout>(R.id.ll_show_hide)
        private val llDetailClaim = root.findViewById<LinearLayout>(R.id.ll_detail_claim)
        private val ivDetail = root.findViewById<ImageView>(R.id.iv_show)

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(claim: ClaimResponse, callback: HomeClientFragment) {
            var isShow = false

            tvTitle.text = claim.title
            tvDetail.text = claim.detail
            tvDescriptionClaim.text = claim.description
            tvPriceClaim.text = "${claim.price!!.toLong()} тг"

            llShowHide.setOnClickListener {
                when (isShow) {
                    false -> {
                        isShow = true
                        ivDetail.background =
                            callback.resources.getDrawable(R.drawable.ic_close_claim_item)
                        llDetailClaim.visibility = View.VISIBLE
                    }
                    true -> {
                        isShow = false
                        ivDetail.background =
                            callback.resources.getDrawable(R.drawable.ic_open_item_claim)
                        llDetailClaim.visibility = View.GONE
                    }
                }
            }
        }
    }
}