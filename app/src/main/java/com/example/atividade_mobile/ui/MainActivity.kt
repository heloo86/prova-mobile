package com.example.atividade_mobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.atividade_mobile.databinding.ActivityMainBinding
import com.example.atividade_mobile.viewmodel.ProducaoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProducaoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.pecas.observe(this) { count ->
            binding.txtContador.text = count.toString()
        }

        viewModel.isProduzindo.observe(this) { produzindo ->
            binding.btnStart.isEnabled = !produzindo
            binding.btnPeca.isEnabled = produzindo

            binding.edtOperador.isEnabled = !produzindo
            binding.edtLinha.isEnabled = !produzindo
        }
    }

    private fun setupListeners() {
        binding.btnStart.setOnClickListener {
            val operador = binding.edtOperador.text.toString()
            val linha = binding.edtLinha.text.toString()

            if (operador.isNotBlank() && linha.isNotBlank()) {
                viewModel.iniciarProducao()
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPeca.setOnClickListener {
            viewModel.registrarPeca()
        }

        binding.btnFinalizar.setOnClickListener {
            if (viewModel.isProduzindo.value == true) {
                val (pecas, tempoTotal, taktTime) = viewModel.finalizarProducao()

                val intent = Intent(this, RelatorioActivity::class.java).apply {
                    putExtra("OPERADOR", binding.edtOperador.text.toString())
                    putExtra("LINHA", binding.edtLinha.text.toString())
                    putExtra("PECAS", pecas)
                    putExtra("TEMPO_TOTAL", tempoTotal)
                    putExtra("TAKT_TIME", taktTime)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Inicie a produção primeiro!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
