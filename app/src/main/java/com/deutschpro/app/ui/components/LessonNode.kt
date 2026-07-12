package com.deutschpro.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deutschpro.app.data.model.LessonState
import com.deutschpro.app.data.model.NodeType
import com.deutschpro.app.ui.theme.StarFilled
import com.deutschpro.app.ui.theme.Success40

/**
 * A single circular node on the Duolingo-style learning path. Tapping a
 * locked node does nothing (visually communicated via reduced opacity +
 * lock icon); unlocked/completed nodes are clickable.
 */
@Composable
fun LessonNode(
    titleFa: String,
    nodeType: NodeType,
    state: LessonState,
    stars: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isClickable = state != LessonState.LOCKED
    val backgroundColor = when (state) {
        LessonState.COMPLETED -> Success40
        LessonState.LOCKED -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.primary
    }
    val iconColor = if (state == LessonState.LOCKED) MaterialTheme.colorScheme.onSurfaceVariant else Color.White

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .background(backgroundColor, CircleShape)
                .clickable(enabled = isClickable, onClick = onClick)
        ) {
            val icon = when {
                state == LessonState.LOCKED -> Icons.Filled.Lock
                state == LessonState.COMPLETED -> Icons.Filled.Check
                nodeType == NodeType.REVIEW -> Icons.Filled.RateReview
                nodeType == NodeType.LEVEL_TEST -> Icons.Filled.School
                else -> Icons.Filled.MenuBook
            }
            Icon(icon, contentDescription = titleFa, tint = iconColor, modifier = Modifier.size(28.dp))
        }
        if (state == LessonState.COMPLETED && stars > 0) {
            Text("★".repeat(stars), color = StarFilled, fontSize = MaterialTheme.typography.labelMedium.fontSize)
        }
        Text(
            text = titleFa,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.size(width = 76.dp, height = 32.dp)
        )
    }
}
