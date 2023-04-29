package com.example.a111

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.a111.Mountain.MountainFragment
import com.example.a111.Mountain.MountainRecommendFragment
import com.example.a111.databinding.ActivityMainBinding
import com.example.a111.home.MainFragment
import com.example.a111.login.LoginActivity
import com.example.a111.model.UsersList
import com.example.a111.retrofit.RetrofitManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_photo.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //플래그
    private lateinit var homeFragment: HomeFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var mountainRecommendFragment: MountainRecommendFragment
    lateinit var binding: ActivityMainBinding







    //Manifest 에서 설정한 권한을 가지고 온다.
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA ,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val PERMISSIONS_REQUEST = 100




    //권한 플래그값 정의
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    //카메라와 갤러리를 호출하는 플래그
    val FLAG_REQ_CAMERA = 101
    val FLAG_REA_STORAGE = 102

    companion object {

        const val TAG: String = "로그"

        // 아이디값
        val loginid = GlobalApplication.prefs.getString("u_id", "")
        val kakaoid = GlobalApplication.prefs.getString("kakaolog", "")
        var share_username :String =""
        var share_userlevel : Int = 0

    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)


        Log.d(TAG, "MainActivity - onCreate() called")




        main_bottom_navigation.setOnNavigationItemSelectedListener(onBottomNavItemSelectedListener)


        mainFragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, mainFragment).commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        //탑바 버튼클릭
        val emergencyIntent = Intent(this, TopEmergency::class.java) // 인텐트를 생성
        button_emergency.setOnClickListener { // 버튼 클릭시 할 행동
            startActivity(emergencyIntent)
        }

        val mpageIntent = Intent(this, TopMy::class.java) // 인텐트를 생성
        mypage.setOnClickListener { // 버튼 클릭시 할 행동
            Log.d(TAG, "MainActivity - button_mypage() called")
            startActivity(mpageIntent)  // 화면 전환하기
        }




        // 2) 내 정보 출력
        RetrofitManager.instance.iRetrofit.myinfolist().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    var result: JsonElement? = response.body()
                    Log.d("로그", "마이 페이지 레트로핏 onResponse 성공: " + result?.toString())
                    getlist(result)
                    Log.d(ContentValues.TAG, "${result} - onResponse() called")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("로그", "마이 페이지  onResponse 실패" + response.body().toString())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("로그", "마이 페이지 레트로핏 onFailure 에러: " + t.message.toString())
            }
        })




        // 3) 로그인 null값일 경우 로그인하기
        if(loginid == ""){
            movelogin()
        }



    }

    // 2-1)
    private fun getlist(result: JsonElement?) {
        var my_info_view = ArrayList<UsersList>()

        try {
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()

            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val u_id = list1.getString("u_id")
                val u_address = list1.getString("u_address")
                val u_pw = list1.getString("u_pw")
                val u_name = list1.getString("u_name")
                val u_level = list1.getInt("u_level")
                val u_experience: String = list1.getString("u_experience")




                if(loginid == u_id) {
                    Log.d("로그", "getlist logdid,u_id 체크 ${loginid}, ${u_id}")
                    var Mymodel = UsersList(
                        u_id = u_id,
                        u_address = u_address,
                        u_pw = u_pw,
                        u_name = u_name,
                        u_level = u_level,
                        u_experience = u_experience
                    )

                    my_info_view.add(Mymodel)

                    // 로그인유저 이름 및 레벨 전역변수화
                    share_username = u_name.toString()
                    share_userlevel = u_level


                    // 상단 아이콘 유저이름 표시
                    topname.text = u_name.toString()
                    if( u_level == 1) {
                        button_mypage.setImageResource(R.drawable.level_1_squirrel_25x25)
                    }
                    else if(u_level == 2){
                        button_mypage.setImageResource(R.drawable.level_2_rabbit_25x25)
                    }
                    else if( u_level == 3) {
                        button_mypage.setImageResource(R.drawable.level_3_owl_25x25)
                    }
                    else if(u_level == 4){
                        button_mypage.setImageResource(R.drawable.level_4_monkey_25x25)
                    }
                    else if( u_level == 5) {
                        button_mypage.setImageResource(R.drawable.level_5_bear_25x25)
                    }
                    else if(u_level == 6){
                        button_mypage.setImageResource(R.drawable.level_6_eagle_25x25)

                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkPermission() {

        //카메라 권한의 승인 상태 가져오기
        val cameraPermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)

        if(cameraPermission == PackageManager.PERMISSION_GRANTED){
            //상태가 승인일 경우에는 코드 진행
            startProcess()
        }else{
            //승인되지 않았다면 권한 요청 프로세스 진행
            requestPermission()
        }

    }


    // 바텀네비게이션 아이템 클릭 리스너 설정
    private val onBottomNavItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_home -> {
                    Log.d(TAG, "[매인액티비티 ---> 홈 프래그먼트] 클릭!")
                    mainFragment = MainFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_frame, mainFragment).commit()
                }

                R.id.menu_mountain -> {
                    Log.d(TAG, "[매인액티비티 ---> 마운틴 프래그먼트] 클릭!")
//                    val abc = Intent(this, MapActivity::class.java) // 인텐트를 생성
//                    startActivity(abc)  // 화면 전환하기
                    mountainRecommendFragment = MountainRecommendFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_frame, mountainRecommendFragment).commit()
                }


            }

            true
        }

    private fun openCamera() {
        //카메라 권한이 있는지 확인
        if(checkPermission(CAMERA_PERMISSION,FLAG_PERM_CAMERA)){
            //권한이 있으면 카메라를 실행시킵니다.
            val intent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,FLAG_REQ_CAMERA)
        }
    }



    //권한이 있는지 체크하는 메소드
    fun checkPermission(permissions:Array<String>,flag:Int):Boolean{
        val permissionList : MutableList<String> = mutableListOf()
        for(permission in permissions){
            val result = ContextCompat.checkSelfPermission(this, permission)
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission)
            }
        }
        if(permissionList.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), PERMISSIONS_REQUEST)
            return false
        }
        return true


    }

    //checkPermission() 에서 ActivityCompat.requestPermissions 을 호출한 다음 사용자가 권한 허용여부를 선택하면 해당 메소드로 값이 전달 됩니다.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            FLAG_PERM_STORAGE ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        //권한이 승인되지 않았다면 return 을 사용하여 메소드를 종료시켜 줍니다
                        Toast.makeText(this,"저장소 권한을 승인해야지만 앱을 사용할 수 있습니다..",Toast.LENGTH_SHORT).show()
                        finish()
                        return
                    }
                }

            }
            FLAG_PERM_CAMERA ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"카메라 권한을 승인해야지만 카메라를 사용할 수 있습니다.",Toast.LENGTH_SHORT).show()
                        return
                    }
                }

            }
        }
    }

    //startActivityForResult 을 사용한 다음 돌아오는 결과값을 해당 메소드로 호출합니다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_CAMERA ->{
                    if(data?.extras?.get("data") != null){
                        //카메라로 방금 촬영한 이미지를 미리 만들어 놓은 이미지뷰로 전달 합니다.
                        val bitmap = data.extras?.get("data") as Bitmap
                        iv_pre.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
    private fun startProcess() {
        Toast.makeText(this,"카메라를 싱행 합니다." ,Toast.LENGTH_SHORT).show()
    }
    private fun requestPermission() {
        //ActivityCompat.requestPermissions을 사용하면 사용자에게 권한을 요청하는 팝업을 보여줍니다.
        //사용자가 선택한 값은 onRequestPermissionsResult메서드를 통해서 전달되어 집니다.
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),98)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),99)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101)
    }



    // 로그인창으로 이동하기
    private fun movelogin() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("알림창")
            .setMessage("현재 로그인 상태가 아닙니다.\n 로그인창으로 이동하겠습니다.\uD83E\uDD70")
            .setPositiveButton("네", DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(this@MainActivity, "이동합니당~^_^", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            })
        // 다이얼로그를 띄워주기
        builder.show()
    }

















}
