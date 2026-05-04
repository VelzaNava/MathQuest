package com.mathquest.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameState(
    val playerName: String = "",
    val hasSavedProgress: Boolean = false,
    val musicVolume: Float = 0.7f,
    val sfxVolume: Float = 0.7f
)

class GameViewModel(app: Application) : AndroidViewModel(app) {

    private val saveManager = SaveManager(app)

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    init {
        val saved = saveManager.load()
        if (saved != null) {
            _state.update {
                it.copy(
                    playerName = saved.playerName,
                    hasSavedProgress = true,
                    musicVolume = saved.musicVolume,
                    sfxVolume = saved.sfxVolume
                )
            }
        } else {
            val (m, s) = saveManager.loadAudio()
            _state.update { it.copy(musicVolume = m, sfxVolume = s) }
        }
    }

    fun setPlayerName(name: String) {
        _state.update { it.copy(playerName = name, hasSavedProgress = true) }
        saveManager.saveAdventureStart(name)
    }

    fun setMusicVolume(v: Float) {
        _state.update { it.copy(musicVolume = v) }
        saveManager.saveAudio(v, _state.value.sfxVolume)
    }

    fun setSfxVolume(v: Float) {
        _state.update { it.copy(sfxVolume = v) }
        saveManager.saveAudio(_state.value.musicVolume, v)
    }

    fun resetForNewGame() {
        val currentMusic = _state.value.musicVolume
        val currentSfx = _state.value.sfxVolume
        _state.update {
            GameState(musicVolume = currentMusic, sfxVolume = currentSfx)
        }
        saveManager.clear()
        saveManager.saveAudio(currentMusic, currentSfx)
    }
}
