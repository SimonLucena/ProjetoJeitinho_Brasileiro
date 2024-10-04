package com.example.projeto_jeitinho_brasileiro.ViewModel.usuario

import com.example.projeto_jeitinho_brasileiro.ui.telas.CartItem;

data class CarrinhoUsuario(
    val usuarioId: String,
    val itens: MutableList<CartItem> = mutableListOf()
) {
    // Função para calcular o total
    fun calcularTotal(): Double {
        return itens.sumOf { it.price * it.quantidade }
    }
}