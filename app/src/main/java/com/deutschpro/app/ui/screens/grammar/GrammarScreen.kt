@file:OptIn(ExperimentalMaterial3Api::class)

package com.deutschpro.app.ui.screens.grammar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.deutschpro.app.data.model.LevelCode
import androidx.compose.material3.ExperimentalMaterial3Api

@Composable
fun GrammarScreen(onTopicClick: (String) -> Unit) {
    val viewModel: GrammarViewModel = viewModel(factory = GrammarViewModel.factory())
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopAppBar(title = { Text("گرامر آلمانی") }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LevelCode.entries.forEach { level ->
                    FilterChip(
                        selected = state.selectedLevel == level,
                        onClick = { viewModel.selectLevel(level) },
                        label = { Text(level.displayNameFa) }
                    )
                }
            }

            if (state.topics.isEmpty()) {
                Text(
                    "نکته گرامری برای این سطح هنوز ثبت نشده است.",
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.topics, key = { it.id }) { topic ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTopicClick(topic.id) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(topic.titleFa, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        topic.titleDe,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Icon(Icons.Filled.ChevronLeft, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}
