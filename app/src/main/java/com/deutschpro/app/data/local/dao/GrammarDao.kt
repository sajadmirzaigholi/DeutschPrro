package com.deutschpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deutschpro.app.data.local.GrammarTopicEntity
import com.deutschpro.app.data.model.LevelCode
import kotlinx.coroutines.flow.Flow

@Dao
interface GrammarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<GrammarTopicEntity>)

    @Query("SELECT COUNT(*) FROM grammar_topics")
    suspend fun topicCount(): Int

    @Query("SELECT * FROM grammar_topics WHERE levelCode = :level ORDER BY orderInLesson ASC")
    fun observeTopicsForLevel(level: LevelCode): Flow<List<GrammarTopicEntity>>

    @Query("SELECT * FROM grammar_topics WHERE lessonId = :lessonId ORDER BY orderInLesson ASC")
    fun observeTopicsForLesson(lessonId: String): Flow<List<GrammarTopicEntity>>

    @Query("SELECT * FROM grammar_topics WHERE id = :id LIMIT 1")
    suspend fun getTopic(id: String): GrammarTopicEntity?
}
