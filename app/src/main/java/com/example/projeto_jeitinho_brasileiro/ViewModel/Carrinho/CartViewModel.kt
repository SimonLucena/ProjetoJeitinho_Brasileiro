package com.example.projeto_jeitinho_brasileiro.ViewModel.Carrinho;

import com.example.projeto_jeitinho_brasileiro.ui.telas.CartItem;
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_jeitinho_brasileiro.repositorio.carrinho.CarrinhoDAO
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.projeto_jeitinho_brasileiro.ViewModel.usuario.CarrinhoUsuario;
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CartViewModel : ViewModel() {
    private val carrinhoDAO = CarrinhoDAO()

    // Estado que guarda o carrinho local do usuário
    private val _carrinhoUsuario = MutableStateFlow<CarrinhoUsuario?>(null)
    val carrinhoUsuario: StateFlow<CarrinhoUsuario?> get() = _carrinhoUsuario

    // Função para buscar o carrinho do Firestore e popular a classe local
    fun fetchCartItems(usuarioId: String) {
        viewModelScope.launch {
            val itens = carrinhoDAO.buscarItensCarrinho(usuarioId) // Busca os itens do Firebase
            val carrinho = CarrinhoUsuario(usuarioId = usuarioId, itens = itens.toMutableList())
            _carrinhoUsuario.value = carrinho // Popula a classe local
        }
    }

    // Função para adicionar item ao carrinho
    fun addItemToCart(usuarioId: String, item: CartItem) {
        viewModelScope.launch {
            val success = carrinhoDAO.adicionarItemAoCarrinho(usuarioId, item)
            if (success) {
                fetchCartItems(usuarioId) // Atualiza o carrinho
            }
        }
    }

    // Função para remover item do carrinho
    fun removeItemFromCart(usuarioId: String, itemId: String?) {
        if (itemId != null) {
            viewModelScope.launch {
                val success = carrinhoDAO.removerItemDoCarrinho(usuarioId, itemId)
                if (success) {
                    fetchCartItems(usuarioId) // Atualiza o carrinho
                }
            }
        }
    }

    // Função para calcular o total usando a classe local
    fun calcularTotal(): Double {
        return _carrinhoUsuario.value?.calcularTotal() ?: 0.0
    }
}