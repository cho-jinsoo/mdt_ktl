package com.example.a111.Mountain

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.a111.MapActivity
import com.example.a111.R
import com.example.a111.model.MountainList
import kotlinx.android.synthetic.main.activity_mountain_rcm_detail.*

class MountainRcmDetailActivity : AppCompatActivity() {
    lateinit var datas : MountainList


    companion object{

        var mttype : Int = 0
        var hk_name :String=""
        var hk_level : String=""
        var hk_exp : Float= 0.0F
        var m_idcheck :Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mountain_rcm_detail)
        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 2) 유저보드리스트  데이터 받아오기
        datas = intent.getSerializableExtra("data") as MountainList
        m_idcheck = datas.m_id
        m_d_name.text = datas.m_name
        m_d_level.text = datas.m_level
        m_d_area.text = datas.area
        m_explain.text = datas.m_explain

        // 3) 맵 값 액티비티로 전달
        hk_name = datas.m_name
        hk_level = datas.m_level
        if(hk_level == "상"){
            hk_exp = 1.0F
        }else if(hk_level == "중"){
            hk_exp = 0.6F
        }else if(hk_level == "하"){
            hk_exp = 0.3F
        }


        // 4) 맵 액티비티 실행버튼
        run_mountain.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("알림창")
                .setMessage("등산유형을 선택해주세요")
                .setPositiveButton("동호회", DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this, "시작합니다", Toast.LENGTH_SHORT).show()
                    mttype = 2
                    val abc = Intent(this, MapActivity::class.java) // 인텐트를 생성
                    startActivity(abc)
                })
                .setNeutralButton("개인",    DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this, "시작합니다", Toast.LENGTH_SHORT)
                        .show()
                    mttype = 1
                    val abc = Intent(this, MapActivity::class.java) // 인텐트를 생성
                    startActivity(abc)
                })
                .setNegativeButton("소모임",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(this, "시작합니다", Toast.LENGTH_SHORT)
                            .show()
                    mttype = 3
                        val abc = Intent(this, MapActivity::class.java) // 인텐트를 생성
                        startActivity(abc)
                    })
            // 다이얼로그를 띄워주기
            builder.show()

           
        }

        // 5) 산에 맞는 사진 올리기

    }

    // 1_1)뒤로가기 툴바
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}