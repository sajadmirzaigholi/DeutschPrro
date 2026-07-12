package com.deutschpro.app.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deutschpro.app.ui.theme.StarFilled
import com.deutschpro.app.ui.theme.Success40

@Composable
fun QuizScreen(
    lessonId: String,
    onFinished: (scorePercent: Int) -> Unit,
    onClose: () -> Unit
) {
    val viewModel: QuizViewModel = viewModel(factory = QuizViewModel.factory(lessonId))
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                state.isLoading -> Text(
                    "در حال بارگذاری آزمون...",
                    modifier = Modifier.align(Alignment.Center)
                )
                state.questions.isEmpty() -> Column(
                    modifier = Modifier.align(Alignment.Center).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("برای این درس هنوز آزمونی ثبت نشده است.", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = onClose) { Text("بازگشت") }
                }
                state.isFinished -> QuizResultView(
                    scorePercent = state.scorePercent,
                    stars = state.stars,
                    onDone = { onFinished(state.scorePercent) }
                )
                else -> QuizQuestionView(state, viewModel)
            }
        }
    }
}

@Composable
private fun QuizQuestionView(state: QuizUiState, viewModel: QuizViewModel) {
    val question = state.currentQuestion ?: return
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        LinearProgressIndicator(
            progress = { (state.currentIndex + 1) / state.questions.size.toFloat() },
            modifier = Modifier.fillMaxWidth().height(8.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "سؤال ${state.currentIndex + 1} از ${state.questions.size}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        Text(question.questionTextFa, style = MaterialTheme.typography.titleLarge)
        if (question.questionTextDe.isNotBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(question.questionTextDe, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            question.options().forEachIndexed { index, option ->
                QuizOptionRow(
                    text = option,
                    index = index,
                    selectedIndex = state.selectedOptionIndex,
                    correctIndex = question.correctOptionIndex,
                    hasAnswered = state.hasAnswered,
                    onClick = { viewModel.selectOption(index) }
                )
            }
        }

        if (state.hasAnswered && question.explanationFa.isNotBlank()) {
            Spacer(Modifier.height(16.dp))
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Text(question.explanationFa, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { if (state.hasAnswered) viewModel.nextQuestion() else viewModel.confirmAnswer() },
            enabled = state.selectedOptionIndex != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.hasAnswered) "ادامه" else "بررسی پاسخ")
        }
    }
}

@Composable
private fun QuizOptionRow(
    text: String,
    index: Int,
    selectedIndex: Int?,
    correctIndex: Int,
    hasAnswered: Boolean,
    onClick: () -> Unit
) {
    val isSelected = index == selectedIndex
    val backgroundColor = when {
        hasAnswered && index == correctIndex -> Success40.copy(alpha = 0.2f)
        hasAnswered && isSelected && index != correctIndex -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val borderColor = when {
        hasAnswered && index == correctIndex -> Success40
        hasAnswered && isSelected && index != correctIndex -> MaterialTheme.colorScheme.error
        isSelected -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(14.dp))
            .clickable(enabled = !hasAnswered, onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
    if (borderColor != Color.Transparent) {
        // Simple visual emphasis without extra dependency: a thin colored spacer under the row.
        Spacer(modifier = Modifier.fillMaxWidth().height(2.dp).background(borderColor))
    }
}

@Composable
private fun QuizResultView(scorePercent: Int, stars: Int, onDone: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "★".repeat(stars) + "☆".repeat(3 - stars),
            color = StarFilled,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(Modifier.height(16.dp))
        Text("نتیجه آزمون", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("$scorePercent٪ پاسخ صحیح", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(32.dp))
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth()) {
            Text("پایان و بازگشت به مسیر یادگیری")
        }
    }
}
