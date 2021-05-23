package com.cpalosrejano.englishdictionary.repository.word

import com.cpalosrejano.englishdictionary.model.Word

class WordRepositoryImpl (
    private val remote: WordRepository.RemoteDataSource,
    private val local: WordRepository.LocalDataSource
) : WordRepository {

    override suspend fun getWord(word: String): Word? {
        return remote.getWord(word)?.asDomain()
    }
}