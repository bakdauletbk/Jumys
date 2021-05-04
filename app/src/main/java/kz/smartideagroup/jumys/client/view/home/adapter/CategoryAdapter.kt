package kz.smartideagroup.jumys.client.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.client.model.response.home.CategoryResponse
import kz.smartideagroup.jumys.client.view.home.HomeClientFragment
import kz.smartideagroup.jumys.common.utils.setImage

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private var callback: HomeClientFragment

    private var categoryList: ArrayList<CategoryResponse> = ArrayList()

    constructor(callback: HomeClientFragment) : super() {
        this.callback = callback
    }

    fun addList(categoryList: List<CategoryResponse>) {
        this.categoryList.clear()
        this.categoryList.addAll(categoryList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(category = categoryList[position], callback)
    }

    override fun getItemCount(): Int = categoryList.size


    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val tvTitle = root.findViewById<TextView>(R.id.tv_title)
        private val tvDescription = root.findViewById<TextView>(R.id.tv_description)
        private val ivCategory = root.findViewById<ImageView>(R.id.iv_category)

        fun bind(category: CategoryResponse, callback: HomeClientFragment) {
            tvTitle.text = category.title
            tvDescription.text = category.description
            ivCategory.setImage(category.img)
        }
    }

}