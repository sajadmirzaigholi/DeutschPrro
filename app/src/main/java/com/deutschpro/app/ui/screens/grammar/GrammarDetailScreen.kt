package com.deutschpro.app.ui.screens.grammar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deutschpro.app.DeutschProApplication
import com.deutschpro.app.data.local.GrammarTopicEntity

@Composable
fun GrammarDetailScreen(topicId: String, onBack: () -> Unit) {
    val context = LocalContext.current
    var topic by remember { mutableStateOf<GrammarTopicEntity?>(null) }

    LaunchedEffect(topicId) {
        val app = context.applicationContext as DeutschProApplication
        topic = app.grammarRepository.getTopic(topicId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topic?.titleFa ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowForward, contentDescription = "بازگشت") }
                }
            )
        }
    ) { padding ->
        val current = topic
        if (current == null) {
            Box(Modifier.fillMaxSize().padding(padding)) {
                Text("در حال بارگذاری...", modifier = Modifier.align(Alignment.Center))
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(current.titleDe, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)

            SectionCard(title = "توضیح ساده") {
                Text(current.explanationFa, style = MaterialTheme.typography.bodyLarge)
            }

            SectionCard(title = "مثال‌ها") {
                current.examplesDe.lines().zip(current.examplesFa.lines()).forEach { (de, fa) ->
                    Column(modifier = Modifier.padding(bottom = 10.dp)) {
                        Text(de, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                        Text(fa, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            if (current.exceptionsFa.isNotBlank()) {
                SectionCard(title = "نکات و استثناها") {
                    Text(current.exceptionsFa, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
            androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 8.dp))
            content()
        }
    }
}
