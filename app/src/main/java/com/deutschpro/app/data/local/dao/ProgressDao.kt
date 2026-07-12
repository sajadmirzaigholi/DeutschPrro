package com.deutschpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deutschpro.app.data.local.AchievementEntity
import com.deutschpro.app.data.local.LessonProgressEntity
import com.deutschpro.app.data.local.UnlockedAchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLessonProgress(progress: LessonProgressEntity)

    @Query("SELECT * FROM lesson_progress")
    fun observeAllProgress(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getProgress(lessonId: String): LessonProgressEntity?

    @Query("SELECT COUNT(*) FROM lesson_progress WHERE state = 'COMPLETED'")
    fun observeCompletedLessonCount(): Flow<Int>

    // --- Achievements ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<AchievementEntity>)

    @Query("SELECT COUNT(*) FROM achievements")
    suspend fun achievementCount(): Int

    @Query("SELECT * FROM achievements")
    fun observeAllAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockAchievement(unlocked: UnlockedAchievementEntity)

    @Query("SELECT achievementId FROM unlocked_achievements")
    fun observeUnlockedIds(): Flow<List<String>>
}
