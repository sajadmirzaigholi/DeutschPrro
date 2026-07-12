package com.deutschpro.app.ui.screens.vocabulary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.model.LevelCode

@Composable
fun VocabularyScreen(onOpenFlashcards: (String) -> Unit) {
    val viewModel: VocabularyViewModel = viewModel(factory = VocabularyViewModel.factory())
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("واژگان آلمانی") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onOpenFlashcards(state.selectedLevel.name) }) {
                Icon(Icons.Filled.School, contentDescription = "حالت فلش‌کارت")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = state.query,
                onValueChange = viewModel::setQuery,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("جستجوی واژه (آلمانی یا فارسی)") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LevelCode.entries.forEach { level ->
                    FilterChip(
                        selected = state.selectedLevel == level && !state.showFavoritesOnly,
                        onClick = { viewModel.selectLevel(level) },
                        label = { Text(level.displayNameFa) }
                    )
                }
                FilterChip(
                    selected = state.showFavoritesOnly,
                    onClick = viewModel::toggleFavoritesOnly,
                    label = { Text("موردعلاقه‌ها") },
                    leadingIcon = { Icon(Icons.Filled.Favorite, contentDescription = null) }
                )
            }

            if (state.words.isEmpty()) {
                Text(
                    "واژه‌ای یافت نشد.",
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.words, key = { it.id }) { word ->
                        VocabularyRow(
                            word = word,
                            isFavorite = state.favoriteIds.contains(word.id),
                            onToggleFavorite = { viewModel.toggleFavorite(word.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VocabularyRow(word: VocabularyEntity, isFavorite: Boolean, onToggleFavorite: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${word.article} ${word.germanWord}".trim(), style = MaterialTheme.typography.titleMedium)
                Text(word.persianTranslation, style = MaterialTheme.typography.bodyMedium)
                Text(
                    word.exampleSentenceDe,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "علاقه‌مندی",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
