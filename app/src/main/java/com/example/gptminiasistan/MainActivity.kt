package com.example.gptminiasistan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gptminiasistan.data.model.AiRequest
import com.example.gptminiasistan.data.model.AiResponse
import com.example.gptminiasistan.data.model.ChatMessage
import com.example.gptminiasistan.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val apiKey = "Bearer sk-or-..."  // "Bearer" eklemeyi unutma!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAnalyze.setOnClickListener {
            val userText = binding.etNote.text.toString().trim()
            if (userText.isNotEmpty()) {
                callOpenAi(userText)
            } else {
                Toast.makeText(this, "Lütfen bir metin gir ☕", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun callOpenAi(note: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openrouter.ai/api/v1/")

            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OpenAiService::class.java)

        val request = AiRequest(
            messages = listOf(
                ChatMessage("system", "Kullanıcının yazdığı günlük notunu 1-2 cümlede özetle ve duygusal tonunu belirt."),
                ChatMessage("user", note)
            )
        )

        service.getChatCompletion(apiKey, request).enqueue(object : Callback<AiResponse> {
            override fun onResponse(call: Call<AiResponse>, response: Response<AiResponse>) {
                if (response.isSuccessful) {
                    val reply = response.body()?.choices?.firstOrNull()?.message?.content ?: "Cevap alınamadı."
                    val intent = Intent(this@MainActivity, SummaryActivity::class.java)
                    intent.putExtra ("ai_summary", reply)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Yanıt başarısız: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "API hatası: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}