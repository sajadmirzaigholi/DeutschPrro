package com.deutschpro.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deutschpro.app.data.model.AchievementCategory
import com.deutschpro.app.data.model.LessonState

/** Tracks per-lesson completion state, best quiz score, and stars earned. */
@Entity(tableName = "lesson_progress", primaryKeys = ["lessonId"])
data class LessonProgressEntity(
    val lessonId: String,
    val state: LessonState = LessonState.LOCKED,
    val bestScorePercent: Int = 0,
    val starsEarned: Int = 0,           // 0..3
    val timesCompleted: Int = 0,
    val lastCompletedEpochMillis: Long = 0L
)

/** A single wrong answer, kept so the "Mistake Review" screen can quiz it again. */
@Entity(tableName = "mistakes")
data class MistakeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionId: String,
    val lessonId: String,
    val chosenOptionIndex: Int,
    val timestampEpochMillis: Long,
    val resolved: Boolean = false
)

/** Static catalog of achievements/badges available in the app. */
@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val category: AchievementCategory,
    val titleFa: String,
    val descriptionFa: String,
    val iconName: String,
    val requiredValue: Int   // e.g. streak days, xp amount, words learned
)

/** Which achievements the user has unlocked, and when. */
@Entity(tableName = "unlocked_achievements", primaryKeys = ["achievementId"])
data class UnlockedAchievementEntity(
    val achievementId: String,
    val unlockedAtEpochMillis: Long
)
