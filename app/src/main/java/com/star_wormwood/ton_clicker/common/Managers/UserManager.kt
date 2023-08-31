package com.star_wormwood.ton_clicker.common.Managers

import android.content.SharedPreferences

object GameData {
    var ton: Float = 0f
}
class UserManager(val prefs: SharedPreferences) {
    private val editor: SharedPreferences.Editor = prefs.edit()
    init {
        loadGameData()
    }


    fun mine() {
        GameData.ton += 0.000_001f
        saveGameData()
    }

    private fun saveGameData() {
        Thread {
            editor.putFloat("ton", GameData.ton)
            editor.apply()
        }.start()
    }
    private fun loadGameData() {
        GameData.ton = prefs.getFloat("ton", 0f)
    }
}