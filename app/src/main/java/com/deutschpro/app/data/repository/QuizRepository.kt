package com.deutschpro.app.data.repository

import com.deutschpro.app.data.local.MistakeEntity
import com.deutschpro.app.data.local.QuizQuestionEntity
import com.deutschpro.app.data.local.dao.QuizDao

data class QuizResult(
    val totalQuestions: Int,
    val correctAnswers: Int
) {
    val scorePercent: Int
        get() = if (totalQuestions == 0) 0 else (correctAnswers * 100) / totalQuestions
}

class QuizRepository(private val dao: QuizDao) {

    suspend fun getQuestionsForLesson(lessonId: String): List<QuizQuestionEntity> =
        dao.getQuestionsForLesson(lessonId)

    suspend fun getLevelTestQuestions(lessonIds: List<String>, count: Int = 20): List<QuizQuestionEntity> =
        dao.getRandomQuestionsForLessons(lessonIds, count)

    suspend fun submitAnswer(
        question: QuizQuestionEntity,
        chosenOptionIndex: Int
    ): Boolean {
        val correct = chosenOptionIndex == question.correctOptionIndex
        if (!correct) {
            dao.logMistake(
                MistakeEntity(
                    questionId = question.id,
                    lessonId = question.lessonId,
                    chosenOptionIndex = chosenOptionIndex,
                    timestampEpochMillis = System.currentTimeMillis()
                )
            )
        } else {
            dao.markMistakeResolved(question.id)
        }
        return correct
    }

    fun observeUnresolvedMistakes() = dao.observeUnresolvedMistakes()
}
