package com.deutschpro.app.data.repository

import com.deutschpro.app.data.local.AchievementEntity
import com.deutschpro.app.data.local.UnlockedAchievementEntity
import com.deutschpro.app.data.local.dao.ProgressDao
import com.deutschpro.app.data.model.AchievementCategory

data class UserStatsSnapshot(
    val currentStreak: Int,
    val totalXp: Int,
    val wordsLearned: Int,
    val lessonsCompleted: Int,
    val perfectQuizzes: Int,
    val levelsCompleted: Int
)

class AchievementRepository(private val dao: ProgressDao) {

    fun observeAllAchievements() = dao.observeAllAchievements()
    fun observeUnlockedIds() = dao.observeUnlockedIds()

    /**
     * Call after any stat-changing event (lesson complete, streak update,
     * xp gained, quiz finished). The caller (ViewModel) already has the
     * latest achievement catalog and unlocked-id set from its own
     * StateFlow collection, so we accept them as plain parameters instead
     * of re-reading Flows here - keeps this function pure and testable.
     */
    suspend fun evaluateAndUnlock(
        allAchievements: List<AchievementEntity>,
        alreadyUnlockedIds: Set<String>,
        stats: UserStatsSnapshot
    ): List<AchievementEntity> {
        val newlyUnlocked = mutableListOf<AchievementEntity>()
        for (achievement in allAchievements) {
            if (achievement.id in alreadyUnlockedIds) continue
            val currentValue = when (achievement.category) {
                AchievementCategory.STREAK -> stats.currentStreak
                AchievementCategory.XP -> stats.totalXp
                AchievementCategory.VOCABULARY -> stats.wordsLearned
                AchievementCategory.LESSONS -> stats.lessonsCompleted
                AchievementCategory.QUIZ -> stats.perfectQuizzes
                AchievementCategory.LEVEL -> stats.levelsCompleted
            }
            if (currentValue >= achievement.requiredValue) {
                dao.unlockAchievement(UnlockedAchievementEntity(achievement.id, System.currentTimeMillis()))
                newlyUnlocked += achievement
            }
        }
        return newlyUnlocked
    }
}
