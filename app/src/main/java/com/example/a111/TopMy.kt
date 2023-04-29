package com.example.a111

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.a111.MainActivity.Companion.loginid
import com.example.a111.MainActivity.Companion.share_username
import com.example.a111.login.LoginActivity
import kotlinx.android.synthetic.main.activity_top_my.*

class TopMy : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_my)


        // 0.0) 로그아웃 버튼 리스너
        logout_btn.setOnClickListener {
            if (loginid.isNullOrBlank()) {
                GlobalApplication.prefs.logout(applicationContext)
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                System.exit(0)
            } else {
                GlobalApplication.prefs.logout(applicationContext)
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                System.exit(0)
            }
        }

        // 0.5) 내 활동기록보기
        topmylinear03.setOnClickListener {
            val abc = Intent(this, TopMyHikingRecord::class.java) // 인텐트를 생성
                    startActivity(abc)  // 화면 전환하기
        }

        // 내가 쓴 글 보기
        topmylinear04.setOnClickListener {
            val abc = Intent(this, TopMyWriteboard::class.java) // 인텐트를 생성
            startActivity(abc)  // 화면 전환하기
        }

        //내가 쓴 댓글 보기
        topmylinear05.setOnClickListener {
            val abc = Intent(this, TopMyCommentboard::class.java) // 인텐트를 생성
            startActivity(abc)  // 화면 전환하기
        }

        //공지사항 보기
        topmylinear06.setOnClickListener {
            val abc = Intent(this, NoticeActivity::class.java) // 인텐트를 생성
            startActivity(abc)  // 화면 전환하기
        }
        //이벤트 보기
        topmylinear07.setOnClickListener {
            val abc = Intent(this, EventActivity::class.java) // 인텐트를 생성
            startActivity(abc)  // 화면 전환하기
        }

        //클럽만드기 보기
        topmylinear09.setOnClickListener {
            val abc = Intent(this, CreateClubActivity::class.java) // 인텐트를 생성
            startActivity(abc)  // 화면 전환하기
        }

        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.myinfotoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        // 2) 내 프로필 표시하기
        topmyname.text =  share_username

        // 3) 레벨 표시하기
        if( MainActivity.share_userlevel == 1) {
            topmy_profileimage.setImageResource(R.drawable.level_1_squirrel_80x80)
        }
        else if(MainActivity.share_userlevel == 2){
            topmy_profileimage.setImageResource(R.drawable.level_2_rabbit_80x80)

        }
        else if( MainActivity.share_userlevel == 3) {
            topmy_profileimage.setImageResource(R.drawable.level_3_owl_80x80)
        }
        else if(MainActivity.share_userlevel == 4){
            topmy_profileimage.setImageResource(R.drawable.level_4_eagle_80x80)

        }
        else if( MainActivity.share_userlevel == 5) {
            topmy_profileimage.setImageResource(R.drawable.level_5_bear_80x80)
        }
        else if(MainActivity.share_userlevel == 6){
            topmy_profileimage.setImageResource(R.drawable.level_6_eagle_80x80)

        }



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