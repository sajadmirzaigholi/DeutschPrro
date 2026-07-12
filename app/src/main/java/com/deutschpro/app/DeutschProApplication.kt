package com.deutschpro.app

import android.app.Application
import com.deutschpro.app.data.content.ContentSeeder
import com.deutschpro.app.data.local.AppDatabase
import com.deutschpro.app.data.repository.AchievementRepository
import com.deutschpro.app.data.repository.CourseRepository
import com.deutschpro.app.data.repository.GrammarRepository
import com.deutschpro.app.data.repository.QuizRepository
import com.deutschpro.app.data.repository.VocabularyRepository
import com.deutschpro.app.util.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Lightweight manual dependency container. The project intentionally
 * avoids pulling in Hilt/Dagger so the codebase stays approachable, but
 * every dependency is still created exactly once and injected through
 * constructors (see AppContainer usage in each ViewModel factory) —
 * swapping this for Hilt later is a mechanical, low-risk change.
 */
class DeutschProApplication : Application() {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var database: AppDatabase
        private set
    lateinit var preferencesManager: PreferencesManager
        private set
    lateinit var courseRepository: CourseRepository
        private set
    lateinit var vocabularyRepository: VocabularyRepository
        private set
    lateinit var grammarRepository: GrammarRepository
        private set
    lateinit var quizRepository: QuizRepository
        private set
    lateinit var achievementRepository: AchievementRepository
        private set

    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.getInstance(this)
        preferencesManager = PreferencesManager(this)

        courseRepository = CourseRepository(database.courseDao(), database.progressDao())
        vocabularyRepository = VocabularyRepository(database.vocabularyDao())
        grammarRepository = GrammarRepository(database.grammarDao())
        quizRepository = QuizRepository(database.quizDao())
        achievementRepository = AchievementRepository(database.progressDao())

        appScope.launch {
            ContentSeeder(database).seedIfNeeded()
            preferencesManager.checkAndBreakStreakIfMissed()
        }
    }
}
