package com.deutschpro.app.data.repository

import com.deutschpro.app.data.local.FavoriteWordEntity
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.local.WordReviewEntity
import com.deutschpro.app.data.local.dao.VocabularyDao
import com.deutschpro.app.data.model.LevelCode
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class VocabularyRepository(private val dao: VocabularyDao) {

    fun observeWordsForLevel(level: LevelCode): Flow<List<VocabularyEntity>> =
        dao.observeWordsForLevel(level)

    fun observeWordsForLesson(lessonId: String): Flow<List<VocabularyEntity>> =
        dao.observeWordsForLesson(lessonId)

    fun search(query: String): Flow<List<VocabularyEntity>> = dao.searchWords(query)

    fun observeCategoriesForLevel(level: LevelCode) = dao.observeCategoriesForLevel(level)

    fun observeFavoriteIds(): Flow<List<String>> = dao.observeFavoriteIds()

    fun observeFavoriteWords(): Flow<List<VocabularyEntity>> = dao.observeFavoriteWords()

    suspend fun toggleFavorite(wordId: String, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            dao.removeFavorite(FavoriteWordEntity(wordId, 0L))
        } else {
            dao.addFavorite(FavoriteWordEntity(wordId, System.currentTimeMillis()))
        }
    }

    /**
     * Leitner-style flashcard review: correct answer -> promote a box level
     * (further review interval), wrong answer -> demote to box 0 (review
     * again soon). Box intervals: 0=10min,1=1d,2=3d,3=7d,4=14d,5=mastered.
     */
    suspend fun recordFlashcardResult(wordId: String, wasCorrect: Boolean) {
        val existing = dao.getReview(wordId) ?: WordReviewEntity(wordId = wordId)
        val newBox = if (wasCorrect) (existing.boxLevel + 1).coerceAtMost(5) else 0
        val intervalMillis = when (newBox) {
            0 -> TimeUnit.MINUTES.toMillis(10)
            1 -> TimeUnit.DAYS.toMillis(1)
            2 -> TimeUnit.DAYS.toMillis(3)
            3 -> TimeUnit.DAYS.toMillis(7)
            4 -> TimeUnit.DAYS.toMillis(14)
            else -> TimeUnit.DAYS.toMillis(30)
        }
        val now = System.currentTimeMillis()
        dao.upsertReview(
            existing.copy(
                boxLevel = newBox,
                reviewCount = existing.reviewCount + 1,
                lastReviewedEpochMillis = now,
                nextDueEpochMillis = now + intervalMillis,
                isMastered = newBox == 5
            )
        )
    }

    suspend fun getDueReviewWordIds(): List<String> =
        dao.getDueReviews(System.currentTimeMillis()).map { it.wordId }
}
