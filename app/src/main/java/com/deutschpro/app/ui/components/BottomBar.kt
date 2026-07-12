package com.deutschpro.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.deutschpro.app.ui.navigation.Screen

private data class BottomBarItem(val screen: Screen, val label: String, val icon: ImageVector)

private val items = listOf(
    BottomBarItem(Screen.LearningPath, "مسیر یادگیری", Icons.Filled.Map),
    BottomBarItem(Screen.Vocabulary, "واژگان", Icons.Filled.MenuBook),
    BottomBarItem(Screen.Grammar, "گرامر", Icons.Filled.Rule),
    BottomBarItem(Screen.Profile, "پروفایل", Icons.Filled.Person)
)

@Composable
fun DeutschProBottomBar(currentRoute: String?, onNavigate: (Screen) -> Unit) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = { onNavigate(item.screen) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
