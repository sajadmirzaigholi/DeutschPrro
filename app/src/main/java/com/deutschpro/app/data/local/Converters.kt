package com.deutschpro.app.data.local

import androidx.room.TypeConverter
import com.deutschpro.app.data.model.AchievementCategory
import com.deutschpro.app.data.model.LessonState
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.model.NodeType
import com.deutschpro.app.data.model.QuestionType

class Converters {
    @TypeConverter
    fun fromLevelCode(value: LevelCode): String = value.name
    @TypeConverter
    fun toLevelCode(value: String): LevelCode = LevelCode.valueOf(value)

    @TypeConverter
    fun fromNodeType(value: NodeType): String = value.name
    @TypeConverter
    fun toNodeType(value: String): NodeType = NodeType.valueOf(value)

    @TypeConverter
    fun fromLessonState(value: LessonState): String = value.name
    @TypeConverter
    fun toLessonState(value: String): LessonState = LessonState.valueOf(value)

    @TypeConverter
    fun fromQuestionType(value: QuestionType): String = value.name
    @TypeConverter
    fun toQuestionType(value: String): QuestionType = QuestionType.valueOf(value)

    @TypeConverter
    fun fromAchievementCategory(value: AchievementCategory): String = value.name
    @TypeConverter
    fun toAchievementCategory(value: String): AchievementCategory = AchievementCategory.valueOf(value)
}
