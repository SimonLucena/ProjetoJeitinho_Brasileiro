package com.example.projeto_jeitinho_brasileiro.repositorio.receita

import com.example.projeto_jeitinho_brasileiro.repositorio.conteudo.Repositorio

data class Receita(
    val indice: Long = 0,
    var arquivo: String = "",
    var descricao: String = "",
    var nome: String = "",
    var preco: Double = 0.0
) {
    constructor() : this(0, "", "", "", 0.0)
}
