package com.example.gptminiasistan.data.model

data class AiResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: ChatMessage
)