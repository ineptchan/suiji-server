package top.inept.suiji.extensions

import top.inept.suiji.exception.business.InvalidUuidException
import java.util.UUID

fun String.toUuidOrThrow(): UUID =
    try {
        UUID.fromString(this)
    } catch (_: IllegalArgumentException) {
        throw InvalidUuidException()
    }
