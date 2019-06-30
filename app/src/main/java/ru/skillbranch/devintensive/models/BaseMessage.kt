package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {

    abstract fun formatMessage(): String

    fun formatIncome(): String = if (isIncoming) "получил" else "отправил"

    companion object AbstractFactory {
        private var lastId = -1

        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            type: String = "text",
            payload: Any,
            isIncoming: Boolean = false
        ): BaseMessage {
            ++lastId

            return when (type) {
                "image" -> ImageMessage("$lastId", from, chat, isIncoming, date, payload as String)
                else -> TextMessage("$lastId", from, chat, isIncoming, date, payload as String)
            }
        }
    }
}