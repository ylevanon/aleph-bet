package com.ylevanon.alephbet.alphabet.data

internal class AlphabetContentValidationException(message: String) :
    IllegalArgumentException(message)

internal fun validateAlphabetContent(content: AlphabetContentDto): AlphabetContentDto {
    val duplicateIds = content.letters
        .groupingBy(LetterDto::id)
        .eachCount()
        .filterValues { count -> count > 1 }
        .keys
        .sorted()

    if (duplicateIds.isNotEmpty()) {
        throw AlphabetContentValidationException(
            "Duplicate letter IDs: ${duplicateIds.joinToString()}",
        )
    }

    return content
}
