package com.deutschpro.app.ui.screens.learningpath

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.ui.components.DailyGoalRing
import com.deutschpro.app.ui.components.LessonNode
import com.deutschpro.app.ui.components.StreakBadge
import com.deutschpro.app.ui.components.XpBadge

@Composable
fun LearningPathScreen(
    onLessonClick: (String) -> Unit
) {
    val viewModel: LearningPathViewModel = viewModel(factory = LearningPathViewModel.factory())
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("مسیر یادگیری آلمانی") },
                actions = {
                    XpBadge(state.totalXp, modifier = Modifier.padding(end = 4.dp))
                    StreakBadge(state.currentStreak, modifier = Modifier.padding(end = 8.dp))
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            LevelSelector(
                selected = state.selectedLevel,
                onSelect = viewModel::selectLevel
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "هدف امروز: ${state.todayXp} از ${state.dailyGoalXp} XP",
                    style = MaterialTheme.typography.bodyMedium
                )
                DailyGoalRing(todayXp = state.todayXp, goalXp = state.dailyGoalXp)
            }

            if (state.isLevelEmpty) {
                Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        "محتوای سطح ${state.selectedLevel.displayNameFa} به‌زودی اضافه می‌شود. فعلاً سطح A1 را کامل کنید!",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)) {
                items(state.unitsWithLessons) { unitWithLessons ->
                    Text(
                        unitWithLessons.unit.titleFa,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                    )
                    Text(
                        unitWithLessons.unit.descriptionFa,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
                    ) {
                        unitWithLessons.lessons.forEach { lessonWithState ->
                            LessonNode(
                                titleFa = lessonWithState.lesson.titleFa,
                                nodeType = lessonWithState.lesson.nodeType,
                                state = lessonWithState.state,
                                stars = lessonWithState.starsEarned,
                                onClick = { onLessonClick(lessonWithState.lesson.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelSelector(selected: LevelCode, onSelect: (LevelCode) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LevelCode.entries.forEach { level ->
            FilterChip(
                selected = level == selected,
                onClick = { onSelect(level) },
                label = { Text(level.displayNameFa) },
                colors = FilterChipDefaults.filterChipColors()
            )
        }
    }
}
