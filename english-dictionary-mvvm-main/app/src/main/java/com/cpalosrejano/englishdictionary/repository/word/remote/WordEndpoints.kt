package com.cpalosrejano.englishdictionary.repository.word.remote

import com.cpalosrejano.englishdictionary.repository.word.remote.dto.WordNetwork
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface WordEndpoints {

    @GET("dictionary/{word}")
    suspend fun getDefinitions (
        @Header("Authorization") authToken: String?,
        @Path("word") username: String?
    ): WordNetwork?

}