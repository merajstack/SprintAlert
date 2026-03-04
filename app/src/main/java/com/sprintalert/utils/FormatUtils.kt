package com.sprintalert.utils

fun formatTime(seconds: Float): String {
    val mins = seconds.toInt() / 60
    val secs = (seconds.toInt() % 60).toString().padStart(2, '0')
    val millis = ((seconds % 1) * 100).toInt().toString().padStart(2, '0')
    return if (mins > 0) "$mins:$secs.$millis" else "0:$secs.$millis"
}
