package ru.skillbranch.devintensive.extensions

fun String.truncate(len: Int = 16): String {
    val result = trim()
    if (result.length <= len)
        return result
    return result.take(len).trimEnd() + "..."
}

fun String.stripHtml(): String {
    val regex1 = """&.*?;|<.*?>""".toRegex()
    val regex2 = """[ ]+""".toRegex()

    return replace(regex1, "").replace(regex2, " ")
}