package com.example.a111

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.a111.Mountain.MountainRcmDetailActivity.Companion.hk_exp
import com.example.a111.Mountain.MountainRcmDetailActivity.Companion.hk_level
import com.example.a111.Mountain.MountainRcmDetailActivity.Companion.hk_name
import com.example.a111.Mountain.MountainRcmDetailActivity.Companion.m_idcheck
import com.example.a111.Mountain.MountainRcmDetailActivity.Companion.mttype
import com.example.a111.databinding.ActivityMapBinding
import com.example.a111.retrofit.IRetrofit
import com.example.a111.retrofit.RetrofitManager
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_map.*
import net.daum.mf.map.api.*
import net.daum.mf.map.api.MapView.MapType.Hybrid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer


class MapActivity : AppCompatActivity() {

    lateinit var mainActivity: MainActivity
    private lateinit var homeFragment: HomeFragment

    private lateinit var binding : ActivityMapBinding
    private val ACCESS_FINE_LOCATION = 1000     // Request Code

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    //위도경도 값
    var constantLatitue : Double = 1.0
    var constantlongitude : Double = 1.0


    //스탑워치 값
    private var time = 0
    private var timerTask: Timer? = null      // null을 허용
    private var isRunning = false
    private var lap = 1
    var recodStartlocation : String = "a"
    var recodEndlocation : String = "b"
    var recodHiking : String = "c"

    companion object;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        //  스위치를 클릭했을때
        gpsSwitch.setOnCheckedChangeListener{CompoundButton, onSwitch ->
            //  스위치가 켜지면
            if (onSwitch){
                permissionCheck()
                startLocationUpdates()
            }
            //  스위치가 꺼지면
            else{
                stopTracking()
            }
        }



        //gps위도경도
        mLocationRequest =  LocationRequest.create().apply {
            interval = 2000 // 업데이트 간격 단위(밀리초)
            fastestInterval = 1000 // 가장 빠른 업데이트 간격 단위(밀리초)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 정확성
            maxWaitTime= 2000 // 위치 갱신 요청 최대 대기 시간 (밀리초)
        }

        markerCollection()
        smarkergrab.setOnClickListener {
            markerStartChecking()

        }
        fmarkergrab.setOnClickListener {
            markerFinishChecking()

        }

        stopplz.setOnClickListener {
            giveupDialog()
        }




        val circle2 = MapCircle(
            MapPoint.mapPointWithGeoCoord(37.4756191, 126.8821647),  // center
            40,  // radius
            Color.argb(128, 255, 0, 0),  // strokeColor
            Color.argb(128, 255, 255, 0) // fillColor
        )
        circle2.tag = 5678
        binding.mapView.addCircle(circle2)

        val circle3 = MapCircle(
            MapPoint.mapPointWithGeoCoord(37.4763286, 126.8832826),  // center
            40,  // radius
            Color.argb(128, 255, 0, 0),  // strokeColor
            Color.argb(128, 255, 255, 0) // fillColor
        )
        circle2.tag = 5678
        binding.mapView.addCircle(circle2)

        // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.

        // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
        val mapPointBoundsArray = arrayOf(circle2.bound, circle3.bound)
        val mapPointBounds = MapPointBounds(mapPointBoundsArray)
        val padding = 50 // px

        binding.mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))

    }
    private fun markerCollection() {
        // 현 위치에 마커 찍기
        val dodangmarker = MapPOIItem()
        dodangmarker.itemName = "도당산 출발지점"
        dodangmarker.mapPoint = MapPoint.mapPointWithGeoCoord(37.5157118, 126.7862168)
        dodangmarker.markerType = MapPOIItem.MarkerType.BluePin
        dodangmarker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        binding.mapView.addPOIItem(dodangmarker)

        val namsungplaza = MapPOIItem()
        namsungplaza.itemName = "남성플라자 출발지점"
        namsungplaza.mapPoint = MapPoint.mapPointWithGeoCoord(37.4756191, 126.8821647)
        namsungplaza.markerType = MapPOIItem.MarkerType.BluePin
        namsungplaza.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        binding.mapView.addPOIItem(namsungplaza)

        val namsungplazaEnd = MapPOIItem()
        namsungplazaEnd.itemName = "남성플라자 도착지점"
        namsungplazaEnd.mapPoint = MapPoint.mapPointWithGeoCoord(37.4750348, 126.8812551)
        namsungplazaEnd.markerType = MapPOIItem.MarkerType.BluePin
        namsungplazaEnd.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        binding.mapView.addPOIItem(namsungplazaEnd)
    }

    private fun markerStartChecking() {
        //도당산 출발지점 마커
        if (37.5152000 < constantLatitue && constantLatitue < 37.5159000
            && 126.7858168 < constantlongitude && constantlongitude < 126.7866168
        ) {
            makerStartDialog()
            recodStartlocation = "도당산 출발"
        }
        else if (37.475319 < constantLatitue && constantLatitue < 37.4759191
            && 126.8818647 < constantlongitude && constantlongitude < 126.8823647
        ) {
            makerStartDialog()
            recodStartlocation = "남성플라자 출발"
        }
    }

    private fun markerFinishChecking() {

        //도당산 종료지점 마커
        if (37.5152000 < constantLatitue && constantLatitue < 37.5159000
            && 126.7858168 < constantlongitude && constantlongitude < 126.7866168
        ) {
            recodEndlocation = "도당산 도착완료"
            recordLapTime()
            makerEndDialog()

        }else if (37.4760092 < constantLatitue && constantLatitue < 37.4766092
            && 126.8830013< constantlongitude && constantlongitude < 126.8835513
        ) {
            recodEndlocation = "가산 디지털 1로 도착완료"
            makerEndDialog()
        }
    }

    //
    private fun start() {

        timerTask = timer(period = 10) {       // 타이머 인터벌 10 ms
            time++
            val milli = time % 100
            val sec = (time / 100) % 60
            val min = (time / 6000) % 60
            val hour = (time / 144000) % 24

            runOnUiThread {                   // UI 조작이 가능한 블럭
                milliTextView.text = "$milli"
                secTextView.text = "$sec"
                minTextView.text = "$min"
                hourTextView.text = "$hour"
            }
        }
    }


    private fun pause() {

        timerTask?.cancel()                  // 실행중인 타이머 취소
    }

    private fun recordLapTime() {

        val lapTime = this.time
        recodHiking = "${(lapTime / 144000) % 24}시 ${(lapTime / 6000) % 60}분 ${(lapTime / 100) % 60}: ${lapTime % 100}"

//            val mil = (lapTime % 100) as String
//            val sec = ((lapTime  / 100) % 60) as String
//            val min = ((lapTime / 6000) % 60) as String
//            val hour = ((lapTime / 144000) % 24) as String
//            recodHiking = String.format("%02d:%02d:%02d:%02d", hour, min, sec,mil)
//            recodHiking = "${zozo}"
    }

    private fun reset() {
        timerTask?.cancel()       // 실행중인 타이머 취소

        // 모든 변수 초기화
        time = 0
        isRunning = false
        milliTextView.text = "0"
        secTextView.text = "0"
        minTextView.text = "0"
        hourTextView.text = "0"

        // 모든 랩타임 기록 삭제
        lap = 1
    }


    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // 권한 거절 (다시 한 번 물어봄)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {
            // 권한이 있는 상태
            startTracking()
        }
    }

    // 권한 요청 후 행동
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
                startTracking()

            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                permissionCheck()
            }
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    }

    // 위치추적 시작
    private fun startTracking() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
        binding.mapView.mapType = Hybrid
        mapView.setZoomLevel(1, true)
        //mapView.setMapRotationAngle(360f,true)

    }





    private fun makerStartDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("알림창")
            .setMessage("등산할 준비가 되셨나요?")
            .setPositiveButton("네", DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(this@MapActivity, "시작합니당~", Toast.LENGTH_SHORT).show()
                start()
            })
            .setNegativeButton("아니요",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this@MapActivity, "아이 참~ 너무 아쉬워요ㅠ.ㅠ!", Toast.LENGTH_SHORT)
                        .show()
                })
        // 다이얼로그를 띄워주기
        builder.show()
    }


    private fun makerEndDialog() {
        recordLapTime()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("알림창")
            .setMessage(
                "축하드립니다. 등정에 성공하셨어요!\n" +   "${recodHiking}"
                //"${recodStartlocation} -> ${recodEndlocation}\n" +

            )


            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->


                    //레트로핏


                    val u_id = GlobalApplication.prefs.getString("u_id", "")
                    Log.d("로그", "onCreate:레트로핏 $u_id")
                    val start_location = "${recodStartlocation}"
                    Log.d("로그", "onCreate:레트로핏 $start_location")
                    val finish_location = "${recodEndlocation}"
                    Log.d("로그", "onCreate:레트로핏 $finish_location")
                    val record_time = "${recodHiking}"
                    Log.d("로그", "onCreate:레트로핏 $recodHiking")
                    val hiking_name = "${hk_name}"
                    Log.d("로그", "onCreate:레트로핏 $hk_name")
                    val hiking_level ="${hk_level}"
                    Log.d("로그", "onCreate:레트로핏 $hk_level")
                    val hiking_exp = "${hk_exp}"
                    Log.d("로그", "onCreate:레트로핏 $hk_exp")
                    mttype //개인, 동호회, 소모임 선택유형값 있음
                    m_idcheck //m_id값 있음


                    RetrofitManager.instance.iRetrofit.recordAdd(u_id, start_location, finish_location,
                        record_time,hiking_name,hiking_level,hiking_exp,mttype,m_idcheck)
                        ?.enqueue(object : Callback<String> {
                            override fun onResponse( call: Call<String>, response: Response<String> ) {
                                if (response.isSuccessful) {
                                    // 정상적으로 통신이 성고된 경우
                                    var result: String? = response.body()
                                    Log.d( ContentValues.TAG, "onResponse 성공: " + result?.toString()
                                    )
                                } else {
                                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                    Log.d( ContentValues.TAG, "onResponse 실패" + response.body() + response
                                    )
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                Log.d(   ContentValues.TAG, "onFailure 에러: " + t.message.toString()
                                )
                            }
                        })

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                })

        // 다이얼로그를 띄워주기
        builder.show()

    }

    private fun giveupDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("알림창")
            .setMessage("포기하시겠어요?\n")

            .setPositiveButton("네",
                DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    reset()
                })
            .setNegativeButton("아니요!",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(
                        this@MapActivity,
                        "역시~!! 저랑 함께 끝까지 올라가요~^_^@!!",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        // 다이얼로그를 띄워주기
        builder.show()
    }


    // 위치추적 중지
    private fun stopTracking() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff
    }


    //////////////////////////
    /////gps위도경도 받기///////
    /////////////////////////
    protected fun startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates()")

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "startLocationUpdates() 두 위치 권한중 하나라도 없는 경우 ")
            return
        }
        Log.d(TAG, "startLocationUpdates() 위치 권한이 하나라도 존재하는 경우")
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청합니다.
        Looper.myLooper()?.let {
            mFusedLocationProviderClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d(TAG, "onLocationResult()")
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        Log.d(TAG, "onLocationChanged()")
        mLastLocation = location
        val date: Date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("hh:mm:ss a")


        constantLatitue = mLastLocation.latitude
        constantlongitude = mLastLocation.longitude
    //    txtLat.text = "LATITUDE : " + mLastLocation.latitude // 갱신 된 위도
    //    txtLong.text = "LONGITUDE : " + mLastLocation.longitude // 갱신 된 경도
    }

    // 위치 업데이터를 제거 하는 메서드
    private fun stoplocationUpdates() {
        Log.d(TAG, "stoplocationUpdates()")
        // 지정된 위치 결과 리스너에 대한 모든 위치 업데이트를 제거
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }



}
