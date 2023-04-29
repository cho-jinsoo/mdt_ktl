package com.example.a111.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.a111.GlobalApplication
import com.example.a111.MainActivity
import com.example.a111.R
import com.example.a111.preference.PreferenceUtil
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import com.google.gson.JsonElement
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.usermgmt.StringSet.is_kakaotalk_user
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object { lateinit var prefs: PreferenceUtil }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val MainActivity = Intent(this, MainActivity::class.java)

        val joinActivity= Intent(this, JoinActivity::class.java)

        log_join.setOnClickListener {
            startActivity(joinActivity)


        }

        chekSharedPreference()
        val test= GlobalApplication.prefs.getString("u_id","")
        UserApiClient.instance.accessTokenInfo{tokenInfo, error ->
        if(error!=null){
            Log.d(TAG, "onCreate: 토큰정보보기실패",error)
        }else if(tokenInfo !=null){
            Log.d(TAG, "onCreate: 성공 회워번호${tokenInfo.id}")
        }
            
        }

        if (GlobalApplication.prefs.getString("u_id", "").isNullOrBlank() and GlobalApplication.prefs.getString("kakaolog","").isNullOrBlank()) {




            log_in_kakao.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(
                        this@LoginActivity,
                        callback = callback
                    )


                    //로그인성공시 페이지이동
                    startActivity(MainActivity)





                } else {
                    UserApiClient.instance.loginWithKakaoTalk(
                        this@LoginActivity,
                        callback = callback
                    )
                }

            }
            log_in.setOnClickListener {
                val u_id = id_text.text.toString()

                val u_pw = pw_text.text.toString()
                Log.d(TAG, "onCreate: ${u_pw}")
                Log.d(TAG, "onCreate: ${u_id}")
                RetrofitManager.instance.iRetrofit.login(u_id, u_pw)?.enqueue(object :
                    Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        if (response.isSuccessful) {
                            var result: JsonElement? = response.body()
                            Log.d(TAG, "onResponse: ${result.toString()}")
                            try {
                                val list = JSONObject(result.toString()).keys().iterator()
                                Log.d(TAG, "onResponse: ${list}")

                                var list1 = JSONObject(result.toString()).getJSONObject(list.next())
                                Log.d(TAG, "onResponse: ${list1}")


                                    if (list1.getString("u_id") != null) {
                                        GlobalApplication.prefs.setString(
                                            "u_id",
                                            list1.getString("u_id")
                                    )
                                    GlobalApplication.prefs.setString(
                                        "u_pw",
                                        list1.getString("u_pw")
                                    )


                                    startActivity(MainActivity)

                                } else {
                                    Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "통신 실패", Toast.LENGTH_SHORT).show()

                    }

                })
            }


        }else{

            
            Log.d(TAG, "onCreate: ${is_kakaotalk_user}")
            Log.d(TAG, "쉐어드 프리: ${GlobalApplication.prefs.getString("u_id","")},${GlobalApplication.prefs.getString("u_pw","")}")
            startActivity(MainActivity)
        }
    }

    private fun chekSharedPreference() {
//        username.setText(sharedPreferences.getString(getString(R.string.prompt_email),""))
//        password.setText(sharedPreferences.getString(getString(R.string.prompt_password),""))
    }

}
internal val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
    if (error != null) {
        Log.e("로그","로그인 실패- $error")
    } else if (token != null) {
        UserApiClient.instance.me { user, error ->
            val kakaoId = user!!.id
            //카카오 유저아이디저장

        }

        Log.d(TAG,"로그인성공 - 토큰 ${token},${is_kakaotalk_user}")

        UserApiClient.instance.me{user, error ->
            if(error!=null){
                Log.d(TAG, "onCreate: 토큰정보보기실패",error)
            }else if(user !=null){
                Log.d(TAG, "onCreate: 성공 회워번호,이메일,닉네임${user.id} ,${user.kakaoAccount?.email},${user.kakaoAccount?.legalName}")
                GlobalApplication.prefs.setString("kakaolog","${user.id}")
            }

        }


    }
}