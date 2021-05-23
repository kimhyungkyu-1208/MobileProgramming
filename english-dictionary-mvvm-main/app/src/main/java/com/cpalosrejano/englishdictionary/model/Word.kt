package com.cpalosrejano.englishdictionary.model

class Word (
    var word: String?,
    var definitions: List<Definition>?,
    var pronunciation: String?
) {

    override fun toString(): String {
        return "Definitions for $word:\n ${definitions.toString()}"
    }

}