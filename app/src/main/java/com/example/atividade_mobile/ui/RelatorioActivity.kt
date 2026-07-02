package com.example.atividade_mobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.atividade_mobile.databinding.ActivityRelatorioBinding
import java.util.*

class RelatorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelatorioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exibirRelatorio()

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun exibirRelatorio() {
        val operador = intent.getStringExtra("OPERADOR") ?: ""
        val linha = intent.getStringExtra("LINHA") ?: ""
        val pecas = intent.getIntExtra("PECAS", 0)
        val tempoTotalSegundos = intent.getLongExtra("TEMPO_TOTAL", 0)
        val taktTime = intent.getDoubleExtra("TAKT_TIME", 0.0)

        val minutos = tempoTotalSegundos / 60
        val segundos = tempoTotalSegundos % 60
        val tempoFormatado = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos)

        binding.txtRelatorioOperador.text = "OPERADOR: ${operador.uppercase()}"
        binding.txtRelatorioLinha.text = "LINHA: ${linha.uppercase()}"
        binding.txtRelatorioPecas.text = "Quantidade de peças: $pecas"
        binding.txtRelatorioTempoTotal.text = "Tempo total: $tempoFormatado ($tempoTotalSegundos s)"
        binding.txtRelatorioTaktTime.text = "Tempo médio (Takt Time): ${"%.2f".format(Locale.US, taktTime)} s"
    }
}
