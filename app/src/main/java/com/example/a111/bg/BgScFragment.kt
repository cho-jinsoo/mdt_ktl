package com.example.a111.bg

import android.content.Intent

import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView

import com.example.a111.R
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.fragment_bg_sc.*



class BgScFragment : Fragment() {





    companion object {
        fun newInstance():BgScFragment =BgScFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bg_sc, container, false)
    }
}