package com.deutschpro.app.data.model

/**
 * The four CEFR levels supported by DeutschPro.
 * Order matters: it defines the unlock sequence of the learning path.
 */
enum class LevelCode(val order: Int, val displayNameFa: String) {
    A1(0, "مبتدی (A1)"),
    A2(1, "مقدماتی (A2)"),
    B1(2, "متوسط (B1)"),
    B2(3, "پیشرفته (B2)")
}

/** The six pedagogical skill areas every lesson is built around. */
enum class SkillType(val displayNameFa: String) {
    VOCABULARY("واژگان"),
    GRAMMAR("گرامر"),
    READING("خواندن (Lesen)"),
    LISTENING("شنیدن (Hören)"),
    WRITING("نوشتن (Schreiben)"),
    SPEAKING("صحبت‌کردن (Sprechen)")
}

/** The kind of node shown on the visual learning path map. */
enum class NodeType {
    LESSON,
    PRACTICE,
    REVIEW,
    LEVEL_TEST
}

/** Lock state of a node/lesson on the learning path. */
enum class LessonState {
    LOCKED,
    UNLOCKED,
    IN_PROGRESS,
    COMPLETED
}

enum class QuestionType {
    MULTIPLE_CHOICE,
    FILL_IN_BLANK,
    MATCHING,
    TRUE_FALSE,
    TRANSLATE
}

enum class AchievementCategory {
    STREAK,
    XP,
    VOCABULARY,
    LESSONS,
    QUIZ,
    LEVEL
}
