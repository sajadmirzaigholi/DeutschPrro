package com.deutschpro.app.data.content

import com.deutschpro.app.data.local.LessonEntity
import com.deutschpro.app.data.local.UnitEntity
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.model.NodeType

/**
 * A2, B1 and B2 currently ship with their unit structure only (titles +
 * descriptions), each holding a single "coming soon" placeholder lesson.
 * This lets the learning-path map render the full A1→B2 journey end to
 * end today, while content authors fill in real lessons unit by unit
 * using [A1Unit1Content] / [A1Unit2Content] as the template to follow.
 * Nothing about the architecture changes when real content is added —
 * just insert more UnitEntity/LessonEntity/VocabularyEntity/etc. rows.
 */
object UpperLevelsPlaceholder {

    private fun comingSoonUnit(level: LevelCode, order: Int, titleFa: String, titleDe: String, descFa: String, icon: String) =
        UnitEntity(
            id = "${level.name.lowercase()}_u${order + 1}",
            levelCode = level,
            orderInLevel = order,
            titleFa = titleFa,
            titleDe = titleDe,
            descriptionFa = descFa,
            iconName = icon
        )

    private fun comingSoonLesson(unitId: String) = LessonEntity(
        id = "${unitId}_soon",
        unitId = unitId,
        orderInUnit = 0,
        nodeType = NodeType.LESSON,
        titleFa = "به‌زودی",
        titleDe = "Demnächst verfügbar",
        xpReward = 0
    )

    val a2Units = listOf(
        comingSoonUnit(LevelCode.A2, 0, "زندگی روزمره", "Alltag", "مکالمات و واژگان زندگی روزمره در سطح A2.", "calendar_today"),
        comingSoonUnit(LevelCode.A2, 1, "سفر و حمل‌ونقل", "Reisen und Verkehr", "خرید بلیط، پرسیدن آدرس و مکالمات سفر.", "flight")
    )

    val b1Units = listOf(
        comingSoonUnit(LevelCode.B1, 0, "کار و تحصیل", "Arbeit und Studium", "مکالمات محیط کار و دانشگاه در سطح B1.", "work"),
        comingSoonUnit(LevelCode.B1, 1, "رسانه و جامعه", "Medien und Gesellschaft", "بحث درباره اخبار، رسانه و موضوعات اجتماعی.", "newspaper")
    )

    val b2Units = listOf(
        comingSoonUnit(LevelCode.B2, 0, "مباحث انتزاعی", "Abstrakte Themen", "بیان نظر و استدلال درباره موضوعات پیچیده.", "psychology"),
        comingSoonUnit(LevelCode.B2, 1, "آمادگی آزمون", "Prüfungsvorbereitung", "تمرین‌های جامع برای آزمون‌های رسمی B2.", "school")
    )

    val allUnits = a2Units + b1Units + b2Units
    val allLessons = allUnits.map { comingSoonLesson(it.id) }
}
