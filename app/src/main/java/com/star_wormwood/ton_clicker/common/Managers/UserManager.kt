package com.star_wormwood.ton_clicker.common.Managers

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

object GameData {
    var ton: Float = 0f
    var energy: Int = 100

}
@SuppressLint("SimpleDateFormat")
val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
class UserManager(val prefs: SharedPreferences) {
    private val editor: SharedPreferences.Editor = prefs.edit()
    var onUpdateListener: () -> Unit = {}
    private var last_mine_time = sdf.format(Date())
    init {

        loadGameData()
        onUpdateListener()
    }


    fun mine() {

        if (GameData.energy > 0) {
            GameData.ton += 0.000_001f
            GameData.energy -= 50
            last_mine_time = sdf.format(Date())
        }
        onUpdateListener()
        saveGameData()
    }

    fun energise() {
        if (GameData.energy < 100) {
            GameData.energy += 1
        }
//        try {
//            onUpdateListener()
//        } catch (_: Exception) {
//            Log.e("UserManagementError", "Failed to execute on update listener!")
//        }
        onUpdateListener()
        saveGameData()
    }

    private fun saveGameData() {

        editor.putFloat("ton", GameData.ton)
        editor.putInt("energy", GameData.energy)
        editor.apply()

    }

    private fun loadGameData() {
        GameData.ton = prefs.getFloat("ton", 0f)
        GameData.energy = prefs.getInt("energy", 100)
    }

    fun getGameData(): GameData {
        return GameData
    }


}