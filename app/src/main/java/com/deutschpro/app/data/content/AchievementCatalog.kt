package com.deutschpro.app.data.content

import com.deutschpro.app.data.local.AchievementEntity
import com.deutschpro.app.data.model.AchievementCategory

/** Fixed set of badges/achievements available in the app. */
object AchievementCatalog {
    val all = listOf(
        AchievementEntity("ach_streak_3", AchievementCategory.STREAK, "شروع پرقدرت", "۳ روز پیاپی درس بخوانید.", "local_fire_department", 3),
        AchievementEntity("ach_streak_7", AchievementCategory.STREAK, "یک هفته پیوسته", "۷ روز پیاپی درس بخوانید.", "local_fire_department", 7),
        AchievementEntity("ach_streak_30", AchievementCategory.STREAK, "استاد پشتکار", "۳۰ روز پیاپی درس بخوانید.", "local_fire_department", 30),

        AchievementEntity("ach_xp_100", AchievementCategory.XP, "قدم اول", "۱۰۰ امتیاز XP کسب کنید.", "star", 100),
        AchievementEntity("ach_xp_500", AchievementCategory.XP, "یادگیرنده جدی", "۵۰۰ امتیاز XP کسب کنید.", "star", 500),
        AchievementEntity("ach_xp_2000", AchievementCategory.XP, "قهرمان زبان", "۲۰۰۰ امتیاز XP کسب کنید.", "star", 2000),

        AchievementEntity("ach_words_25", AchievementCategory.VOCABULARY, "واژه‌دان تازه‌کار", "۲۵ واژه جدید یاد بگیرید.", "menu_book", 25),
        AchievementEntity("ach_words_100", AchievementCategory.VOCABULARY, "گنجینه واژگان", "۱۰۰ واژه جدید یاد بگیرید.", "menu_book", 100),

        AchievementEntity("ach_lessons_5", AchievementCategory.LESSONS, "شروع مسیر", "۵ درس را کامل کنید.", "check_circle", 5),
        AchievementEntity("ach_lessons_20", AchievementCategory.LESSONS, "دانش‌آموز پرتلاش", "۲۰ درس را کامل کنید.", "check_circle", 20),

        AchievementEntity("ach_quiz_perfect_1", AchievementCategory.QUIZ, "امتیاز کامل", "در یک آزمون نمره ۱۰۰٪ بگیرید.", "military_tech", 1),
        AchievementEntity("ach_quiz_perfect_10", AchievementCategory.QUIZ, "استاد آزمون", "در ۱۰ آزمون نمره کامل بگیرید.", "military_tech", 10),

        AchievementEntity("ach_level_a1", AchievementCategory.LEVEL, "فارغ‌التحصیل A1", "سطح A1 را به پایان برسانید.", "emoji_events", 1),
        AchievementEntity("ach_level_a2", AchievementCategory.LEVEL, "فارغ‌التحصیل A2", "سطح A2 را به پایان برسانید.", "emoji_events", 2),
        AchievementEntity("ach_level_b1", AchievementCategory.LEVEL, "فارغ‌التحصیل B1", "سطح B1 را به پایان برسانید.", "emoji_events", 3),
        AchievementEntity("ach_level_b2", AchievementCategory.LEVEL, "فارغ‌التحصیل B2", "سطح B2 را به پایان برسانید و آلمانی را مسلط شوید!", "emoji_events", 4)
    )
}
