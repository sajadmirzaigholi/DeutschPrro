package com.deutschpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deutschpro.app.data.local.FavoriteWordEntity
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.local.WordReviewEntity
import com.deutschpro.app.data.model.LevelCode
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<VocabularyEntity>)

    @Query("SELECT COUNT(*) FROM vocabulary")
    suspend fun wordCount(): Int

    @Query("SELECT * FROM vocabulary WHERE levelCode = :level ORDER BY topicCategory ASC")
    fun observeWordsForLevel(level: LevelCode): Flow<List<VocabularyEntity>>

    @Query("SELECT * FROM vocabulary WHERE lessonId = :lessonId")
    fun observeWordsForLesson(lessonId: String): Flow<List<VocabularyEntity>>

    @Query(
        "SELECT * FROM vocabulary WHERE germanWord LIKE '%' || :query || '%' " +
            "OR persianTranslation LIKE '%' || :query || '%' ORDER BY germanWord ASC"
    )
    fun searchWords(query: String): Flow<List<VocabularyEntity>>

    @Query("SELECT DISTINCT topicCategory FROM vocabulary WHERE levelCode = :level")
    fun observeCategoriesForLevel(level: LevelCode): Flow<List<String>>

    // --- Favorites ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteWordEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteWordEntity)

    @Query("SELECT wordId FROM favorite_words")
    fun observeFavoriteIds(): Flow<List<String>>

    @Query(
        "SELECT v.* FROM vocabulary v INNER JOIN favorite_words f ON v.id = f.wordId " +
            "ORDER BY f.addedAtEpochMillis DESC"
    )
    fun observeFavoriteWords(): Flow<List<VocabularyEntity>>

    // --- Spaced-repetition review ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertReview(review: WordReviewEntity)

    @Query("SELECT * FROM word_review WHERE wordId = :wordId LIMIT 1")
    suspend fun getReview(wordId: String): WordReviewEntity?

    @Query("SELECT * FROM word_review WHERE nextDueEpochMillis <= :nowMillis AND isMastered = 0")
    suspend fun getDueReviews(nowMillis: Long): List<WordReviewEntity>
}
