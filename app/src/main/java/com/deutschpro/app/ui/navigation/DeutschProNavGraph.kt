package com.deutschpro.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import com.deutschpro.app.ui.components.DeutschProBottomBar
import com.deutschpro.app.ui.screens.grammar.GrammarDetailScreen
import com.deutschpro.app.ui.screens.grammar.GrammarScreen
import com.deutschpro.app.ui.screens.learningpath.LearningPathScreen
import com.deutschpro.app.ui.screens.lesson.LessonScreen
import com.deutschpro.app.ui.screens.profile.ProfileScreen
import com.deutschpro.app.ui.screens.quiz.QuizScreen
import com.deutschpro.app.ui.screens.vocabulary.FlashcardScreen
import com.deutschpro.app.ui.screens.vocabulary.VocabularyScreen

@Composable
fun DeutschProNavGraph(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in Screen.bottomBarScreens.map { it.route }) {
                DeutschProBottomBar(currentRoute) { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(Screen.LearningPath.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.LearningPath.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.LearningPath.route) {
                LearningPathScreen(
                    onLessonClick = { lessonId -> navController.navigate(Screen.Lesson.routeFor(lessonId)) }
                )
            }

            composable(Screen.Vocabulary.route) {
                VocabularyScreen(
                    onOpenFlashcards = { levelCode -> navController.navigate(Screen.Flashcards.routeFor(levelCode)) }
                )
            }

            composable(Screen.Grammar.route) {
                GrammarScreen(
                    onTopicClick = { topicId -> navController.navigate(Screen.GrammarDetail.routeFor(topicId)) }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(Screen.Lesson.route) { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString(Screen.Lesson.ARG_LESSON_ID).orEmpty()
                LessonScreen(
                    lessonId = lessonId,
                    onBack = { navController.popBackStack() },
                    onStartQuiz = { id -> navController.navigate(Screen.Quiz.routeFor(id)) }
                )
            }

            composable(Screen.Quiz.route) { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString(Screen.Quiz.ARG_LESSON_ID).orEmpty()
                QuizScreen(
                    lessonId = lessonId,
                    onFinished = {
                        navController.popBackStack(Screen.LearningPath.route, inclusive = false)
                    },
                    onClose = { navController.popBackStack() }
                )
            }

            composable(Screen.Flashcards.route) { backStackEntry ->
                val levelCode = backStackEntry.arguments?.getString(Screen.Flashcards.ARG_LEVEL_CODE).orEmpty()
                FlashcardScreen(levelCode = levelCode, onBack = { navController.popBackStack() })
            }

            composable(Screen.GrammarDetail.route) { backStackEntry ->
                val topicId = backStackEntry.arguments?.getString(Screen.GrammarDetail.ARG_TOPIC_ID).orEmpty()
                GrammarDetailScreen(topicId = topicId, onBack = { navController.popBackStack() })
            }
        }
    }
}
