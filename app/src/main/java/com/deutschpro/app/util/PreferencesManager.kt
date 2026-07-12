package com.deutschpro.app.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

private val Context.dataStore by preferencesDataStore(name = "deutschpro_prefs")

/**
 * Central place for all lightweight, single-value user state that does not
 * belong in a relational table: total XP, current streak, last study day,
 * daily goal, and theme preference. Backed by Jetpack DataStore so it
 * survives offline and across app restarts.
 */
class PreferencesManager(private val context: Context) {

    private object Keys {
        val TOTAL_XP = intPreferencesKey("total_xp")
        val CURRENT_STREAK = intPreferencesKey("current_streak")
        val LONGEST_STREAK = intPreferencesKey("longest_streak")
        val LAST_STUDY_DAY_EPOCH = longPreferencesKey("last_study_day_epoch") // day index, not millis
        val DAILY_GOAL_XP = intPreferencesKey("daily_goal_xp")
        val TODAY_XP = intPreferencesKey("today_xp")
        val TODAY_DAY_EPOCH = longPreferencesKey("today_day_epoch")
        val THEME_MODE = stringPreferencesKey("theme_mode") // "SYSTEM" | "LIGHT" | "DARK"
        val CURRENT_LEVEL = stringPreferencesKey("current_level") // A1..B2
    }

    private fun dayIndex(): Long = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis())

    val totalXp: Flow<Int> = context.dataStore.data.map { it[Keys.TOTAL_XP] ?: 0 }
    val currentStreak: Flow<Int> = context.dataStore.data.map { it[Keys.CURRENT_STREAK] ?: 0 }
    val longestStreak: Flow<Int> = context.dataStore.data.map { it[Keys.LONGEST_STREAK] ?: 0 }
    val dailyGoalXp: Flow<Int> = context.dataStore.data.map { it[Keys.DAILY_GOAL_XP] ?: 50 }
    val themeMode: Flow<String> = context.dataStore.data.map { it[Keys.THEME_MODE] ?: "SYSTEM" }
    val currentLevel: Flow<String> = context.dataStore.data.map { it[Keys.CURRENT_LEVEL] ?: "A1" }

    val todayXp: Flow<Int> = context.dataStore.data.map { prefs ->
        val storedDay = prefs[Keys.TODAY_DAY_EPOCH] ?: -1L
        if (storedDay == dayIndex()) prefs[Keys.TODAY_XP] ?: 0 else 0
    }

    suspend fun setDailyGoal(xp: Int) {
        context.dataStore.edit { it[Keys.DAILY_GOAL_XP] = xp }
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { it[Keys.THEME_MODE] = mode }
    }

    suspend fun setCurrentLevel(levelCode: String) {
        context.dataStore.edit { it[Keys.CURRENT_LEVEL] = levelCode }
    }

    /**
     * Call when a lesson/quiz/review is completed. Adds XP, updates the
     * today-XP bucket, and advances the streak counter (once per calendar
     * day, extends if yesterday was the last study day, resets otherwise).
     */
    suspend fun addXp(amount: Int) {
        context.dataStore.edit { prefs ->
            val today = dayIndex()
            val lastStudyDay = prefs[Keys.LAST_STUDY_DAY_EPOCH] ?: -1L

            prefs[Keys.TOTAL_XP] = (prefs[Keys.TOTAL_XP] ?: 0) + amount

            val storedTodayBucketDay = prefs[Keys.TODAY_DAY_EPOCH] ?: -1L
            val currentTodayXp = if (storedTodayBucketDay == today) prefs[Keys.TODAY_XP] ?: 0 else 0
            prefs[Keys.TODAY_XP] = currentTodayXp + amount
            prefs[Keys.TODAY_DAY_EPOCH] = today

            when {
                lastStudyDay == today -> { /* already studied today, streak unchanged */ }
                lastStudyDay == today - 1 -> {
                    val newStreak = (prefs[Keys.CURRENT_STREAK] ?: 0) + 1
                    prefs[Keys.CURRENT_STREAK] = newStreak
                    prefs[Keys.LONGEST_STREAK] = maxOf(prefs[Keys.LONGEST_STREAK] ?: 0, newStreak)
                }
                else -> {
                    prefs[Keys.CURRENT_STREAK] = 1
                    prefs[Keys.LONGEST_STREAK] = maxOf(prefs[Keys.LONGEST_STREAK] ?: 0, 1)
                }
            }
            prefs[Keys.LAST_STUDY_DAY_EPOCH] = today
        }
    }

    /** Should be called on app open to break the streak if a day was missed. */
    suspend fun checkAndBreakStreakIfMissed() {
        context.dataStore.edit { prefs ->
            val today = dayIndex()
            val lastStudyDay = prefs[Keys.LAST_STUDY_DAY_EPOCH] ?: return@edit
            if (lastStudyDay < today - 1) {
                prefs[Keys.CURRENT_STREAK] = 0
            }
        }
    }
}
