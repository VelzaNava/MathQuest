package com.mathquest.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mathquest.app.data.CHAPTERS
import com.mathquest.app.data.CORRECT_MESSAGES
import com.mathquest.app.data.WRONG_MESSAGES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameState(
    val playerName: String = "",
    val currentChapter: Int = 0,
    val currentProblem: Int = 0,
    val hearts: Int = 5,
    val hintsLeft: Int = 3,
    val streak: Int = 0,
    val cutStep: Int = 0,
    val totalRight: Int = 0,
    val showingHint: Boolean = false,
    val completedChapters: Set<Int> = emptySet(),
    val feedbackMessage: String? = null,
    val isCorrectFeedback: Boolean = false,
    val showStreakBanner: Boolean = false,
    val streakCount: Int = 0,
    val hintText: String? = null,
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
                    currentChapter = saved.currentLevel - 1,
                    completedChapters = saved.completedLevels,
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
        _state.update { it.copy(playerName = name) }
        saveManager.saveAdventureStart(name)
        _state.update { it.copy(hasSavedProgress = true) }
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
        _state.update {
            GameState(
                playerName = "",
                hasSavedProgress = false,
                musicVolume = it.musicVolume,
                sfxVolume = it.sfxVolume
            )
        }
        saveManager.clear()
        saveManager.saveAudio(_state.value.musicVolume, _state.value.sfxVolume)
    }

    fun nextCutsceneStep(): Boolean {
        val current = _state.value.cutStep
        return if (current < 4) {
            _state.update { it.copy(cutStep = current + 1) }
            false
        } else {
            _state.update { it.copy(cutStep = 0) }
            true
        }
    }

    fun selectChapter(chapterIndex: Int) {
        _state.update { it.copy(currentChapter = chapterIndex) }
    }

    fun startChallenge() {
        _state.update {
            it.copy(
                hearts = 5,
                hintsLeft = 3,
                streak = 0,
                showingHint = false,
                currentProblem = 0,
                feedbackMessage = null,
                hintText = null,
                showStreakBanner = false
            )
        }
    }

    fun submitAnswer(userInput: String): AnswerResult {
        val s = _state.value
        val chapter = CHAPTERS[s.currentChapter]
        val correct = chapter.problems[s.currentProblem].answer
            .trim().lowercase().replace("\\s".toRegex(), "")
        val userAnswer = userInput.trim().lowercase().replace("\\s".toRegex(), "")

        if (userAnswer.isEmpty()) return AnswerResult.Empty

        return if (userAnswer == correct) {
            val newStreak = s.streak + 1
            val newTotalRight = s.totalRight + 1
            var newHearts = s.hearts
            val streakRestored = newStreak % 3 == 0 && s.hearts < 5

            if (streakRestored) newHearts = minOf(5, s.hearts + 1)

            val msg = CORRECT_MESSAGES.random()
            _state.update {
                it.copy(
                    streak = newStreak,
                    totalRight = newTotalRight,
                    hearts = newHearts,
                    feedbackMessage = msg,
                    isCorrectFeedback = true,
                    showStreakBanner = streakRestored,
                    streakCount = newStreak
                )
            }

            val nextProblem = s.currentProblem + 1
            if (nextProblem >= chapter.problems.size) {
                AnswerResult.ChapterComplete
            } else {
                AnswerResult.Correct(streakRestored)
            }
        } else {
            val newHearts = s.hearts - 1
            val newStreak = 0
            val msg = WRONG_MESSAGES.random()
            _state.update {
                it.copy(
                    hearts = newHearts,
                    streak = newStreak,
                    feedbackMessage = msg,
                    isCorrectFeedback = false,
                    showStreakBanner = false
                )
            }
            if (newHearts <= 0) AnswerResult.GameOver
            else AnswerResult.Wrong(newHearts)
        }
    }

    fun advanceProblem() {
        _state.update {
            it.copy(
                currentProblem = it.currentProblem + 1,
                feedbackMessage = null,
                hintText = null,
                showingHint = false,
                showStreakBanner = false
            )
        }
    }

    fun clearFeedback() {
        _state.update { it.copy(feedbackMessage = null, showStreakBanner = false) }
    }

    fun useHint() {
        val s = _state.value
        if (s.hintsLeft <= 0 || s.showingHint) return
        val hint = CHAPTERS[s.currentChapter].problems[s.currentProblem].hint
        _state.update {
            it.copy(
                hintsLeft = it.hintsLeft - 1,
                showingHint = true,
                hintText = hint
            )
        }
    }

    fun markChapterComplete() {
        val s = _state.value
        val newCompleted = s.completedChapters + s.currentChapter
        _state.update { it.copy(completedChapters = newCompleted) }
        saveManager.updateProgress(s.currentChapter + 1, newCompleted)
    }

    fun isLastChapter(): Boolean = _state.value.currentChapter >= CHAPTERS.size - 1

    fun nextChapter() {
        _state.update {
            it.copy(
                currentChapter = it.currentChapter + 1,
                currentProblem = 0,
                hearts = 5,
                hintsLeft = 3,
                streak = 0,
                showingHint = false,
                feedbackMessage = null,
                hintText = null
            )
        }
        saveManager.updateProgress(_state.value.currentChapter + 1, _state.value.completedChapters)
    }
}

sealed class AnswerResult {
    object Empty : AnswerResult()
    data class Correct(val streakRestored: Boolean) : AnswerResult()
    object ChapterComplete : AnswerResult()
    data class Wrong(val heartsLeft: Int) : AnswerResult()
    object GameOver : AnswerResult()
}
