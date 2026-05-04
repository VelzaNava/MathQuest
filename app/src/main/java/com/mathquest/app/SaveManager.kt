package com.mathquest.app

import android.content.Context
import android.content.SharedPreferences

data class SavedProgress(
    val playerName: String,
    val currentLevel: Int,
    val completedLevels: Set<Int>,
    val musicVolume: Float = 0.7f,
    val sfxVolume: Float = 0.7f
)

class SaveManager(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("mathquest_save", Context.MODE_PRIVATE)

    fun hasSavedProgress(): Boolean =
        prefs.getBoolean(KEY_HAS_STARTED, false)

    fun saveAdventureStart(playerName: String) {
        prefs.edit()
            .putBoolean(KEY_HAS_STARTED, true)
            .putString(KEY_PLAYER_NAME, playerName)
            .putInt(KEY_CURRENT_LEVEL, 1)
            .apply()
    }

    fun updateProgress(currentLevel: Int, completedLevels: Set<Int>) {
        prefs.edit()
            .putInt(KEY_CURRENT_LEVEL, currentLevel)
            .putStringSet(KEY_COMPLETED, completedLevels.map { it.toString() }.toSet())
            .apply()
    }

    fun load(): SavedProgress? {
        if (!hasSavedProgress()) return null
        return SavedProgress(
            playerName = prefs.getString(KEY_PLAYER_NAME, "") ?: "",
            currentLevel = prefs.getInt(KEY_CURRENT_LEVEL, 1),
            completedLevels = prefs.getStringSet(KEY_COMPLETED, emptySet())
                ?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet(),
            musicVolume = prefs.getFloat(KEY_MUSIC, 0.7f),
            sfxVolume = prefs.getFloat(KEY_SFX, 0.7f)
        )
    }

    fun saveAudio(music: Float, sfx: Float) {
        prefs.edit()
            .putFloat(KEY_MUSIC, music)
            .putFloat(KEY_SFX, sfx)
            .apply()
    }

    fun loadAudio(): Pair<Float, Float> =
        prefs.getFloat(KEY_MUSIC, 0.7f) to prefs.getFloat(KEY_SFX, 0.7f)

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_HAS_STARTED = "has_started"
        private const val KEY_PLAYER_NAME = "player_name"
        private const val KEY_CURRENT_LEVEL = "current_level"
        private const val KEY_COMPLETED = "completed_levels"
        private const val KEY_MUSIC = "music_vol"
        private const val KEY_SFX = "sfx_vol"
    }
}
