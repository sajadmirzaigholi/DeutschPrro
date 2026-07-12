package com.deutschpro.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deutschpro.app.data.model.NodeType

/**
 * A single lesson (= one node on the learning-path map).
 * Each lesson bundles the six skills (vocabulary/grammar/reading/listening/
 * writing/speaking) that belong to it; the actual skill content lives in
 * dedicated tables (VocabularyEntity, GrammarTopicEntity, etc.) and is
 * linked back to the lesson via lessonId.
 */
@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey val id: String,
    val unitId: String,
    val orderInUnit: Int,
    val nodeType: NodeType,
    val titleFa: String,
    val titleDe: String,
    val xpReward: Int = 20,
    val readingTextDe: String = "",
    val readingTextFa: String = "",
    val listeningScript: String = "",
    val listeningPromptFa: String = "",
    val writingPromptFa: String = "",
    val writingExampleDe: String = "",
    val speakingPromptFa: String = "",
    val speakingExampleDe: String = ""
)
