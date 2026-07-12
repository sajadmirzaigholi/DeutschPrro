package com.deutschpro.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deutschpro.app.data.model.LevelCode

/**
 * A thematic unit inside a level, e.g. "Unit 1: Greetings & Introductions".
 * A level (A1/A2/B1/B2) contains many units; units contain lessons.
 */
@Entity(tableName = "units")
data class UnitEntity(
    @PrimaryKey val id: String,
    val levelCode: LevelCode,
    val orderInLevel: Int,
    val titleFa: String,
    val titleDe: String,
    val descriptionFa: String,
    val iconName: String
)
