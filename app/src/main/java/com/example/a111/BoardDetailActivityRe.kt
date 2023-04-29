package com.example.a111

import android.animation.ValueAnimator
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.MainActivity.Companion.TAG
import com.example.a111.MainActivity.Companion.loginid
import com.example.a111.MainActivity.Companion.share_userlevel
import com.example.a111.MainActivity.Companion.share_username
import com.example.a111.model.CommentList
import com.example.a111.model.UserBoardList
import com.example.a111.model.heartlist
import com.example.a111.retrofit.RetrofitManager
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_board_detail_cardview.*
import kotlinx.android.synthetic.main.activity_board_detail_re.*
import kotlinx.android.synthetic.main.fragment_board_cardview.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class BoardDetailActivityRe : AppCompatActivity() {

    lateinit var datas : UserBoardList

    var isliked: Boolean = false
    var bidchecking : Int = 0
    var likechoice : Int = 0
    var likecheck : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_detail_re)

        Log.d("로그", "[보드 디테일 액티비 접속!] ")



        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 2) 유저보드리스트  데이터 받아오기
        datas = intent.getSerializableExtra("data") as UserBoardList
        val board_id = datas.b_id
        bidchecking = datas.b_id
        ubd_u_id.text = datas.u_name
        ubd_b_date.text = datas.b_date
        ubd_b_title.text = datas.b_Title
        ubd_b_content.text = datas.b_Content

        if( datas.u_level == 1) {
            profileimage_ub.setImageResource(R.drawable.level_1_squirrel_80x80)
        }
        else if(datas.u_level == 2){
            profileimage_ub.setImageResource(R.drawable.level_2_rabbit_80x80)

        }
        else if( datas.u_level == 3) {
            profileimage_ub.setImageResource(R.drawable.level_3_owl_80x80)
        }
        else if(datas.u_level == 4){
            profileimage_ub.setImageResource(R.drawable.level_4_eagle_80x80)

        }
        else if( datas.u_level == 5) {
            profileimage_ub.setImageResource(R.drawable.level_5_bear_80x80)
        }
        else if(datas.u_level == 6){
            profileimage_ub.setImageResource(R.drawable.level_6_eagle_80x80)

        }


        // 3.0) 댓글 작성자 닉네임값 설정
        comment_id.text = share_username


        // 3.1) 댓글 작성하기
        button_comment_send.setOnClickListener {


            val comment_contents = comment_boardfree.text.toString()
            Log.d("로그", "onCreate: 댓글 내용 $comment_contents")
            val b_id = board_id.toString()
            Log.d("로그", "onCreate 게시글 번호 ${b_id}")
            val u_name = comment_id.text.toString()
            Log.d("로그", "onCreate: 댓글 작성자 $u_name")
            val u_level = share_userlevel


            RetrofitManager.instance.iRetrofit.userboardcommentapp(comment_contents, u_name, b_id,u_level)
                .enqueue(object :
                    Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성고된 경우
                            var result: String? = response.body()
                            Log.d("로그", "onResponse 성공: " + result?.toString())
                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d("로그", "onResponse 실패" + response.body() + response)
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        Log.d("로그", "onFailure 에러: " + t.message.toString())
                    }
                })
            finish()
            startActivity(intent)

        }


        // 4) 댓글보드


        RetrofitManager.instance.iRetrofit.userboardcommentlist().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    var result: JsonElement? = response.body()
                    Log.d("로그", "유저보드 댓글보드 onResponse 성공: " + result?.toString())
                    getlist(result)
                    Log.d(ContentValues.TAG, "${result} - onResponse() called")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("로그", "유저보드 댓글보드  onResponse 실패" + response.body().toString())
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("로그", "유저보드 댓글보드  onFailure 에러: " + t.message.toString())
            }
        })

        // 5) 좋아요 누르기
        isliked = false

        liket.setOnClickListener {
            if (isliked == false) {
                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(1000)
                animator.addUpdateListener { animation: ValueAnimator ->
                    //방법1
                    liket.progress = animation.animatedValue as Float
                }
                animator.start()
                isliked = true

                var b_id = bidchecking
                var u_id = loginid
                likechoice = 1
                RetrofitManager.instance.iRetrofit.like(b_id, likechoice, u_id).enqueue(object :
                    Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성고된 경우
                            var result: String? = response.body()
                            Log.d("로그", "onResponse 성공 라이크: " + result?.toString())
                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d("로그", "onResponse 실패 라이크" + response.body() + response)
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        Log.d("로그", "onFailure 에러 라이크 : " + t.message.toString())
                    }
                })


            }
            else{
                //좋아요 상태일때


                val animator = ValueAnimator.ofFloat(0f, 0f).setDuration(1000)
                animator.addUpdateListener { animation: ValueAnimator ->

                    //방법1
                    liket.progress = animation.animatedValue as Float

                }
                animator.start()
                isliked = false

                Log.d("로그", "좋아요 버튼이 클릭되었다. isliked : ${isliked} ")


            }



            if(likecheck == loginid){
                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(1000)
                animator.addUpdateListener { animation: ValueAnimator ->
                    //방법1
                    liket.progress = animation.animatedValue as Float
                }
                animator.start()
                isliked = true

            }
        }



        // 하트 판별
        RetrofitManager.instance.iRetrofit.likedo().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    var result: JsonElement? = response.body()
                    Log.d("로그", "유저보드 댓글보드 onResponse 성공: " + result?.toString())
                    getlistdo(result)
                    Log.d(ContentValues.TAG, "${result} - onResponse() called")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("로그", "유저보드 댓글보드  onResponse 실패" + response.body().toString())
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("로그", "유저보드 댓글보드  onFailure 에러: " + t.message.toString())
            }
        })


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



    // 4-2) 코멘트 출력
    private fun getlist(result: JsonElement?) {
        var ubcommentlist = ArrayList<CommentList>()
        lateinit var boardCommentAdapter: BoardCommentAdapter

        try {
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()


            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val b_id = list1.getInt("b_id")
                val u_name = list1.getString("u_name")
                val comment_contents = list1.getString("comment_contents")
                val comment_date = list1.getLong("comment_date")
                val parser = SimpleDateFormat("MM.dd")
                val time = parser.format(comment_date)
                val u_level = list1.getInt("u_level")

                if(b_id == datas.b_id) {
                    var cl = CommentList(
                        b_id = b_id,
                        u_name = u_name,
                        comment_contents = comment_contents,
                        comment_date = time,
                        u_level = u_level
                        )
                    ubcommentlist.add(cl)

                }
            }
            boardCommentAdapter = BoardCommentAdapter()
            boardCommentAdapter.submitList(ubcommentlist)

            ubcommentRecycler.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = boardCommentAdapter
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // 5-2) 하트 판별 출력
    private fun getlistdo(result: JsonElement?) {
        var heart = ArrayList<heartlist>()
        lateinit var boardCommentAdapter: BoardCommentAdapter

        try {
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()


            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val lc_num = list1.getInt("lc_num")
                val b_id = list1.getInt("b_id")
                val u_id = list1.getString("u_id")

                Log.d("로그", "BoardDetailActivityRe - ${datas.b_id}")
                if(b_id == datas.b_id) {
                    if(u_id == loginid){

                    var cl = heartlist(
                        lc_num = lc_num,
                        b_id = b_id,
                        u_id = u_id,
                    )
                    heart.add(cl)
                    likecheck = u_id


                    }

                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
