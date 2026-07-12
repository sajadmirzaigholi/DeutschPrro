package com.deutschpro.app.util

/**
 * Central XP/leveling rules so reward numbers are consistent everywhere
 * (lesson completion, quiz score, flashcard review, daily goal bonus).
 */
object XpEngine {
    const val XP_LESSON_COMPLETE = 20
    const val XP_PERFECT_QUIZ_BONUS = 10
    const val XP_FLASHCARD_REVIEW = 2
    const val XP_DAILY_GOAL_BONUS = 15

    /** Simple square-root curve: user "level" grows slower as XP increases. */
    fun userLevelForXp(totalXp: Int): Int = (1 + kotlin.math.sqrt(totalXp / 50.0)).toInt()

    fun xpNeededForNextUserLevel(currentUserLevel: Int): Int {
        val nextLevel = currentUserLevel + 1
        return ((nextLevel - 1) * (nextLevel - 1)) * 50
    }

    fun starsForScore(percent: Int): Int = when {
        percent >= 90 -> 3
        percent >= 70 -> 2
        percent >= 50 -> 1
        else -> 0
    }
}
