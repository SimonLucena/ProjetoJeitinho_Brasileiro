package com.example.projeto_jeitinho_brasileiro.repositorio.receita

import com.example.projeto_jeitinho_brasileiro.repositorio.conteudo.Repositorio

data class Receita(
    val indice: Int = 0,
    var arquivo: String = "",
    var descricao: String = "",
    var nome: String = ""
) {

}

class Receitas {
    private val repositorio = mutableListOf<Repositorio>()

    fun addReceita(receita: Repositorio) {
        repositorio.add(receita)
    }

    // Método público para obter o tamanho da lista repositorio
    fun getTamanhoRepositorio(): Int {
        return (repositorio.size-1)
    }

    fun getListaRepositorio(): List<Repositorio>{
        return repositorio
    }

    fun getArquivo(index: Int): String?{
        val imagem = repositorio.find { it.indice == index }
        return imagem?.arquivo
    }
    fun getTitulo(index: Int): String?{
        val imagem = repositorio.find { it.indice == index }
        return imagem?.titulo
    }
    fun getDescricao(index: Int): String?{
        val imagem = repositorio.find { it.indice == index }
        return imagem?.descricao
    }
}