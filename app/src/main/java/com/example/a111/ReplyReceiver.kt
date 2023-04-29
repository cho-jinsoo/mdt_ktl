package com.example.a111

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a111.utils.Constants.TAG

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val replyTxt=RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")
        Log.d(TAG, "onReceive: ${replyTxt}")
        val manager = context.getSystemService(
            AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(11)
    }
}