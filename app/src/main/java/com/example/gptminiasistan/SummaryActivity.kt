package com.example.gptminiasistan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gptminiasistan.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val response = intent.getStringExtra("ai_summary") ?: "Yanıt alınamadı."

        // Yapay zekâ cevabından özet ve duygu ayrıştır
        val (summary, emotion) = parseSummaryAndEmotion(response)

        binding.tvSummary.text = summary
        binding.tvEmotion.text = "Duygusal analiz: $emotion"
    }

    private fun parseSummaryAndEmotion(response: String): Pair<String, String> {
        val parts = response.split("Duygu:", "Emotion:", "Tone:", ignoreCase = true, limit = 2)
        val summary = parts.getOrNull(0)?.trim() ?: response
        val emotion = parts.getOrNull(1)?.trim()?.capitalize() ?: "belirlenemedi"
        return summary to emotion
    }
}