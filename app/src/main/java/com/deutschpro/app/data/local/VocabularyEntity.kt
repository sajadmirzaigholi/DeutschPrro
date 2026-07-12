package com.deutschpro.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deutschpro.app.data.model.LevelCode

@Entity(tableName = "vocabulary")
data class VocabularyEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val levelCode: LevelCode,
    val germanWord: String,
    val article: String = "",       // der / die / das / "" for non-nouns
    val persianTranslation: String,
    val exampleSentenceDe: String,
    val exampleSentenceFa: String,
    val topicCategory: String,      // e.g. "خانواده", "غذا", "زمان"
    val pronunciationHint: String = "" // simple phonetic hint in Persian script
)
