package com.deutschpro.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deutschpro.app.ui.theme.StreakFlame
import com.deutschpro.app.ui.theme.XpGold

@Composable
fun XpBadge(xp: Int, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(XpGold.copy(alpha = 0.15f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(Icons.Filled.Star, contentDescription = null, tint = XpGold, modifier = Modifier.padding(end = 2.dp))
        Spacer(Modifier.width(4.dp))
        Text("$xp XP", color = Color(0xFF8A5D06), fontSize = 13.sp, fontWeight = MaterialTheme.typography.labelLarge.fontWeight)
    }
}

@Composable
fun StreakBadge(days: Int, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(StreakFlame.copy(alpha = 0.15f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(Icons.Filled.LocalFireDepartment, contentDescription = null, tint = StreakFlame)
        Spacer(Modifier.width(4.dp))
        Text("$days روز", color = Color(0xFF9A3A1F), fontSize = 13.sp, fontWeight = MaterialTheme.typography.labelLarge.fontWeight)
    }
}
