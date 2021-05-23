package com.cpalosrejano.englishdictionary.repository.word.remote

import android.content.Context
import com.cpalosrejano.englishdictionary.repository.word.WordRepository
import com.cpalosrejano.englishdictionary.repository.word.remote.dto.WordNetwork
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordRemoteDataSource(context: Context) : WordRepository.RemoteDataSource {

    private val endpoints: WordEndpoints by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            .create(WordEndpoints::class.java)
    }

    override suspend fun getWord(word: String): WordNetwork? {
        return endpoints.getDefinitions(AUTH_TOKEN, word)
    }


    companion object {
        const val BASE_URL = "https://owlbot.info/api/v4/"
        const val AUTH_TOKEN = "Token 6e77101929f64d877461c15ba4ee6db6070948b1"
    }

}