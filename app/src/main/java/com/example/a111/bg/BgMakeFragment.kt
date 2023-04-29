package com.example.a111.bg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a111.MapActivity
import com.example.a111.R
import com.example.a111.databinding.FragmentBgMakeBinding
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.fragment_bg_make.*
import kotlinx.android.synthetic.main.fragment_mountain_recommend.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BgMakeFragment : Fragment() {
    private lateinit var binding:FragmentBgMakeBinding
    var g_choice : Int = 0

    companion object {

        fun newInstance():BgMakeFragment = BgMakeFragment()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: 온크리에트")




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        radio_group.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.bigroup -> g_choice = 1

                R.id.smallgroup -> g_choice = 2

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentBgMakeBinding.inflate(inflater,container,false)

        binding.bgMakeBnt.setOnClickListener {

            val u_id=bg_u_id.text.toString()
            val g_name=bg_name.text.toString()
            val g_intro =bg_itro.text.toString()
            Log.d(TAG, "onCreate: ${g_intro}")
            Log.d(TAG, "onCreate: ${g_name}")
            Log.d(TAG, "onCreate: ${u_id}")


            RetrofitManager.instance.iRetrofit.createbg(g_choice, u_id, g_name, g_intro)?.enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        var result: String? = response.body()
                        Log.d(TAG, "onResponse: " + result)
                    } else {
                        Log.d(TAG, "onResponse: " + response.body() + response)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d(TAG, "onFailure: " + t.message.toString())
                }

            })
        }
        return binding.root
    }




}