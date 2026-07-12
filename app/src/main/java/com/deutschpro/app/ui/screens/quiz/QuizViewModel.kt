package com.deutschpro.app.ui.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.local.QuizQuestionEntity
import com.deutschpro.app.data.repository.AchievementRepository
import com.deutschpro.app.data.repository.CourseRepository
import com.deutschpro.app.data.repository.QuizRepository
import com.deutschpro.app.data.repository.UserStatsSnapshot
import com.deutschpro.app.util.PreferencesManager
import com.deutschpro.app.util.ViewModelFactory
import com.deutschpro.app.util.XpEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class QuizUiState(
    val questions: List<QuizQuestionEntity> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val hasAnswered: Boolean = false,
    val correctCount: Int = 0,
    val isFinished: Boolean = false,
    val isLoading: Boolean = true
) {
    val currentQuestion: QuizQuestionEntity? get() = questions.getOrNull(currentIndex)
    val scorePercent: Int get() = if (questions.isEmpty()) 0 else (correctCount * 100) / questions.size
    val stars: Int get() = when {
        scorePercent >= 90 -> 3
        scorePercent >= 70 -> 2
        scorePercent >= 50 -> 1
        else -> 0
    }
}

class QuizViewModel(
    private val lessonId: String,
    private val quizRepository: QuizRepository,
    private val courseRepository: CourseRepository,
    private val preferencesManager: PreferencesManager,
    private val achievementRepository: AchievementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val questions = quizRepository.getQuestionsForLesson(lessonId)
            _uiState.value = _uiState.value.copy(questions = questions, isLoading = false)
        }
    }

    fun selectOption(index: Int) {
        if (_uiState.value.hasAnswered) return
        _uiState.value = _uiState.value.copy(selectedOptionIndex = index)
    }

    fun confirmAnswer() {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        val selected = state.selectedOptionIndex ?: return
        viewModelScope.launch {
            val correct = quizRepository.submitAnswer(question, selected)
            _uiState.value = state.copy(
                hasAnswered = true,
                correctCount = state.correctCount + if (correct) 1 else 0
            )
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentIndex + 1
        if (nextIndex >= state.questions.size) {
            finishQuiz()
        } else {
            _uiState.value = state.copy(
                currentIndex = nextIndex,
                selectedOptionIndex = null,
                hasAnswered = false
            )
        }
    }

    private fun finishQuiz() {
        val state = _uiState.value
        _uiState.value = state.copy(isFinished = true)
        viewModelScope.launch {
            courseRepository.markLessonCompleted(lessonId, state.scorePercent, state.stars)

            var xp = XpEngine.XP_LESSON_COMPLETE
            if (state.scorePercent == 100) xp += XpEngine.XP_PERFECT_QUIZ_BONUS
            preferencesManager.addXp(xp)

            val allAchievements = achievementRepository.observeAllAchievements().first()
            val unlockedIds = achievementRepository.observeUnlockedIds().first().toSet()
            achievementRepository.evaluateAndUnlock(
                allAchievements,
                unlockedIds,
                UserStatsSnapshot(
                    currentStreak = preferencesManager.currentStreak.first(),
                    totalXp = preferencesManager.totalXp.first(),
                    wordsLearned = 0,
                    lessonsCompleted = courseRepository.observeCompletedLessonCount().first(),
                    perfectQuizzes = if (state.scorePercent == 100) 1 else 0,
                    levelsCompleted = 0
                )
            )
        }
    }

    companion object {
        fun factory(lessonId: String) = ViewModelFactory { app: DeutschProApplication ->
            QuizViewModel(
                lessonId,
                app.quizRepository,
                app.courseRepository,
                app.preferencesManager,
                app.achievementRepository
            )
        }
    }
}
