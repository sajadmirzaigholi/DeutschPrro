package com.deutschpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deutschpro.app.data.local.LessonEntity
import com.deutschpro.app.data.local.UnitEntity
import com.deutschpro.app.data.model.LevelCode
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(units: List<UnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Query("SELECT COUNT(*) FROM units")
    suspend fun unitCount(): Int

    @Query("SELECT * FROM units WHERE levelCode = :level ORDER BY orderInLevel ASC")
    fun observeUnitsForLevel(level: LevelCode): Flow<List<UnitEntity>>

    @Query("SELECT * FROM lessons WHERE unitId = :unitId ORDER BY orderInUnit ASC")
    fun observeLessonsForUnit(unitId: String): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :lessonId LIMIT 1")
    suspend fun getLesson(lessonId: String): LessonEntity?

    @Query("SELECT * FROM units WHERE id = :unitId LIMIT 1")
    suspend fun getUnit(unitId: String): UnitEntity?

    @Query("SELECT * FROM lessons")
    fun observeAllLessons(): Flow<List<LessonEntity>>
}
