package com.example.gptminiasistan.data.model

data class AiRequest(
   val model : String =  "openrouter/openai/gpt-3.5-turbo",

    val messages: List<ChatMessage>
)