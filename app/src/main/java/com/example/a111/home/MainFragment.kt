package com.example.a111.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.a111.HomeFragment
import com.example.a111.R
import com.example.a111.utils.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2


    companion object {
        const val TAG: String = "로그"
        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_main,container,false)
        tabLayout = view.findViewById(R.id.main_tab)
        viewPager2=view.findViewById(R.id.main_tab_viewpager)
        val adapter = HomeViewPagerAdapter(this)
        viewPager2.adapter=adapter
        val tabName = arrayOf<String>("이것저것","이야기","클럽","랭킹")

        TabLayoutMediator(tabLayout,viewPager2){
                tab,position -> tab.text = tabName[position].toString()
        }.attach()
        Log.d(Constants.TAG, "onCreateView:여기옴? ")
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d(Constants.TAG, "onTabSelected: 여기와야됨")
                viewPager2.currentItem= tab!!.position
                Log.d(Constants.TAG, "onTabSelected: ${tab.position}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        return view
    }


}