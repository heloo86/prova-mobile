package com.example.atividade_mobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProducaoViewModel : ViewModel() {

    private val _pecas = MutableLiveData(0)
    val pecas: LiveData<Int> = _pecas

    private val _isProduzindo = MutableLiveData(false)
    val isProduzindo: LiveData<Boolean> = _isProduzindo

    private var tempoInicial: Long = 0
    private var tempoTotalSegundos: Long = 0
    private var somaIntervalosMs: Long = 0
    private var ultimoRegistroMs: Long = 0

    fun iniciarProducao() {
        _pecas.value = 0
        tempoInicial = System.currentTimeMillis()
        ultimoRegistroMs = tempoInicial
        somaIntervalosMs = 0
        _isProduzindo.value = true
    }

    fun registrarPeca() {
        if (_isProduzindo.value == true) {
            val agora = System.currentTimeMillis()
            somaIntervalosMs += (agora - ultimoRegistroMs)
            ultimoRegistroMs = agora
            _pecas.value = (_pecas.value ?: 0) + 1
        }
    }

    fun finalizarProducao(): Triple<Int, Long, Double> {
        val tempoFinal = System.currentTimeMillis()
        _isProduzindo.value = false

        tempoTotalSegundos = (tempoFinal - tempoInicial) / 1000
        val totalPecas = _pecas.value ?: 0

        val taktTime = if (totalPecas > 0) {
            (somaIntervalosMs / 1000.0) / totalPecas
        } else {
            0.0
        }

        return Triple(totalPecas, tempoTotalSegundos, taktTime)
    }
}
