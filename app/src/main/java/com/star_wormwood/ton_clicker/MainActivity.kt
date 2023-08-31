package com.star_wormwood.ton_clicker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.star_wormwood.ton_clicker.Screens.GamesScreenFragment
import com.star_wormwood.ton_clicker.Screens.MiningScreenFragment
import com.star_wormwood.ton_clicker.Screens.ShopScreenFragment
import com.star_wormwood.ton_clicker.Screens.TopPlayersScreenFragment
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
}


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
//        Managers.fragmentManager.goToFragment(NavigationScreenFragment())
    }
}