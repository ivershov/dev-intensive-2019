package ru.skillbranch.devintensive.models

import android.graphics.Color
import android.os.Bundle

private const val KEY_STATUS = "status"
private const val KEY_QUESTION = "question"

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus() = values()[(ordinal + 1) % values().size]

        fun color() = Color.rgb(color.first, color.second, color.third)
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun validate(answer: String) = answer.firstOrNull()?.isUpperCase() ?: false
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun validate(answer: String) = answer.firstOrNull()?.isLowerCase() ?: false
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun validate(answer: String) = answer.contains(Regex("\\d")).not()
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun validate(answer: String) = answer.contains(Regex("^[0-9]*$"))
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun validate(answer: String) = answer.contains(Regex("^[0-9]{7}$"))
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun validate(answer: String) = true
        };

        fun nextQuestion() = values()[(ordinal + 1) % values().size]

        abstract fun validate(answer: String): Boolean

        fun getValidationErrorText(): String? {
            return when (this) {
                NAME -> "Имя должно начинаться с заглавной буквы"
                PROFESSION -> "Профессия должна начинаться со строчной буквы"
                MATERIAL -> "Материал не должен содержать цифр"
                BDAY -> "Год моего рождения должен содержать только цифры"
                SERIAL -> "Серийный номер содержит только цифры, и их 7"
                else -> null
            }
        }
    }

    fun askQuestion(): String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if(question == Question.IDLE) {
            return question.question to status.color
        }

        val src = answer.trim()
        if (!question.validate(src)) {
            return question.getValidationErrorText() + "\n" + question.question to status.color
        }

        val responseText: String

        responseText = when {
            question.answers.contains(src.toLowerCase()) -> {
                question = question.nextQuestion()
                "Отлично - ты справился"
            }
            status == Status.CRITICAL -> {
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой"
            }
            else -> {
                status = status.nextStatus()
                "Это неправильный ответ"
            }
        }
        return "$responseText\n${question.question}" to status.color
    }

    fun saveState(bundle: Bundle?) {
        bundle?.putString(KEY_STATUS, status.name)
        bundle?.putString(KEY_QUESTION, question.name)
    }

    companion object Factory {
        fun fromStateOrDefault(bundle: Bundle?): Bender {
            val status = bundle?.getString(KEY_STATUS) ?: Status.NORMAL.name
            val question = bundle?.getString(KEY_QUESTION) ?: Question.NAME.name
            return Bender(Status.valueOf(status), Question.valueOf(question))
        }
    }


}