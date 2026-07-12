package com.deutschpro.app.data.repository

import com.deutschpro.app.data.local.LessonEntity
import com.deutschpro.app.data.local.LessonProgressEntity
import com.deutschpro.app.data.local.UnitEntity
import com.deutschpro.app.data.local.dao.CourseDao
import com.deutschpro.app.data.local.dao.ProgressDao
import com.deutschpro.app.data.model.LessonState
import com.deutschpro.app.data.model.LevelCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

data class LessonWithState(
    val lesson: LessonEntity,
    val state: LessonState,
    val bestScorePercent: Int,
    val starsEarned: Int
)

data class UnitWithLessons(
    val unit: UnitEntity,
    val lessons: List<LessonWithState>
)

/**
 * Owns the learning-path logic: which lesson is locked/unlocked/completed.
 * Rule: the very first lesson of A1 Unit 1 is always unlocked. Every other
 * lesson unlocks once the lesson immediately before it (in global order)
 * has been completed.
 */
class CourseRepository(
    private val courseDao: CourseDao,
    private val progressDao: ProgressDao
) {
    fun observeUnitsForLevel(level: LevelCode): Flow<List<UnitEntity>> =
        courseDao.observeUnitsForLevel(level)

    fun observeLearningPath(level: LevelCode): Flow<List<UnitWithLessons>> =
        combine(
            courseDao.observeUnitsForLevel(level),
            courseDao.observeAllLessons(),
            progressDao.observeAllProgress()
        ) { units, allLessons, allProgress ->
            val progressByLessonId = allProgress.associateBy { it.lessonId }
            val globallyOrderedLessons = allLessons.sortedWith(
                compareBy({ it.unitId }, { it.orderInUnit })
            )

            var previousCompleted = true // first lesson overall is always unlocked
            val stateByLessonId = HashMap<String, LessonState>()
            for (lesson in globallyOrderedLessons) {
                val progress = progressByLessonId[lesson.id]
                val state = when {
                    progress?.state == LessonState.COMPLETED -> LessonState.COMPLETED
                    previousCompleted -> LessonState.UNLOCKED
                    else -> LessonState.LOCKED
                }
                stateByLessonId[lesson.id] = state
                previousCompleted = state == LessonState.COMPLETED
            }

            units.map { unit ->
                val lessonsForUnit = allLessons
                    .filter { it.unitId == unit.id }
                    .sortedBy { it.orderInUnit }
                    .map { lesson ->
                        val progress: LessonProgressEntity? = progressByLessonId[lesson.id]
                        LessonWithState(
                            lesson = lesson,
                            state = stateByLessonId[lesson.id] ?: LessonState.LOCKED,
                            bestScorePercent = progress?.bestScorePercent ?: 0,
                            starsEarned = progress?.starsEarned ?: 0
                        )
                    }
                UnitWithLessons(unit, lessonsForUnit)
            }
        }

    suspend fun getLesson(lessonId: String) = courseDao.getLesson(lessonId)

    suspend fun markLessonCompleted(lessonId: String, scorePercent: Int, stars: Int) {
        val existing = progressDao.getProgress(lessonId)
        progressDao.upsertLessonProgress(
            LessonProgressEntity(
                lessonId = lessonId,
                state = LessonState.COMPLETED,
                bestScorePercent = maxOf(existing?.bestScorePercent ?: 0, scorePercent),
                starsEarned = maxOf(existing?.starsEarned ?: 0, stars),
                timesCompleted = (existing?.timesCompleted ?: 0) + 1,
                lastCompletedEpochMillis = System.currentTimeMillis()
            )
        )
    }

    fun observeCompletedLessonCount(): Flow<Int> = progressDao.observeCompletedLessonCount()
}
