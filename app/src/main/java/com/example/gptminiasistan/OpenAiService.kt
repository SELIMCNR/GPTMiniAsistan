package com.example.gptminiasistan

import retrofit2.Call
import com.example.gptminiasistan.data.model.AiRequest
import com.example.gptminiasistan.data.model.AiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface OpenAiService {
    @POST("v1/chat/completions")
    fun getChatCompletion(
        @Header("Authorization") auth: String,
        @Body request: AiRequest
    ): Call<AiResponse>
}