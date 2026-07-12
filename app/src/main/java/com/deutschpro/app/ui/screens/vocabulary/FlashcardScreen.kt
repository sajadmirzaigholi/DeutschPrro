package com.deutschpro.app.ui.screens.vocabulary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.ui.theme.Error40
import com.deutschpro.app.ui.theme.Success40

@Composable
fun FlashcardScreen(levelCode: String, onBack: () -> Unit) {
    val level = runCatching { LevelCode.valueOf(levelCode) }.getOrDefault(LevelCode.A1)
    val viewModel: FlashcardViewModel = viewModel(factory = FlashcardViewModel.factory(level))
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("فلش‌کارت — ${level.displayNameFa}") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowForward, contentDescription = "بازگشت") }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                state.isLoading -> Text("در حال بارگذاری...", modifier = Modifier.align(Alignment.Center))
                state.cards.isEmpty() -> Text(
                    "واژه‌ای برای این سطح وجود ندارد.",
                    modifier = Modifier.align(Alignment.Center).padding(24.dp)
                )
                state.sessionDone -> FlashcardSessionDone(state.knownCount, state.cards.size, onBack)
                else -> FlashcardContent(state, viewModel)
            }
        }
    }
}

@Composable
private fun FlashcardContent(state: FlashcardUiState, viewModel: FlashcardViewModel) {
    val card = state.currentCard ?: return
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(
            "کارت ${state.currentIndex + 1} از ${state.cards.size}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable { viewModel.flip() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (!state.isFlipped) {
                        Text(
                            "${card.article} ${card.germanWord}".trim(),
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "برای دیدن معنی ضربه بزنید",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    } else {
                        Text(card.persianTranslation, style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(12.dp))
                        Text(card.exampleSentenceDe, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                        Text(card.exampleSentenceFa, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        if (state.isFlipped) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { viewModel.answer(false) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Error40)
                ) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                    Spacer(Modifier.height(0.dp))
                    Text(" بلد نبودم")
                }
                Button(
                    onClick = { viewModel.answer(true) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Success40)
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null)
                    Text(" بلد بودم")
                }
            }
        } else {
            Button(onClick = { viewModel.flip() }, modifier = Modifier.fillMaxWidth()) {
                Text("نمایش معنی")
            }
        }
    }
}

@Composable
private fun FlashcardSessionDone(knownCount: Int, total: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("مرور تمام شد! 🎉", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("$knownCount از $total واژه را بلد بودید.", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("بازگشت به واژگان")
        }
    }
}
