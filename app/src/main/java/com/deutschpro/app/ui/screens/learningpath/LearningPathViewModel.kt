package com.deutschpro.app.ui.screens.learningpath

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.repository.CourseRepository
import com.deutschpro.app.data.repository.UnitWithLessons
import com.deutschpro.app.util.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LearningPathUiState(
    val selectedLevel: LevelCode = LevelCode.A1,
    val unitsWithLessons: List<UnitWithLessons> = emptyList(),
    val totalXp: Int = 0,
    val todayXp: Int = 0,
    val dailyGoalXp: Int = 50,
    val currentStreak: Int = 0,
    val isLevelEmpty: Boolean = false
)

class LearningPathViewModel(
    private val courseRepository: CourseRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val selectedLevel = MutableStateFlow(LevelCode.A1)

    fun selectLevel(level: LevelCode) {
        selectedLevel.value = level
    }

    val uiState: StateFlow<LearningPathUiState> = combine(
        selectedLevel.flatMapLatest { level -> courseRepository.observeLearningPath(level) },
        selectedLevel,
        preferencesManager.totalXp,
        preferencesManager.todayXp,
        preferencesManager.dailyGoalXp,
        preferencesManager.currentStreak
    ) { flows ->
        @Suppress("UNCHECKED_CAST")
        val units = flows[0] as List<UnitWithLessons>
        val level = flows[1] as LevelCode
        val totalXp = flows[2] as Int
        val todayXp = flows[3] as Int
        val dailyGoal = flows[4] as Int
        val streak = flows[5] as Int
        val hasRealLessons = units.any { u -> u.lessons.any { it.lesson.xpReward > 0 } }
        LearningPathUiState(
            selectedLevel = level,
            unitsWithLessons = units,
            totalXp = totalXp,
            todayXp = todayXp,
            dailyGoalXp = dailyGoal,
            currentStreak = streak,
            isLevelEmpty = !hasRealLessons
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LearningPathUiState())

    companion object {
        fun factory() = com.deutschpro.app.util.ViewModelFactory { app: DeutschProApplication ->
            LearningPathViewModel(app.courseRepository, app.preferencesManager)
        }
    }
}
