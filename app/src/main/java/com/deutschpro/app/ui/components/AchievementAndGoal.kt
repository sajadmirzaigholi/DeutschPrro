package com.deutschpro.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AchievementCard(
    titleFa: String,
    descriptionFa: String,
    unlocked: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (unlocked) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        if (unlocked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline,
                        CircleShape
                    )
            ) {
                Icon(Icons.Filled.EmojiEvents, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondary)
            }
            Spacer(Modifier.padding(4.dp))
            Text(titleFa, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
            Text(
                descriptionFa,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Circular ring showing today's XP progress toward the daily goal. */
@Composable
fun DailyGoalRing(todayXp: Int, goalXp: Int, modifier: Modifier = Modifier) {
    val progress = if (goalXp == 0) 0f else (todayXp.toFloat() / goalXp).coerceIn(0f, 1f)
    Box(contentAlignment = Alignment.Center, modifier = modifier.size(72.dp)) {
        CircularProgressIndicator(
            progress = { progress },
            strokeWidth = 6.dp,
            modifier = Modifier.size(72.dp)
        )
        Text("$todayXp/$goalXp", style = MaterialTheme.typography.labelMedium)
    }
}
