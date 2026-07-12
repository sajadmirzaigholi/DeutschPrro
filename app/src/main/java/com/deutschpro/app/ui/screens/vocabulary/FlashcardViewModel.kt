package com.deutschpro.app.ui.screens.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.repository.VocabularyRepository
import com.deutschpro.app.util.PreferencesManager
import com.deutschpro.app.util.ViewModelFactory
import com.deutschpro.app.util.XpEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class FlashcardUiState(
    val cards: List<VocabularyEntity> = emptyList(),
    val currentIndex: Int = 0,
    val isFlipped: Boolean = false,
    val sessionDone: Boolean = false,
    val knownCount: Int = 0,
    val isLoading: Boolean = true
) {
    val currentCard: VocabularyEntity? get() = cards.getOrNull(currentIndex)
}

class FlashcardViewModel(
    private val levelCode: LevelCode,
    private val repository: VocabularyRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlashcardUiState())
    val uiState: StateFlow<FlashcardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val words = repository.observeWordsForLevel(levelCode).first()
            _uiState.value = _uiState.value.copy(cards = words.shuffled(), isLoading = false)
        }
    }

    fun flip() {
        _uiState.value = _uiState.value.copy(isFlipped = !_uiState.value.isFlipped)
    }

    fun answer(knewIt: Boolean) {
        val state = _uiState.value
        val card = state.currentCard ?: return
        viewModelScope.launch {
            repository.recordFlashcardResult(card.id, knewIt)
            preferencesManager.addXp(XpEngine.XP_FLASHCARD_REVIEW)

            val nextIndex = state.currentIndex + 1
            _uiState.value = if (nextIndex >= state.cards.size) {
                state.copy(sessionDone = true, knownCount = state.knownCount + if (knewIt) 1 else 0)
            } else {
                state.copy(
                    currentIndex = nextIndex,
                    isFlipped = false,
                    knownCount = state.knownCount + if (knewIt) 1 else 0
                )
            }
        }
    }

    companion object {
        fun factory(levelCode: LevelCode) = ViewModelFactory { app: DeutschProApplication ->
            FlashcardViewModel(levelCode, app.vocabularyRepository, app.preferencesManager)
        }
    }
}
