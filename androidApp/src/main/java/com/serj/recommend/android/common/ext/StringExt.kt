package com.serj.recommend.android.common.ext

import android.util.Patterns
import androidx.compose.ui.graphics.Color
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= MIN_PASS_LENGTH &&
            Pattern.compile(PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun String.idFromParameter(): String {
    return this.substring(1, this.length - 1)
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

fun String.toParagraphText(): List<String> {
    val prepare = this.replace("\\n", "\n")
    return prepare.split("\\n".toRegex()).map { it.trim() }
}