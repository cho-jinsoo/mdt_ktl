package com.example.a111.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a111.BoardFragment
import com.example.a111.HomeFragment


private  const val NUM_PAGESE=4
class HomeViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = NUM_PAGESE

    override fun createFragment(position: Int): Fragment {
        Log.d("로그", "createFragment:${position} ")

        return  when(position){
            0-> HomeFragment()
            1-> BoardFragment()
            2-> BgListFragment()
            3-> BgRankFragment()
            else -> HomeFragment()
        }
    }
}