package com.deutschpro.app.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.local.AchievementEntity
import com.deutschpro.app.data.repository.AchievementRepository
import com.deutschpro.app.data.repository.CourseRepository
import com.deutschpro.app.util.PreferencesManager
import com.deutschpro.app.util.ViewModelFactory
import com.deutschpro.app.util.XpEngine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProfileUiState(
    val totalXp: Int = 0,
    val userLevel: Int = 1,
    val xpForNextLevel: Int = 50,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val dailyGoalXp: Int = 50,
    val lessonsCompleted: Int = 0,
    val themeMode: String = "SYSTEM",
    val achievements: List<AchievementEntity> = emptyList(),
    val unlockedAchievementIds: Set<String> = emptySet()
)

class ProfileViewModel(
    private val preferencesManager: PreferencesManager,
    private val achievementRepository: AchievementRepository,
    private val courseRepository: CourseRepository
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = combine(
        preferencesManager.totalXp,
        preferencesManager.currentStreak,
        preferencesManager.longestStreak,
        preferencesManager.dailyGoalXp,
        preferencesManager.themeMode,
        courseRepository.observeCompletedLessonCount(),
        achievementRepository.observeAllAchievements(),
        achievementRepository.observeUnlockedIds()
    ) { values ->
        val totalXp = values[0] as Int
        val userLevel = XpEngine.userLevelForXp(totalXp)
        ProfileUiState(
            totalXp = totalXp,
            userLevel = userLevel,
            xpForNextLevel = XpEngine.xpNeededForNextUserLevel(userLevel),
            currentStreak = values[1] as Int,
            longestStreak = values[2] as Int,
            dailyGoalXp = values[3] as Int,
            themeMode = values[4] as String,
            lessonsCompleted = values[5] as Int,
            @Suppress("UNCHECKED_CAST")
            achievements = values[6] as List<AchievementEntity>,
            @Suppress("UNCHECKED_CAST")
            unlockedAchievementIds = (values[7] as List<String>).toSet()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileUiState())

    fun setDailyGoal(xp: Int) {
        viewModelScope.launch { preferencesManager.setDailyGoal(xp) }
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch { preferencesManager.setThemeMode(mode) }
    }

    companion object {
        fun factory() = ViewModelFactory { app: DeutschProApplication ->
            ProfileViewModel(app.preferencesManager, app.achievementRepository, app.courseRepository)
        }
    }
}
