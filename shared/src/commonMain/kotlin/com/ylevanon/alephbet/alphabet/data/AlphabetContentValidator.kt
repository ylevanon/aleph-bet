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

    val letterIds = content.letters.map(LetterDto::id).toSet()
    val missingBaseReferences = content.letters.mapNotNull { letter ->
        val baseLetterId = letter.baseLetterId ?: return@mapNotNull null
        if (baseLetterId in letterIds) {
            null
        } else {
            "${letter.id} -> $baseLetterId"
        }
    }.sorted()

    if (missingBaseReferences.isNotEmpty()) {
        throw AlphabetContentValidationException(
            "Missing base letter references: ${missingBaseReferences.joinToString()}",
        )
    }

    return content
}
