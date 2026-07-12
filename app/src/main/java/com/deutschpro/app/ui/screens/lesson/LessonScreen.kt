package com.deutschpro.app.ui.screens.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

private val skillTabTitles = listOf("واژگان", "گرامر", "خواندن", "شنیدن", "نوشتن", "صحبت‌کردن")

@Composable
fun LessonScreen(
    lessonId: String,
    onBack: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    val viewModel: LessonViewModel = viewModel(factory = LessonViewModel.factory(lessonId))
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lesson by viewModel.lesson.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(lesson?.titleFa ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                skillTabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                when (selectedTab) {
                    0 -> VocabularyTab(state.vocabulary, state.favoriteIds, viewModel::toggleFavorite)
                    1 -> GrammarTab(state.grammarTopics.map { it.titleFa to it.explanationFa to (it.examplesDe to it.examplesFa) })
                    2 -> ReadingTab(lesson?.readingTextDe.orEmpty(), lesson?.readingTextFa.orEmpty())
                    3 -> ListeningTab(lesson?.listeningPromptFa.orEmpty(), lesson?.listeningScript.orEmpty())
                    4 -> WritingTab(lesson?.writingPromptFa.orEmpty(), lesson?.writingExampleDe.orEmpty())
                    5 -> SpeakingTab(lesson?.speakingPromptFa.orEmpty(), lesson?.speakingExampleDe.orEmpty())
                }

                androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 12.dp))

                Button(
                    onClick = { onStartQuiz(lessonId) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("شروع آزمون این درس")
                }
            }
        }
    }
}

@Composable
private fun VocabularyTab(
    words: List<com.deutschpro.app.data.local.VocabularyEntity>,
    favoriteIds: Set<String>,
    onToggleFavorite: (String) -> Unit
) {
    if (words.isEmpty()) {
        Text("واژه‌ای برای این درس ثبت نشده است.", style = MaterialTheme.typography.bodyMedium)
        return
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(words) { word ->
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${word.article} ${word.germanWord}".trim(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(onClick = { onToggleFavorite(word.id) }) {
                            val isFav = favoriteIds.contains(word.id)
                            Icon(
                                if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "علاقه‌مندی",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Text(word.persianTranslation, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        word.exampleSentenceDe,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        word.exampleSentenceFa,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun GrammarTab(topics: List<Pair<Pair<String, String>, Pair<String, String>>>) {
    if (topics.isEmpty()) {
        Text("این درس شامل نکته گرامری جدیدی نیست.", style = MaterialTheme.typography.bodyMedium)
        return
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(topics) { (titleAndExplanation, examples) ->
            val (title, explanation) = titleAndExplanation
            val (examplesDe, examplesFa) = examples
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(title, style = MaterialTheme.typography.titleMedium)
                    Text(explanation, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 6.dp))
                    Text(examplesDe, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
                    Text(examplesFa, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun ReadingTab(textDe: String, textFa: String) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("متن (Lesen)", style = MaterialTheme.typography.titleMedium)
        Card { Text(textDe, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodyLarge) }
        Text("ترجمه", style = MaterialTheme.typography.titleMedium)
        Card { Text(textFa, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium) }
    }
}

@Composable
private fun ListeningTab(promptFa: String, script: String) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("تمرین شنیداری (Hören)", style = MaterialTheme.typography.titleMedium)
        Text(promptFa, style = MaterialTheme.typography.bodyMedium)
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("متن صوتی (برای پخش با موتور صوتی/فایل ضبط‌شده):", style = MaterialTheme.typography.labelLarge)
                Text(script, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 6.dp))
            }
        }
    }
}

@Composable
private fun WritingTab(promptFa: String, exampleDe: String) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("تمرین نوشتاری (Schreiben)", style = MaterialTheme.typography.titleMedium)
        Text(promptFa, style = MaterialTheme.typography.bodyMedium)
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("نمونه پاسخ:", style = MaterialTheme.typography.labelLarge)
                Text(exampleDe, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 6.dp))
            }
        }
    }
}

@Composable
private fun SpeakingTab(promptFa: String, exampleDe: String) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("تمرین گفتاری (Sprechen)", style = MaterialTheme.typography.titleMedium)
        Text(promptFa, style = MaterialTheme.typography.bodyMedium)
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("نمونه جمله برای تمرین تلفظ:", style = MaterialTheme.typography.labelLarge)
                Text(exampleDe, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 6.dp))
            }
        }
    }
}
