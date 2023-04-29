package com.example.a111

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent

import android.content.Intent

import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.example.a111.databinding.ActivityTopEmergencyBinding
import com.example.a111.utils.Constants.TAG

class TopEmergency : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTopEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.teback)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 2) 응급 요청
        binding.helpButton.setOnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder:NotificationCompat.Builder
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val channelId ="one-channel"
                val channelName = "My Channel One"
                val channel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT)
                    .apply {
                        description="My Channel One Description"
                        setShowBadge(true)
                        val uri: Uri =RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        val audioAttributes=AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .build()
                        setSound(uri,audioAttributes)
                        enableVibration(true)
                    }
                manager.createNotificationChannel(channel)

                builder=NotificationCompat.Builder(this,channelId)

        }else{
            builder = NotificationCompat.Builder(this)
            }
            builder.run {
                val u_id = GlobalApplication.prefs.getString("u_id","")
                setSmallIcon(R.drawable.ic_baseline_mail_24)
                setWhen(System.currentTimeMillis())
                if(u_id.isNullOrBlank()){

                   val kao= GlobalApplication.prefs.getString("kakaolog","")
                    Log.d(TAG, "onCreate: ${kao}")

                    setContentTitle("${kao}님의 도움요청")
                }else {
                    Log.d(TAG, "onCreate: ${u_id}")
                    setContentTitle("${u_id}님의 도움요청")
                }
                setContentText("....님의 위치")
            }
            val KEY_TEXT_REPLY = "key_text_reply"
            var replyLabel:String ="답장"
            var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                setLabel(replyLabel)
                build()
            }
            val replyIntent = Intent(this , ReplyReceiver::class.java)
            val replyPendingIntent = PendingIntent.getBroadcast(
                this,30,replyIntent,PendingIntent.FLAG_MUTABLE
            )
            builder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.ic_baseline_mail_24,
                    "답장",
                    replyPendingIntent
                ).addRemoteInput(remoteInput).build()
            )
            manager.notify(11,builder.build())
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