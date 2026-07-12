package com.deutschpro.app.data.local

import androidx.room.Entity

/** Words the user marked with the star/favorite icon. */
@Entity(tableName = "favorite_words", primaryKeys = ["wordId"])
data class FavoriteWordEntity(
    val wordId: String,
    val addedAtEpochMillis: Long
)

/**
 * Lightweight spaced-repetition tracker for the flashcard review mode.
 * Every time a user reviews a word we bump reviewCount and set the next
 * due date further into the future (simple Leitner-style progression).
 */
@Entity(tableName = "word_review", primaryKeys = ["wordId"])
data class WordReviewEntity(
    val wordId: String,
    val boxLevel: Int = 0,          // 0..5, higher = better known
    val reviewCount: Int = 0,
    val lastReviewedEpochMillis: Long = 0L,
    val nextDueEpochMillis: Long = 0L,
    val isMastered: Boolean = false
)
