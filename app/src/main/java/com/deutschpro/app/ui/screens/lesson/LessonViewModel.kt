package com.deutschpro.app.ui.screens.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.local.GrammarTopicEntity
import com.deutschpro.app.data.local.LessonEntity
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.repository.AchievementRepository
import com.deutschpro.app.data.repository.CourseRepository
import com.deutschpro.app.data.repository.GrammarRepository
import com.deutschpro.app.data.repository.UserStatsSnapshot
import com.deutschpro.app.data.repository.VocabularyRepository
import com.deutschpro.app.util.PreferencesManager
import com.deutschpro.app.util.ViewModelFactory
import com.deutschpro.app.util.XpEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LessonUiState(
    val lesson: LessonEntity? = null,
    val vocabulary: List<VocabularyEntity> = emptyList(),
    val grammarTopics: List<GrammarTopicEntity> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val isCompleted: Boolean = false,
    val justEarnedXp: Int? = null
)

class LessonViewModel(
    private val lessonId: String,
    private val courseRepository: CourseRepository,
    private val vocabularyRepository: VocabularyRepository,
    private val grammarRepository: GrammarRepository,
    private val preferencesManager: PreferencesManager,
    private val achievementRepository: AchievementRepository
) : ViewModel() {

    private val lessonIdFlow = MutableStateFlow(lessonId)
    private val justEarnedXp = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<LessonUiState> = combine(
        vocabularyRepository.observeWordsForLesson(lessonId),
        grammarRepository.observeTopicsForLesson(lessonId),
        vocabularyRepository.observeFavoriteIds(),
        justEarnedXp
    ) { vocab, grammar, favorites, earnedXp ->
        LessonUiState(
            vocabulary = vocab,
            grammarTopics = grammar,
            favoriteIds = favorites.toSet(),
            justEarnedXp = earnedXp
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LessonUiState())

    private val _lesson = MutableStateFlow<LessonEntity?>(null)
    val lesson: StateFlow<LessonEntity?> = _lesson

    init {
        viewModelScope.launch {
            _lesson.value = courseRepository.getLesson(lessonId)
        }
    }

    fun toggleFavorite(wordId: String) {
        viewModelScope.launch {
            val isFav = uiState.value.favoriteIds.contains(wordId)
            vocabularyRepository.toggleFavorite(wordId, isFav)
        }
    }

    /** Called when the user finishes all skill tabs (or skips straight through). */
    fun completeLessonWithoutQuiz() {
        viewModelScope.launch {
            val xp = XpEngine.XP_LESSON_COMPLETE
            courseRepository.markLessonCompleted(lessonId, scorePercent = 0, stars = 0)
            preferencesManager.addXp(xp)
            justEarnedXp.value = xp
            evaluateAchievements()
        }
    }

    /** Called after a quiz finishes with a given score, awarding bonus XP for perfect runs. */
    fun completeLessonWithQuiz(scorePercent: Int, stars: Int) {
        viewModelScope.launch {
            var xp = XpEngine.XP_LESSON_COMPLETE
            if (scorePercent == 100) xp += XpEngine.XP_PERFECT_QUIZ_BONUS
            courseRepository.markLessonCompleted(lessonId, scorePercent, stars)
            preferencesManager.addXp(xp)
            justEarnedXp.value = xp
            evaluateAchievements()
        }
    }

    private suspend fun evaluateAchievements() {
        val allAchievements = achievementRepository.observeAllAchievements().first()
        val unlockedIds = achievementRepository.observeUnlockedIds().first().toSet()
        val totalXp = preferencesManager.totalXp.first()
        val streak = preferencesManager.currentStreak.first()
        val lessonsCompleted = courseRepository.observeCompletedLessonCount().first()
        achievementRepository.evaluateAndUnlock(
            allAchievements,
            unlockedIds,
            UserStatsSnapshot(
                currentStreak = streak,
                totalXp = totalXp,
                wordsLearned = 0,
                lessonsCompleted = lessonsCompleted,
                perfectQuizzes = 0,
                levelsCompleted = 0
            )
        )
    }

    companion object {
        fun factory(lessonId: String) = ViewModelFactory { app: DeutschProApplication ->
            LessonViewModel(
                lessonId,
                app.courseRepository,
                app.vocabularyRepository,
                app.grammarRepository,
                app.preferencesManager,
                app.achievementRepository
            )
        }
    }
}
