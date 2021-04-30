package kz.smartideagroup.jumys.on_boarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.jumys.R

class OnBoardingPagerAdapter : RecyclerView.Adapter<OnBoardingPagerAdapter.OnBoardingVH>() {

    private val layouts = listOf(
        OnBoardingLayout.ON_BOARDING_PAGE_ONE,
        OnBoardingLayout.ON_BOARDING_PAGE_TWO
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingVH {
        return when (viewType) {
            OnBoardingLayout.ON_BOARDING_PAGE_ONE.ordinal -> {
                OnBoardingVH(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.on_boarding_page_one, parent, false)
                )
            }
            else -> {
                OnBoardingVH(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.on_boarding_page_two, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: OnBoardingVH, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return layouts[position].ordinal
    }

    override fun getItemCount(): Int = layouts.size

    class OnBoardingVH(private val root: View) : RecyclerView.ViewHolder(root)

    private enum class OnBoardingLayout {
        ON_BOARDING_PAGE_ONE,
        ON_BOARDING_PAGE_TWO
    }

}