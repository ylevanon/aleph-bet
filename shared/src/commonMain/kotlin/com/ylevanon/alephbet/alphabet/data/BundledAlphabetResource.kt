package com.ylevanon.alephbet.alphabet.data

import alephbet.shared.generated.resources.Res


internal suspend fun readBundledAlphabetJson(): String {
    return Res.readBytes("files/hebrew/alphabet.json").decodeToString()
}