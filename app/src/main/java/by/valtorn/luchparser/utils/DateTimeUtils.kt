package by.valtorn.luchparser.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

internal val backendDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
internal val normalDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

private fun String.backendTimeToDateTime(): DateTime = backendDateFormat.parseDateTime(this)

fun String.backTimeToNormalTime(): String = normalDateFormat.print(this.backendTimeToDateTime().toDateTime(DateTimeZone.forOffsetHours(3)))