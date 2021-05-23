package com.cpalosrejano.englishdictionary.repository.word

import com.cpalosrejano.englishdictionary.model.Word
import com.cpalosrejano.englishdictionary.repository.word.remote.dto.WordNetwork

interface WordRepository {

    /**
     * Get a list of definitions for a word
     */
    suspend fun getWord(word: String) : Word?

    interface LocalDataSource {

    }

    interface RemoteDataSource {

        /**
         * Make a request to OwlBot to find definition of a word
         */
        suspend fun getWord(word: String) : WordNetwork?
    }

}