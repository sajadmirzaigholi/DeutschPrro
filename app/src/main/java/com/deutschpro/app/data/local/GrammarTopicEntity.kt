package com.deutschpro.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deutschpro.app.data.model.LevelCode

@Entity(tableName = "grammar_topics")
data class GrammarTopicEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val levelCode: LevelCode,
    val titleFa: String,
    val titleDe: String,
    /** Simple, plain-language explanation written in Persian. */
    val explanationFa: String,
    /** Newline separated German example sentences. */
    val examplesDe: String,
    /** Newline separated Persian translations, aligned with examplesDe. */
    val examplesFa: String,
    /** Common exceptions / irregular forms explained in Persian. */
    val exceptionsFa: String = "",
    val orderInLesson: Int = 0
)
