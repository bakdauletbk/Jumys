package kz.smartideagroup.jumys.specialist.view.apply_claim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.specialist.model.response.apply_claim.AddressOrderResponse
import kz.smartideagroup.jumys.specialist.view.apply_claim.OrderListFragment
import kz.smartideagroup.jumys.common.utils.setImage

class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private var orderList: ArrayList<AddressOrderResponse> = ArrayList()

    private var callback: OrderListFragment

    constructor(callback: OrderListFragment) : super() {
        this.callback = callback
    }

    fun addLoans(orderList: List<AddressOrderResponse>) {
        this.orderList.clear()
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_list, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        holder.bind(orderList = orderList[position], callback)
    }

    class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        private val tvName = root.findViewById<TextView>(R.id.tv_name_order)
        private val tvDescription = root.findViewById<TextView>(R.id.tv_description_order)
        private val tvPriceStatus = root.findViewById<TextView>(R.id.tv_price_status)
        private val tvAddress = root.findViewById<TextView>(R.id.tv_address)
        private val ivProfile = root.findViewById<ImageView>(R.id.iv_profile)

        @SuppressLint("SetTextI18n")
        fun bind(orderList: AddressOrderResponse, callback: OrderListFragment) {
            tvName.text = orderList.name
            tvDescription.text = orderList.description
            ivProfile.setImage(orderList.img)
            tvAddress.text = orderList.address

            root.setOnClickListener {
                callback.onClickItem(orderList)
            }
        }
    }

}