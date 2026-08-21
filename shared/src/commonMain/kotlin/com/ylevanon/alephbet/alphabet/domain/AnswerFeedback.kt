package com.ylevanon.alephbet.alphabet.domain

sealed interface AnswerFeedback {
    data object None : AnswerFeedback

    data class Correct(
        val correctLetterId : LetterId,
    ) : AnswerFeedback

    data class Incorrect(
        val correctLetterId : LetterId,
        val incorrectLetterId : LetterId,
    ) : AnswerFeedback
}