package com.star_wormwood.ton_clicker.Services

import android.annotation.SuppressLint
import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaMetadata
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.content.ContextCompat
import com.star_wormwood.ton_clicker.Fragments
import com.star_wormwood.ton_clicker.MainActivity
import com.star_wormwood.ton_clicker.Managers
import com.star_wormwood.ton_clicker.R
import com.star_wormwood.ton_clicker.common.Managers.GameData
import java.time.LocalDate
import java.util.Date

//class GameService() : Service() {
//    var run = false
//    override fun onBind(p0: Intent?): IBinder? {
//        throw UnsupportedOperationException("Not yet implemented")
//    }
//
//
//    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//
//        Thread {
//            startLoop()
//        }.start()
//        return START_STICKY
//
//
//    }
//
////    @RequiresApi(Build.VERSION_CODES.O)
//    private fun startLoop() {
//        run = true
//
//        while (run) {
////            er
//
////                Log.e("Energy", "1")
////
////            try {
//               Managers.userManager.energise()
////            } catch (_:Exception) {
//                Log.e("Energy", Managers.userManager.getGameData().energy.toString())
////            }
////            val today = LocalDate.now()
//
//                Thread.sleep(1000)
//
//        }
//    }
//
//    private fun stopLoop() {
//        run = false
//    }
//}


class GameService : Service() {

    var run = false
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            startLoop()
        }.start()
        showNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("LaunchActivityFromNotification")
    private fun showNotification() {
        val collabsedViews: RemoteViews = RemoteViews(packageName, R.layout.notification_collabsed)

        val bigViews: RemoteViews = RemoteViews(packageName, R.layout.notification_big)
        val toActivity = Intent(this, MainActivity::class.java)
        val toActivityPendingIntent = PendingIntent.getActivity(this, 0, toActivity,
            PendingIntent.FLAG_IMMUTABLE
        )


        var notification = NotificationCompat.Builder(this, CHANNEL_ID)
            //.setContentText("$artist - $name")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setSmallIcon(R.drawable.baseline_currency_bitcoin_24)
            .setContentTitle("Hello")
            .setContentText(Managers.userManager.getGameData().energy.toString())
            .setCustomContentView(collabsedViews)
            .setCustomBigContentView(bigViews)
            .setContentIntent(toActivityPendingIntent)
        notification = notification.addAction(R.drawable.baseline_electric_bolt_24, "Go", toActivityPendingIntent)
        startForeground(123, notification.build())

    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            serviceChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }

    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    @RequiresApi(Build.VERSION_CODES.P)
    private fun startLoop() {
        run = true
        while (run) {
            try {
                Managers.userManager.energise()
            } catch (_: Exception) {
            }
            Log.e("Energy", Managers.userManager.getGameData().energy.toString())
            Thread.sleep(1000)
            showNotification()
        }
    }

    private fun stopLoop() {
        run = false
    }
}