package com.cpalosrejano.englishdictionary.repository.word.remote.dto

import com.cpalosrejano.englishdictionary.model.Definition
import com.cpalosrejano.englishdictionary.repository.DTOBase
import com.google.gson.annotations.SerializedName

class DefinitionNetwork: DTOBase<Definition>() {

    @SerializedName("type")
    var type: String? = null

    @SerializedName("definition")
    var definition: String? = null

    @SerializedName("example")
    var example: String? = null

    @SerializedName("image_url")
    var imageUrl: String? = null

    @SerializedName("emoji")
    var emoji: String? = null

    override fun asDomain(): Definition {
        // convert this DTO into Definition object
        return Definition(
                type,
                definition,
                example,
                imageUrl,
                emoji
        )
    }
}