package kz.smartideagroup.jumys.manager.view.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.item_banner.view.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.setImage
import kz.smartideagroup.jumys.manager.model.response.home.BannerResponse

class BannerAdapter(val context: Context?) : PagerAdapter() {

    private var inflater: LayoutInflater? = null

    private var sliderList: ArrayList<BannerResponse> = ArrayList()

    fun addImageSlider(sliderList: List<BannerResponse>) {
        this.sliderList.addAll(sliderList)
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return sliderList.size
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater!!.inflate(R.layout.item_banner, null)

        view.iv_banner.setImage(sliderList[position].img)

        val pager = container as ViewPager

        pager.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val pager = container as ViewPager
        val view = `object` as View
        pager.removeView(view)
    }
}