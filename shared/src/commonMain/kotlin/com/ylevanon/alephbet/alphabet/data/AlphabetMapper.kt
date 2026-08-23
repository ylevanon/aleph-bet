package com.ylevanon.alephbet.alphabet.data

import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import com.ylevanon.alephbet.alphabet.domain.LetterSoundSample

internal fun LetterDto.toDomain(): Letter {
    return Letter(
        id = LetterId(id),
        order = order,
        glyph = glyph,
        pointedName = namePointed,
        latinName = nameLatin,
        sounds = sounds.toList(),
        forms = forms.toList(),
        soundSamples = soundSamples.map { sample ->
            LetterSoundSample(
                id = sample.id,
                pointed = sample.pointed,
            )
        },
        isFinalForm = finalForm,
        baseLetterId = baseLetterId?.let(::LetterId),
    )
}

internal fun AlphabetContentDto.toDomainLetters(): List<Letter> {
    validateAlphabetContent(this)
    return letters.map { letterDto ->
        letterDto.toDomain()
    }
}
