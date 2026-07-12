package com.deutschpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deutschpro.app.data.local.MistakeEntity
import com.deutschpro.app.data.local.QuizQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizQuestionEntity>)

    @Query("SELECT COUNT(*) FROM quiz_questions")
    suspend fun questionCount(): Int

    @Query("SELECT * FROM quiz_questions WHERE lessonId = :lessonId")
    suspend fun getQuestionsForLesson(lessonId: String): List<QuizQuestionEntity>

    @Query("SELECT * FROM quiz_questions WHERE lessonId IN (:lessonIds) ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomQuestionsForLessons(lessonIds: List<String>, limit: Int): List<QuizQuestionEntity>

    @Insert
    suspend fun logMistake(mistake: MistakeEntity)

    @Query("SELECT * FROM mistakes WHERE resolved = 0 ORDER BY timestampEpochMillis DESC")
    fun observeUnresolvedMistakes(): Flow<List<MistakeEntity>>

    @Query("UPDATE mistakes SET resolved = 1 WHERE questionId = :questionId")
    suspend fun markMistakeResolved(questionId: String)
}
