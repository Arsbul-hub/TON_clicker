package com.star_wormwood.ton_clicker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.star_wormwood.ton_clicker.Screens.GamesScreenFragment
import com.star_wormwood.ton_clicker.Screens.MiningScreenFragment
import com.star_wormwood.ton_clicker.Screens.ShopScreenFragment
import com.star_wormwood.ton_clicker.Screens.TopPlayersScreenFragment
import com.star_wormwood.ton_clicker.Services.GameService
import com.star_wormwood.ton_clicker.common.Managers.FragmentNavigationManager
import com.star_wormwood.ton_clicker.common.Managers.UserManager
import com.star_wormwood.ton_clicker.databinding.ActivityMainBinding


object Managers {
    lateinit var fragmentManager: FragmentNavigationManager
    lateinit var userManager: UserManager

}
object Fragments {
    val miningScreen = MiningScreenFragment()
    val shopScreen = ShopScreenFragment()
    val topPlayersScreen = TopPlayersScreenFragment()
    val gamesScreen = GamesScreenFragment()
    lateinit var actv: Activity
}


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }
    @SuppressLint("RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Fragments.actv = this@MainActivity
        Managers.userManager = UserManager(getSharedPreferences("User", Context.MODE_PRIVATE))
        val mapFragments: MutableMap<Int, Fragment> = mutableMapOf(
            R.id.mining to Fragments.miningScreen,
            R.id.shop to Fragments.shopScreen,
            R.id.top to Fragments.topPlayersScreen,
            R.id.games to Fragments.gamesScreen
        )

        Managers.fragmentManager = FragmentNavigationManager(binding.fragmentView.id, supportFragmentManager)
        Managers.fragmentManager.goToFragment(Fragments.miningScreen)
        binding.navigationBar.selectedItemId = R.id.mining
        binding.navigationBar.setOnItemSelectedListener {
//            if (it.itemId == R.id.m) {
            Managers.fragmentManager.goToFragment(mapFragments[it.itemId]!!, animation_enter = R.anim.fade_in, animation_exit = R.anim.fade_out)
//            } else {
//                Managers.fragmentManager.goToFragment(mapFragments[it.itemId]!!, animation_enter = R.anim.slide_lift, animation_exit = R.anim.fade_out)
//            }


            return@setOnItemSelectedListener true
        }
//        if (ActivityCompat.checkSelfPermission(
//                applicationContext,
//                Manifest.permission.FOREGROUND_SERVICE
//            ) != PackageManager.PERMISSION_GRANTED
//        )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.FOREGROUND_SERVICE, Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                    111
                )
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, GameService::class.java))
        }


//        Managers.fragmentManager.goToFragment(NavigationScreenFragment())
    }


}