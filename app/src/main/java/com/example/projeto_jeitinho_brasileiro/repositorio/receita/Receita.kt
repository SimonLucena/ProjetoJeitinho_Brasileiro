package com.example.projeto_jeitinho_brasileiro.repositorio.receita

import com.example.projeto_jeitinho_brasileiro.repositorio.conteudo.Repositorio

data class Receita(
    val indice: Int = 0,
    var arquivo: String = "",
    var descricao: String = "",
    var nome: String = "",
    var preco: Float
) {}