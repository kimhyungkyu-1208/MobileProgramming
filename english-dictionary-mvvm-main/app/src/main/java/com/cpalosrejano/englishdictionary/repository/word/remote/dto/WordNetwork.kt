package com.cpalosrejano.englishdictionary.repository.word.remote.dto

import com.cpalosrejano.englishdictionary.model.Word
import com.cpalosrejano.englishdictionary.repository.DTOBase
import com.cpalosrejano.englishdictionary.repository.asDomain
import com.google.gson.annotations.SerializedName

class WordNetwork : DTOBase<Word>() {

    @SerializedName("word")
    var word: String? = null

    @SerializedName("definitions")
    var definitions: List<DefinitionNetwork>? = null

    @SerializedName("pronunciation")
    var pronunciation: String? = null

    override fun asDomain(): Word {
        // convert this DTO into Word object
       return Word(
               word,
               definitions?.asDomain(),
               pronunciation
       )
    }
}