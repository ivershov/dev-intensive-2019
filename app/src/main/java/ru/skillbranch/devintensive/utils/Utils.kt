package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val split = fullName?.split(" ")

        val firstName = split?.getOrNull(0)?.ifEmpty { null }
        val lastName = split?.getOrNull(1)?.ifEmpty { null }
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return TransliterationHelper.translite(payload, divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = firstName?.trimStart()?.firstOrNull()?.toUpperCase()
        val second = lastName?.trimStart()?.firstOrNull()?.toUpperCase()

        return listOfNotNull(first, second)
            .joinToString("")
            .ifEmpty { null }
    }
}