package com.deutschpro.app.ui.navigation

/** All navigable destinations. Route strings double as NavHost routes. */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object LearningPath : Screen("learning_path")
    data object Vocabulary : Screen("vocabulary")
    data object Grammar : Screen("grammar")
    data object Profile : Screen("profile")

    data object Lesson : Screen("lesson/{lessonId}") {
        fun routeFor(lessonId: String) = "lesson/$lessonId"
        const val ARG_LESSON_ID = "lessonId"
    }

    data object Flashcards : Screen("flashcards/{levelCode}") {
        fun routeFor(levelCode: String) = "flashcards/$levelCode"
        const val ARG_LEVEL_CODE = "levelCode"
    }

    data object GrammarDetail : Screen("grammar_detail/{topicId}") {
        fun routeFor(topicId: String) = "grammar_detail/$topicId"
        const val ARG_TOPIC_ID = "topicId"
    }

    data object Quiz : Screen("quiz/{lessonId}") {
        fun routeFor(lessonId: String) = "quiz/$lessonId"
        const val ARG_LESSON_ID = "lessonId"
    }

    /** Bottom navigation bar destinations, in display order. */
    companion object {
        val bottomBarScreens = listOf(LearningPath, Vocabulary, Grammar, Profile)
    }
}
