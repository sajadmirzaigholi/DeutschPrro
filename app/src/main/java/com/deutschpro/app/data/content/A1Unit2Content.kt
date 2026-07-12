package com.deutschpro.app.data.content

import com.deutschpro.app.data.local.GrammarTopicEntity
import com.deutschpro.app.data.local.LessonEntity
import com.deutschpro.app.data.local.QuizQuestionEntity
import com.deutschpro.app.data.local.UnitEntity
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.model.NodeType
import com.deutschpro.app.data.model.QuestionType

/** A1, Unit 2 — "Familie und Personen" (Family & People). */
object A1Unit2Content {

    const val UNIT_ID = "a1_u2"

    val unit = UnitEntity(
        id = UNIT_ID,
        levelCode = LevelCode.A1,
        orderInLevel = 1,
        titleFa = "خانواده و افراد",
        titleDe = "Familie und Personen",
        descriptionFa = "اعضای خانواده، فعل haben و صفات ساده برای توصیف افراد را یاد بگیرید.",
        iconName = "family_restroom"
    )

    val lesson1 = LessonEntity(
        id = "a1_u2_l1",
        unitId = UNIT_ID,
        orderInUnit = 0,
        nodeType = NodeType.LESSON,
        titleFa = "اعضای خانواده",
        titleDe = "Die Familie",
        xpReward = 20,
        readingTextDe = "Das ist meine Familie. Mein Vater heißt Peter. Meine Mutter heißt Julia. Ich habe eine Schwester und einen Bruder.",
        readingTextFa = "این خانواده من است. اسم پدرم پیتر است. اسم مادرم یولیا است. من یک خواهر و یک برادر دارم.",
        listeningScript = "Meine Familie ist klein. Ich habe eine Mutter, einen Vater und einen Bruder.",
        listeningPromptFa = "گوش دهید و اعضای خانواده گوینده را نام ببرید.",
        writingPromptFa = "درباره اعضای خانواده خودتان بنویسید (والدین، خواهر و برادر).",
        writingExampleDe = "Meine Mutter heißt Fatemeh. Ich habe zwei Brüder.",
        speakingPromptFa = "با صدای بلند بگویید در خانواده‌تان چند نفر هستید.",
        speakingExampleDe = "Meine Familie hat vier Personen."
    )

    val lesson2 = LessonEntity(
        id = "a1_u2_l2",
        unitId = UNIT_ID,
        orderInUnit = 1,
        nodeType = NodeType.LESSON,
        titleFa = "گرامر: فعل haben",
        titleDe = "Grammatik: Verb 'haben'",
        xpReward = 25,
        readingTextDe = "Ich habe einen Bruder. Du hast eine Schwester. Er hat zwei Kinder. Wir haben ein Auto.",
        readingTextFa = "من یک برادر دارم. تو یک خواهر داری. او دو فرزند دارد. ما یک ماشین داریم.",
        listeningScript = "Ich habe ein Buch. Du hast einen Hund. Wir haben Zeit.",
        listeningPromptFa = "گوش دهید و صرف فعل haben را که می‌شنوید تشخیص دهید.",
        writingPromptFa = "با فعل haben چهار جمله درباره چیزهایی که دارید بنویسید.",
        writingExampleDe = "Ich habe eine Katze. Wir haben ein Haus.",
        speakingPromptFa = "صرف فعل haben را برای ich/du/er با صدای بلند تمرین کنید.",
        speakingExampleDe = "ich habe, du hast, er/sie/es hat, wir haben, ihr habt, sie/Sie haben"
    )

    val lesson3 = LessonEntity(
        id = "a1_u2_l3",
        unitId = UNIT_ID,
        orderInUnit = 2,
        nodeType = NodeType.LESSON,
        titleFa = "توصیف افراد",
        titleDe = "Personen beschreiben",
        xpReward = 20,
        readingTextDe = "Mein Bruder ist groß und freundlich. Meine Schwester ist klein und lustig.",
        readingTextFa = "برادرم قدبلند و مهربان است. خواهرم کوتاه‌قد و بامزه است.",
        listeningScript = "Meine Mutter ist nett und klug. Mein Vater ist groß und ruhig.",
        listeningPromptFa = "گوش دهید و صفاتی که برای توصیف پدر و مادر استفاده شده را بنویسید.",
        writingPromptFa = "با سه صفت، یکی از اعضای خانواده خود را توصیف کنید.",
        writingExampleDe = "Mein Vater ist groß, freundlich und klug.",
        speakingPromptFa = "یکی از دوستانتان را با صدای بلند به آلمانی توصیف کنید.",
        speakingExampleDe = "Mein Freund ist nett und lustig."
    )

    val lessonReview = LessonEntity(
        id = "a1_u2_review",
        unitId = UNIT_ID,
        orderInUnit = 3,
        nodeType = NodeType.REVIEW,
        titleFa = "مرور واحد ۲",
        titleDe = "Wiederholung: Einheit 2",
        xpReward = 30
    )

    val vocabulary = listOf(
        VocabularyEntity("a1_v_vater", lesson1.id, LevelCode.A1, "Vater", "der", "پدر", "Mein Vater ist Lehrer.", "پدرم معلم است.", "خانواده", "فاتر"),
        VocabularyEntity("a1_v_mutter", lesson1.id, LevelCode.A1, "Mutter", "die", "مادر", "Meine Mutter kocht gern.", "مادرم آشپزی را دوست دارد.", "خانواده", "موتر"),
        VocabularyEntity("a1_v_bruder", lesson1.id, LevelCode.A1, "Bruder", "der", "برادر", "Ich habe einen Bruder.", "من یک برادر دارم.", "خانواده", "برودر"),
        VocabularyEntity("a1_v_schwester", lesson1.id, LevelCode.A1, "Schwester", "die", "خواهر", "Meine Schwester ist nett.", "خواهرم مهربان است.", "خانواده", "شوستر"),
        VocabularyEntity("a1_v_kind", lesson1.id, LevelCode.A1, "Kind", "das", "فرزند/بچه", "Das Kind spielt.", "بچه بازی می‌کند.", "خانواده", "کیند"),
        VocabularyEntity("a1_v_eltern", lesson1.id, LevelCode.A1, "Eltern", "die", "والدین", "Meine Eltern wohnen in Teheran.", "والدینم در تهران زندگی می‌کنند.", "خانواده", "اِلترن"),
        VocabularyEntity("a1_v_familie", lesson1.id, LevelCode.A1, "Familie", "die", "خانواده", "Ich liebe meine Familie.", "من خانواده‌ام را دوست دارم.", "خانواده", "فامیلیه"),

        VocabularyEntity("a1_v_haben", lesson2.id, LevelCode.A1, "haben", "", "داشتن", "Ich habe Zeit.", "من وقت دارم.", "خانواده", "هابن"),
        VocabularyEntity("a1_v_auto", lesson2.id, LevelCode.A1, "Auto", "das", "ماشین", "Wir haben ein Auto.", "ما یک ماشین داریم.", "خانواده", "آاوتو"),
        VocabularyEntity("a1_v_hund", lesson2.id, LevelCode.A1, "Hund", "der", "سگ", "Du hast einen Hund.", "تو یک سگ داری.", "خانواده", "هوند"),

        VocabularyEntity("a1_v_gross", lesson3.id, LevelCode.A1, "groß", "", "بزرگ / قدبلند", "Mein Bruder ist groß.", "برادرم قدبلند است.", "صفات", "گروس"),
        VocabularyEntity("a1_v_klein", lesson3.id, LevelCode.A1, "klein", "", "کوچک", "Das Kind ist klein.", "بچه کوچک است.", "صفات", "کلاین"),
        VocabularyEntity("a1_v_freundlich", lesson3.id, LevelCode.A1, "freundlich", "", "مهربان/دوستانه", "Sie ist sehr freundlich.", "او خیلی مهربان است.", "صفات", "فروینتلیش"),
        VocabularyEntity("a1_v_lustig", lesson3.id, LevelCode.A1, "lustig", "", "بامزه", "Mein Freund ist lustig.", "دوستم بامزه است.", "صفات", "لوستیگ")
    )

    val grammarTopics = listOf(
        GrammarTopicEntity(
            id = "a1_g_haben",
            lessonId = lesson2.id,
            levelCode = LevelCode.A1,
            titleFa = "صرف فعل haben (داشتن)",
            titleDe = "Konjugation von 'haben'",
            explanationFa = "فعل haben به معنای «داشتن» است و مانند sein یک فعل بی‌قاعده و بسیار پرکاربرد است. از این فعل برای بیان مالکیت، اعضای خانواده و بسیاری از عبارات روزمره استفاده می‌شود.",
            examplesDe = "Ich habe einen Bruder.\nDu hast eine Schwester.\nEr/Sie/Es hat ein Kind.\nWir haben Zeit.\nIhr habt ein Auto.\nSie/sie haben Kinder.",
            examplesFa = "من یک برادر دارم.\nتو یک خواهر داری.\nاو یک فرزند دارد.\nما وقت داریم.\nشما یک ماشین دارید.\nآن‌ها/شما (رسمی) فرزند دارند.",
            exceptionsFa = "توجه: در سوم‌شخص مفرد (er/sie/es) فعل به شکل «hat» می‌آید نه «habt» — این اشتباه رایج زبان‌آموزان است. «ihr habt» فقط برای جمع دوم‌شخص است.",
            orderInLesson = 0
        )
    )

    val quizQuestions = listOf(
        QuizQuestionEntity("a1_q2_l1_1", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«Schwester» یعنی چه؟", "", "برادر|خواهر|مادر|پدر", 1),
        QuizQuestionEntity("a1_q2_l1_2", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "حرف تعریف درست برای «Vater» کدام است؟", "", "die|das|der|-", 2),
        QuizQuestionEntity("a1_q2_l1_3", lesson1.id, "READING", QuestionType.TRUE_FALSE,
            "گوینده متن یک خواهر و یک برادر دارد.", "", "درست|نادرست", 0),
        QuizQuestionEntity("a1_q2_l1_4", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«Eltern» به چه معناست؟", "", "فرزندان|والدین|خواهر و برادر|خانواده", 1),
        QuizQuestionEntity("a1_q2_l1_5", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "حرف تعریف درست برای «Mutter» کدام است؟", "", "der|die|das|den", 1),

        QuizQuestionEntity("a1_q2_l2_1", lesson2.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "Ich ___ einen Bruder.", "", "habe|hast|hat|haben", 0),
        QuizQuestionEntity("a1_q2_l2_2", lesson2.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "Du ___ eine Schwester.", "", "habe|hast|hat|habt", 1),
        QuizQuestionEntity("a1_q2_l2_3", lesson2.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "Er ___ ein Kind.", "", "habe|hast|hat|haben", 2),
        QuizQuestionEntity("a1_q2_l2_4", lesson2.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "Wir ___ ein Auto.", "", "habe|hat|haben|habt", 2),
        QuizQuestionEntity("a1_q2_l2_5", lesson2.id, "GRAMMAR", QuestionType.MULTIPLE_CHOICE,
            "فعل haben از نظر صرف چگونه است؟", "", "کاملاً باقاعده|بی‌قاعده|فقط در گذشته کاربرد دارد|فقط رسمی است", 1),

        QuizQuestionEntity("a1_q2_l3_1", lesson3.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«groß» یعنی چه؟", "", "کوچک|بزرگ/قدبلند|بامزه|مهربان", 1),
        QuizQuestionEntity("a1_q2_l3_2", lesson3.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«lustig» یعنی چه؟", "", "بامزه|غمگین|جدی|بزرگ", 0),
        QuizQuestionEntity("a1_q2_l3_3", lesson3.id, "READING", QuestionType.TRUE_FALSE,
            "در متن، خواهر راوی قدبلند توصیف شده است.", "", "درست|نادرست", 1),
        QuizQuestionEntity("a1_q2_l3_4", lesson3.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«freundlich» به چه معناست؟", "", "بامزه|مهربان|کوچک|بزرگ", 1),
        QuizQuestionEntity("a1_q2_l3_5", lesson3.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«klein» یعنی چه؟", "", "بزرگ|کوچک|مهربان|بامزه", 1)
    )

    val allLessons = listOf(lesson1, lesson2, lesson3, lessonReview)
}
