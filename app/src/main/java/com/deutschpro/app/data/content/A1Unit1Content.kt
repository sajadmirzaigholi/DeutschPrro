package com.deutschpro.app.data.content

import com.deutschpro.app.data.local.GrammarTopicEntity
import com.deutschpro.app.data.local.LessonEntity
import com.deutschpro.app.data.local.QuizQuestionEntity
import com.deutschpro.app.data.local.UnitEntity
import com.deutschpro.app.data.local.VocabularyEntity
import com.deutschpro.app.data.model.LevelCode
import com.deutschpro.app.data.model.NodeType
import com.deutschpro.app.data.model.QuestionType

/**
 * Fully worked example content for A1, Unit 1 ("سلام! - Begrüßung und Vorstellung").
 * This is the reference implementation other units/levels should follow:
 * every lesson ships vocabulary, (optionally) a grammar topic, a short
 * reading text, a listening script + prompt, a writing prompt, a speaking
 * prompt, and a 5-question quiz.
 */
object A1Unit1Content {

    const val UNIT_ID = "a1_u1"

    val unit = UnitEntity(
        id = UNIT_ID,
        levelCode = LevelCode.A1,
        orderInLevel = 0,
        titleFa = "سلام! آشنایی و احوال‌پرسی",
        titleDe = "Begrüßung und Vorstellung",
        descriptionFa = "در این بخش یاد می‌گیرید چطور به آلمانی سلام کنید، خودتان را معرفی کنید و احوال‌پرسی ساده انجام دهید.",
        iconName = "waving_hand"
    )

    // ---- Lessons -----------------------------------------------------
    val lesson1 = LessonEntity(
        id = "a1_u1_l1",
        unitId = UNIT_ID,
        orderInUnit = 0,
        nodeType = NodeType.LESSON,
        titleFa = "سلام و خداحافظی",
        titleDe = "Hallo und Tschüss",
        xpReward = 20,
        readingTextDe = "Anna: Hallo! Wie geht's?\nMax: Danke, gut! Und dir?\nAnna: Auch gut, danke.\nMax: Tschüss, bis morgen!\nAnna: Tschüss!",
        readingTextFa = "آنا: سلام! حالت چطوره؟\nمکس: ممنون، خوبم! تو چطوری؟\nآنا: منم خوبم، ممنون.\nمکس: خداحافظ، تا فردا!\nآنا: خداحافظ!",
        listeningScript = "Hallo! Ich heiße Anna. Wie geht's dir? Guten Morgen und einen schönen Tag!",
        listeningPromptFa = "به فایل صوتی گوش دهید و بگویید آنا در ابتدای مکالمه چه می‌گوید.",
        writingPromptFa = "یک پیام کوتاه بنویسید که در آن به یک دوست آلمانی سلام می‌کنید و می‌پرسید حالش چطور است.",
        writingExampleDe = "Hallo Lisa! Wie geht's? Ich heiße Sara und komme aus dem Iran.",
        speakingPromptFa = "با صدای بلند بگویید: «سلام، حالت چطوره؟» و «ممنون، خوبم» به آلمانی.",
        speakingExampleDe = "Hallo! Wie geht's? — Danke, gut!"
    )

    val lesson2 = LessonEntity(
        id = "a1_u1_l2",
        unitId = UNIT_ID,
        orderInUnit = 1,
        nodeType = NodeType.LESSON,
        titleFa = "معرفی خود",
        titleDe = "Sich vorstellen",
        xpReward = 20,
        readingTextDe = "Ich heiße Sara. Ich komme aus dem Iran. Ich wohne in Berlin. Ich spreche Persisch und ein bisschen Deutsch.",
        readingTextFa = "اسم من سارا است. من اهل ایران هستم. در برلین زندگی می‌کنم. فارسی و کمی آلمانی صحبت می‌کنم.",
        listeningScript = "Guten Tag! Ich heiße Tom. Ich komme aus Deutschland und wohne in München. Ich spreche Deutsch und Englisch.",
        listeningPromptFa = "گوش دهید و بگویید تام اهل کجاست و در کدام شهر زندگی می‌کند.",
        writingPromptFa = "چند جمله درباره خودتان بنویسید: اسم، کشور، شهر و زبانی که صحبت می‌کنید.",
        writingExampleDe = "Ich heiße Ali. Ich komme aus dem Iran und wohne in Hamburg.",
        speakingPromptFa = "با صدای بلند خودتان را به آلمانی معرفی کنید (اسم، کشور، شهر).",
        speakingExampleDe = "Ich heiße ... Ich komme aus ... Ich wohne in ..."
    )

    val lesson3 = LessonEntity(
        id = "a1_u1_l3",
        unitId = UNIT_ID,
        orderInUnit = 2,
        nodeType = NodeType.LESSON,
        titleFa = "گرامر: فعل sein",
        titleDe = "Grammatik: Verb 'sein'",
        xpReward = 25,
        readingTextDe = "Ich bin müde. Du bist nett. Er ist mein Freund. Wir sind Studenten.",
        readingTextFa = "من خسته‌ام. تو مهربانی. او (مذکر) دوست من است. ما دانشجو هستیم.",
        listeningScript = "Ich bin Anna. Du bist mein Freund. Er ist Lehrer. Wir sind in Berlin.",
        listeningPromptFa = "گوش دهید و صرف فعل sein را که می‌شنوید یادداشت کنید.",
        writingPromptFa = "با فعل sein چهار جمله درباره خودتان و خانواده‌تان بنویسید.",
        writingExampleDe = "Ich bin Student. Meine Schwester ist Lehrerin.",
        speakingPromptFa = "صرف فعل sein را برای من/تو/او با صدای بلند تمرین کنید.",
        speakingExampleDe = "ich bin, du bist, er/sie/es ist, wir sind, ihr seid, sie/Sie sind"
    )

    val lesson4 = LessonEntity(
        id = "a1_u1_l4",
        unitId = UNIT_ID,
        orderInUnit = 3,
        nodeType = NodeType.LESSON,
        titleFa = "اعداد ۰ تا ۲۰",
        titleDe = "Zahlen 0–20",
        xpReward = 20,
        readingTextDe = "Null, eins, zwei, drei, vier, fünf, sechs, sieben, acht, neun, zehn.",
        readingTextFa = "صفر، یک، دو، سه، چهار، پنج، شش، هفت، هشت، نه، ده.",
        listeningScript = "Eins, zwei, drei, vier, fünf, sechs, sieben, acht, neun, zehn, elf, zwölf.",
        listeningPromptFa = "گوش دهید و اعدادی که می‌شنوید را با رقم بنویسید.",
        writingPromptFa = "شماره تلفن ساختگی خود را با کلمات آلمانی بنویسید.",
        writingExampleDe = "Meine Nummer ist: null eins fünf, zwei drei vier.",
        speakingPromptFa = "اعداد ۰ تا ۲۰ را با صدای بلند به ترتیب بشمارید.",
        speakingExampleDe = "eins, zwei, drei, vier, fünf ..."
    )

    val lesson5Review = LessonEntity(
        id = "a1_u1_review",
        unitId = UNIT_ID,
        orderInUnit = 4,
        nodeType = NodeType.REVIEW,
        titleFa = "مرور واحد ۱",
        titleDe = "Wiederholung: Einheit 1",
        xpReward = 30
    )

    // ---- Vocabulary ----------------------------------------------------
    val vocabulary = listOf(
        VocabularyEntity("a1_v_hallo", lesson1.id, LevelCode.A1, "Hallo", "", "سلام", "Hallo! Wie geht's?", "سلام! حالت چطوره؟", "احوال‌پرسی", "هالو"),
        VocabularyEntity("a1_v_tschuess", lesson1.id, LevelCode.A1, "Tschüss", "", "خداحافظ", "Tschüss, bis morgen!", "خداحافظ، تا فردا!", "احوال‌پرسی", "چوس"),
        VocabularyEntity("a1_v_gutenmorgen", lesson1.id, LevelCode.A1, "Guten Morgen", "", "صبح بخیر", "Guten Morgen, Anna!", "صبح بخیر، آنا!", "احوال‌پرسی", "گوتن مورگن"),
        VocabularyEntity("a1_v_gutentag", lesson1.id, LevelCode.A1, "Guten Tag", "", "روز بخیر", "Guten Tag, Herr Müller.", "روز بخیر آقای مولر.", "احوال‌پرسی", "گوتن تاگ"),
        VocabularyEntity("a1_v_gutenabend", lesson1.id, LevelCode.A1, "Guten Abend", "", "عصر بخیر", "Guten Abend, wie geht's?", "عصر بخیر، حالت چطوره؟", "احوال‌پرسی", "گوتن آبند"),
        VocabularyEntity("a1_v_wiegehts", lesson1.id, LevelCode.A1, "Wie geht's?", "", "حالت چطوره؟", "Hallo, wie geht's dir?", "سلام، حالت چطوره؟", "احوال‌پرسی", "وی گتس"),
        VocabularyEntity("a1_v_danke", lesson1.id, LevelCode.A1, "Danke", "", "ممنون", "Danke, gut!", "ممنون، خوبم!", "احوال‌پرسی", "دانکه"),
        VocabularyEntity("a1_v_bitte", lesson1.id, LevelCode.A1, "Bitte", "", "خواهش می‌کنم / بفرمایید", "Bitte schön!", "بفرمایید!", "احوال‌پرسی", "بیته"),
        VocabularyEntity("a1_v_ja", lesson1.id, LevelCode.A1, "Ja", "", "بله", "Ja, das stimmt.", "بله، درسته.", "احوال‌پرسی", "یا"),
        VocabularyEntity("a1_v_nein", lesson1.id, LevelCode.A1, "Nein", "", "نه", "Nein, danke.", "نه، ممنون.", "احوال‌پرسی", "ناین"),

        VocabularyEntity("a1_v_ich", lesson2.id, LevelCode.A1, "ich", "", "من", "Ich heiße Sara.", "اسم من ساراست.", "ضمایر و معرفی", "ایش"),
        VocabularyEntity("a1_v_du", lesson2.id, LevelCode.A1, "du", "", "تو", "Wie heißt du?", "اسم تو چیه؟", "ضمایر و معرفی", "دو"),
        VocabularyEntity("a1_v_heissen", lesson2.id, LevelCode.A1, "heißen", "", "نامیده شدن / نام داشتن", "Ich heiße Ali.", "اسم من علی است.", "ضمایر و معرفی", "هایسن"),
        VocabularyEntity("a1_v_name", lesson2.id, LevelCode.A1, "Name", "der", "اسم", "Mein Name ist Sara.", "اسم من سارا است.", "ضمایر و معرفی", "نامه"),
        VocabularyEntity("a1_v_kommenaus", lesson2.id, LevelCode.A1, "kommen aus", "", "اهل ... بودن", "Ich komme aus dem Iran.", "من اهل ایران هستم.", "ضمایر و معرفی", "کومن آاوس"),
        VocabularyEntity("a1_v_land", lesson2.id, LevelCode.A1, "Land", "das", "کشور", "Welches Land ist das?", "این کدام کشور است؟", "ضمایر و معرفی", "لاند"),
        VocabularyEntity("a1_v_wohnen", lesson2.id, LevelCode.A1, "wohnen", "", "زندگی کردن (سکونت)", "Ich wohne in Berlin.", "من در برلین زندگی می‌کنم.", "ضمایر و معرفی", "وونن"),
        VocabularyEntity("a1_v_stadt", lesson2.id, LevelCode.A1, "Stadt", "die", "شهر", "Berlin ist eine große Stadt.", "برلین شهر بزرگی است.", "ضمایر و معرفی", "اشتات"),
        VocabularyEntity("a1_v_sprechen", lesson2.id, LevelCode.A1, "sprechen", "", "صحبت کردن", "Ich spreche Deutsch.", "من آلمانی صحبت می‌کنم.", "ضمایر و معرفی", "اشپرشن"),
        VocabularyEntity("a1_v_sprache", lesson2.id, LevelCode.A1, "Sprache", "die", "زبان", "Deutsch ist meine zweite Sprache.", "آلمانی زبان دوم من است.", "ضمایر و معرفی", "اشپراخه"),

        VocabularyEntity("a1_v_null", lesson4.id, LevelCode.A1, "null", "", "صفر", "Meine Zimmernummer ist null eins.", "شماره اتاق من صفر یک است.", "اعداد", "نول"),
        VocabularyEntity("a1_v_eins", lesson4.id, LevelCode.A1, "eins", "", "یک", "Ich habe eins Bruder.", "من یک برادر دارم.", "اعداد", "آینس"),
        VocabularyEntity("a1_v_zwei", lesson4.id, LevelCode.A1, "zwei", "", "دو", "Zwei Kaffee, bitte.", "دو قهوه لطفاً.", "اعداد", "تسوای"),
        VocabularyEntity("a1_v_drei", lesson4.id, LevelCode.A1, "drei", "", "سه", "Drei Bücher liegen hier.", "سه کتاب اینجاست.", "اعداد", "دِرای"),
        VocabularyEntity("a1_v_zehn", lesson4.id, LevelCode.A1, "zehn", "", "ده", "Zehn Minuten, bitte.", "ده دقیقه لطفاً.", "اعداد", "تسن")
    )

    // ---- Grammar ---------------------------------------------------
    val grammarTopics = listOf(
        GrammarTopicEntity(
            id = "a1_g_sein",
            lessonId = lesson3.id,
            levelCode = LevelCode.A1,
            titleFa = "صرف فعل sein (بودن)",
            titleDe = "Konjugation von 'sein'",
            explanationFa = "فعل sein به معنای «بودن» است و یکی از پرکاربردترین افعال آلمانی است. برخلاف بیشتر افعال، sein یک فعل بی‌قاعده (unregelmäßig) است و باید صرف آن را کامل حفظ کنید. این فعل برای بیان هویت، احساس و ویژگی به کار می‌رود.",
            examplesDe = "Ich bin müde.\nDu bist nett.\nEr/Sie/Es ist zu Hause.\nWir sind Freunde.\nIhr seid pünktlich.\nSie/sie sind Studenten.",
            examplesFa = "من خسته‌ام.\nتو مهربانی.\nاو در خانه است.\nما دوست هستیم.\nشما وقت‌شناس هستید.\nآن‌ها/شما (رسمی) دانشجو هستند.",
            exceptionsFa = "توجه: sein هیچ الگوی منظمی از فعل‌های -en ندارد، پس باید جداگانه حفظ شود. همچنین «Sie» با حرف بزرگ برای خطاب رسمی و «sie» با حرف کوچک برای جمع سوم‌شخص استفاده می‌شود، ولی هر دو با «sind» صرف می‌شوند.",
            orderInLesson = 0
        )
    )

    // ---- Quiz --------------------------------------------------------
    val quizQuestions = listOf(
        QuizQuestionEntity("a1_q_l1_1", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "کدام گزینه معنی «Tschüss» است؟", "", "سلام|خداحافظ|ممنون|بله", 1),
        QuizQuestionEntity("a1_q_l1_2", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«روز بخیر» به آلمانی چه می‌شود؟", "", "Guten Morgen|Guten Abend|Guten Tag|Tschüss", 2),
        QuizQuestionEntity("a1_q_l1_3", lesson1.id, "READING", QuestionType.TRUE_FALSE,
            "در متن، مکس به آنا می‌گوید حالش خوب است.", "", "درست|نادرست", 0),
        QuizQuestionEntity("a1_q_l1_4", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "کدام کلمه یعنی «بله»؟", "", "Nein|Ja|Bitte|Danke", 1),
        QuizQuestionEntity("a1_q_l1_5", lesson1.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«ممنون» به آلمانی؟", "", "Bitte|Hallo|Danke|Tschüss", 2),

        QuizQuestionEntity("a1_q_l2_1", lesson2.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«heißen» یعنی چه؟", "", "زندگی کردن|نام داشتن|صحبت کردن|آمدن", 1),
        QuizQuestionEntity("a1_q_l2_2", lesson2.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "جای خالی را کامل کنید: Ich ___ aus dem Iran.", "", "komme|kommst|kommt|kommen", 0),
        QuizQuestionEntity("a1_q_l2_3", lesson2.id, "READING", QuestionType.TRUE_FALSE,
            "سارا در برلین زندگی می‌کند.", "", "درست|نادرست", 0),
        QuizQuestionEntity("a1_q_l2_4", lesson2.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "کلمه «Sprache» یعنی؟", "", "شهر|کشور|زبان|اسم", 2),
        QuizQuestionEntity("a1_q_l2_5", lesson2.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«wohnen» به معنای چیست؟", "", "صحبت کردن|زندگی‌کردن(سکونت)|آمدن|نام داشتن", 1),

        QuizQuestionEntity("a1_q_l3_1", lesson3.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "جای خالی را با شکل درست sein پر کنید: Ich ___ müde.", "", "bin|bist|ist|sind", 0),
        QuizQuestionEntity("a1_q_l3_2", lesson3.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "Du ___ nett.", "", "bin|bist|ist|seid", 1),
        QuizQuestionEntity("a1_q_l3_3", lesson3.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "Wir ___ Freunde.", "", "bin|bist|sind|seid", 2),
        QuizQuestionEntity("a1_q_l3_4", lesson3.id, "GRAMMAR", QuestionType.FILL_IN_BLANK,
            "Er ___ Lehrer.", "", "bin|ist|sind|seid", 1),
        QuizQuestionEntity("a1_q_l3_5", lesson3.id, "GRAMMAR", QuestionType.MULTIPLE_CHOICE,
            "فعل sein چه نوع فعلی است؟", "", "باقاعده|بی‌قاعده|وجهی|کمکی زمان گذشته فقط", 1),

        QuizQuestionEntity("a1_q_l4_1", lesson4.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "عدد «fünf» یعنی چند؟", "", "۳|۴|۵|۶", 2),
        QuizQuestionEntity("a1_q_l4_2", lesson4.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "عدد ۱۰ به آلمانی؟", "", "neun|zehn|elf|zwölf", 1),
        QuizQuestionEntity("a1_q_l4_3", lesson4.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«zwei» یعنی چند؟", "", "۱|۲|۳|۴", 1),
        QuizQuestionEntity("a1_q_l4_4", lesson4.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "کدام گزینه «صفر» است؟", "", "eins|null|zehn|drei", 1),
        QuizQuestionEntity("a1_q_l4_5", lesson4.id, "VOCABULARY", QuestionType.MULTIPLE_CHOICE,
            "«drei» یعنی چند؟", "", "۲|۳|۴|۵", 1)
    )

    val allLessons = listOf(lesson1, lesson2, lesson3, lesson4, lesson5Review)
}
