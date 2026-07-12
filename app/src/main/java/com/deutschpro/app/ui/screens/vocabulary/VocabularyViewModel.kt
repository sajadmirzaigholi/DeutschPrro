package com.deutschpro.app.ui.screens.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.repository.VocabularyRepository
import com.deutschpro.app.util.ViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class VocabularyUiState(
    val selectedLevel: LevelCode = LevelCode.A1,
    val query: String = "",
    val words: List<VocabularyEntity> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val showFavoritesOnly: Boolean = false
)

class VocabularyViewModel(
    private val repository: VocabularyRepository
) : ViewModel() {

    private val selectedLevel = MutableStateFlow(LevelCode.A1)
    private val query = MutableStateFlow("")
    private val showFavoritesOnly = MutableStateFlow(false)

    private val wordsFlow = combine(selectedLevel, query, showFavoritesOnly) { level, q, favOnly ->
        Triple(level, q, favOnly)
    }.flatMapLatest { (level, q, favOnly) ->
        when {
            favOnly -> repository.observeFavoriteWords()
            q.isNotBlank() -> repository.search(q)
            else -> repository.observeWordsForLevel(level)
        }
    }

    val uiState: StateFlow<VocabularyUiState> = combine(
        selectedLevel, query, wordsFlow, repository.observeFavoriteIds(), showFavoritesOnly
    ) { level, q, words, favIds, favOnly ->
        VocabularyUiState(level, q, words, favIds.toSet(), favOnly)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VocabularyUiState())

    fun selectLevel(level: LevelCode) { selectedLevel.value = level }
    fun setQuery(text: String) { query.value = text }
    fun toggleFavoritesOnly() { showFavoritesOnly.value = !showFavoritesOnly.value }

    fun toggleFavorite(wordId: String) {
        viewModelScope.launch {
            val isFav = uiState.value.favoriteIds.contains(wordId)
            repository.toggleFavorite(wordId, isFav)
        }
    }

    companion object {
        fun factory() = ViewModelFactory { app: DeutschProApplication ->
            VocabularyViewModel(app.vocabularyRepository)
        }
    }
}
