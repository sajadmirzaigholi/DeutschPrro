package com.deutschpro.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deutschpro.app.data.model.QuestionType

@Entity(tableName = "quiz_questions")
data class QuizQuestionEntity(
    @PrimaryKey val id: String,
    val lessonId: String,
    val skillTag: String,             // "VOCABULARY" | "GRAMMAR" | "READING" | ...
    val questionType: QuestionType,
    val questionTextFa: String,
    val questionTextDe: String = "",
    /** Pipe-separated list of options, e.g. "der Hund|die Katze|das Pferd|der Vogel" */
    val optionsRaw: String,
    val correctOptionIndex: Int,
    val explanationFa: String = ""
) {
    fun options(): List<String> = optionsRaw.split("|").map { it.trim() }
}
