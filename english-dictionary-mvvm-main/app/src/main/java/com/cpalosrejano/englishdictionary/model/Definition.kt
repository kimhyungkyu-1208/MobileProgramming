package com.cpalosrejano.englishdictionary.model

class Definition (
    var type: String?,
    var definition: String?,
    var example: String?,
    var imageUrl: String?,
    var emoji: String?,
) {

    override fun toString(): String {
        return "[\n" +
                "definition: $definition\n" +
                "type: $type\n" +
                "example: $example\n" +
                "image: $imageUrl\n" +
                "emoji: $emoji\n" +
                "]\n"
    }
}