package com.deutschpro.app.ui.screens.grammar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.local.GrammarTopicEntity
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.repository.GrammarRepository
import com.deutschpro.app.util.ViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

data class GrammarListUiState(
    val selectedLevel: LevelCode = LevelCode.A1,
    val topics: List<GrammarTopicEntity> = emptyList()
)

class GrammarViewModel(private val repository: GrammarRepository) : ViewModel() {

    private val selectedLevel = MutableStateFlow(LevelCode.A1)

    val uiState: StateFlow<GrammarListUiState> = combine(
        selectedLevel,
        selectedLevel.flatMapLatest { repository.observeTopicsForLevel(it) }
    ) { level, topics -> GrammarListUiState(level, topics) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GrammarListUiState())

    fun selectLevel(level: LevelCode) { selectedLevel.value = level }

    companion object {
        fun factory() = ViewModelFactory { app: DeutschProApplication ->
            GrammarViewModel(app.grammarRepository)
        }
    }
}
