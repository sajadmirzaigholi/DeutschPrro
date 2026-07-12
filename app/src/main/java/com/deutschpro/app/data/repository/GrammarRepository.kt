package com.deutschpro.app.data.repository

import com.deutschpro.app.data.local.dao.GrammarDao
import com.deutschpro.app.data.model.LevelCode

class GrammarRepository(private val dao: GrammarDao) {
    fun observeTopicsForLevel(level: LevelCode) = dao.observeTopicsForLevel(level)
    fun observeTopicsForLesson(lessonId: String) = dao.observeTopicsForLesson(lessonId)
    suspend fun getTopic(id: String) = dao.getTopic(id)
}
