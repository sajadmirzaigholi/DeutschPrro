package com.deutschpro.app.data.content

import com.deutschpro.app.data.local.AppDatabase

/**
 * Populates the local database with course content the very first time
 * the app runs, so learning works fully offline right away. Safe to call
 * on every app start: it checks counts before writing anything.
 */
class ContentSeeder(private val db: AppDatabase) {

    suspend fun seedIfNeeded() {
        val courseDao = db.courseDao()
        val vocabDao = db.vocabularyDao()
        val grammarDao = db.grammarDao()
        val quizDao = db.quizDao()
        val progressDao = db.progressDao()

        if (courseDao.unitCount() == 0) {
            courseDao.insertUnits(
                listOf(A1Unit1Content.unit, A1Unit2Content.unit) + UpperLevelsPlaceholder.allUnits
            )
            courseDao.insertLessons(
                A1Unit1Content.allLessons + A1Unit2Content.allLessons + UpperLevelsPlaceholder.allLessons
            )
        }

        if (vocabDao.wordCount() == 0) {
            vocabDao.insertWords(A1Unit1Content.vocabulary + A1Unit2Content.vocabulary)
        }

        if (grammarDao.topicCount() == 0) {
            grammarDao.insertTopics(A1Unit1Content.grammarTopics + A1Unit2Content.grammarTopics)
        }

        if (quizDao.questionCount() == 0) {
            quizDao.insertQuestions(A1Unit1Content.quizQuestions + A1Unit2Content.quizQuestions)
        }

        if (progressDao.achievementCount() == 0) {
            progressDao.insertAchievements(AchievementCatalog.all)
        }
    }
}
