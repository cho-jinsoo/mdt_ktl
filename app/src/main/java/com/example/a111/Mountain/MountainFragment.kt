package com.example.a111.Mountain

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.a111.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MountainFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    companion object {
        fun newInstance() : MountainFragment = MountainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.fragment_mountain, container, false)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.search_view_pager)

        val adapter = MountainFragViewPagerAdapter(this)
        viewPager.adapter = adapter

        val tabName = arrayOf<String>("개인 등산", "그룹 등산")

        //슬라이드로 이동했을 때, 탭이 같이 변경되도록
        TabLayoutMediator(tabLayout, viewPager){
                tab, position -> tab.text = tabName[position].toString()
        }.attach()

        //탭이 선택되었을 때, 뷰페이저가 같이 변경되도록
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        return view

    }
}