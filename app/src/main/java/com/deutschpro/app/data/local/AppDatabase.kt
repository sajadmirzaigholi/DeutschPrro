package com.deutschpro.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deutschpro.app.data.local.dao.CourseDao
import com.deutschpro.app.data.local.dao.GrammarDao
import com.deutschpro.app.data.local.dao.ProgressDao
import com.deutschpro.app.data.local.dao.QuizDao
import com.deutschpro.app.data.local.dao.VocabularyDao

/**
 * Single offline-first Room database. Everything the user needs to keep
 * learning without an internet connection lives here: course content
 * (seeded on first launch) plus all user-generated progress data.
 */
@Database(
    entities = [
        UnitEntity::class,
        LessonEntity::class,
        VocabularyEntity::class,
        FavoriteWordEntity::class,
        WordReviewEntity::class,
        GrammarTopicEntity::class,
        QuizQuestionEntity::class,
        LessonProgressEntity::class,
        MistakeEntity::class,
        AchievementEntity::class,
        UnlockedAchievementEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun vocabularyDao(): VocabularyDao
    abstract fun grammarDao(): GrammarDao
    abstract fun quizDao(): QuizDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "deutschpro.db"
                ).build().also { INSTANCE = it }
            }
    }
}
