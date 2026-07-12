package com.deutschpro.app.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deutschpro.app.ui.components.AchievementCard
import com.deutschpro.app.ui.components.StreakBadge
import com.deutschpro.app.ui.components.XpBadge

@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.factory())
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopAppBar(title = { Text("پروفایل من") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            StatsSummaryCard(state)
            DailyGoalCard(state.dailyGoalXp, onGoalChange = viewModel::setDailyGoal)
            ThemeCard(state.themeMode, onThemeChange = viewModel::setThemeMode)
            AchievementsSection(state)
        }
    }
}

@Composable
private fun StatsSummaryCard(state: ProfileUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("سطح کاربری ${state.userLevel}", style = MaterialTheme.typography.titleLarge)
                    Text("${state.lessonsCompleted} درس کامل‌شده", style = MaterialTheme.typography.bodyMedium)
                }
                Column(horizontalAlignment = Alignment.End) {
                    XpBadge(state.totalXp)
                    androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 6.dp))
                    StreakBadge(state.currentStreak)
                }
            }
            androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 12.dp))
            Text(
                "بلندترین رکورد پیوسته: ${state.longestStreak} روز",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 8.dp))
            val progress = if (state.xpForNextLevel == 0) 0f else (state.totalXp.toFloat() / state.xpForNextLevel).coerceIn(0f, 1f)
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
            Text(
                "${state.totalXp} / ${state.xpForNextLevel} XP تا سطح بعدی",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun DailyGoalCard(currentGoal: Int, onGoalChange: (Int) -> Unit) {
    var sliderValue by remember(currentGoal) { mutableFloatStateOf(currentGoal.toFloat()) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("هدف روزانه", style = MaterialTheme.typography.titleMedium)
            Text("${sliderValue.toInt()} XP در روز", style = MaterialTheme.typography.bodyMedium)
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                onValueChangeFinished = { onGoalChange(sliderValue.toInt()) },
                valueRange = 20f..200f,
                steps = 8
            )
        }
    }
}

@Composable
private fun ThemeCard(currentMode: String, onThemeChange: (String) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("ظاهر برنامه", style = MaterialTheme.typography.titleMedium)
            androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("SYSTEM" to "پیش‌فرض سیستم", "LIGHT" to "روشن", "DARK" to "تیره").forEach { (mode, label) ->
                    FilterChip(
                        selected = currentMode == mode,
                        onClick = { onThemeChange(mode) },
                        label = { Text(label) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AchievementsSection(state: ProfileUiState) {
    Column {
        Text("نشان‌ها و دستاوردها", style = MaterialTheme.typography.titleLarge)
        androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().height(360.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.achievements, key = { it.id }) { achievement ->
                AchievementCard(
                    titleFa = achievement.titleFa,
                    descriptionFa = achievement.descriptionFa,
                    unlocked = state.unlockedAchievementIds.contains(achievement.id)
                )
            }
        }
    }
}
