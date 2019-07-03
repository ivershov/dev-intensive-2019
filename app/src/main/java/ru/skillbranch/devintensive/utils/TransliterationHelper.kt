package ru.skillbranch.devintensive.utils

object TransliterationHelper {
    val map: Map<Char, String> = mapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "e",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "i",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sh'",
        'ъ' to "",
        'ы' to "i",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya"
    )

    fun translite(payload: String, divider: String = " "): String {
        var result = ""

        payload.replace(" ", divider)
            .toCharArray()
            .forEach {
                val isUpperCase = it.isUpperCase()
                var latinSymbol = map.getOrDefault(it.toLowerCase(), it.toString())

                if (isUpperCase) latinSymbol = latinSymbol.capitalize()
                result = result.plus(latinSymbol)
            }
        return result
    }
}