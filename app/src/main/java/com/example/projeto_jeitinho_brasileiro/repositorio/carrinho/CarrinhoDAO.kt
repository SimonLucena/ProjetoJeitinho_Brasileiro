package com.example.projeto_jeitinho_brasileiro.repositorio.carrinho

import com.example.projeto_jeitinho_brasileiro.ui.telas.CartItem;
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarrinhoDAO {

    private val db = FirebaseFirestore.getInstance()

    // Função para adicionar um item ao carrinho na subcoleção do usuário
    suspend fun adicionarItemAoCarrinho(usuarioId: String, item: CartItem): Boolean {
        return try {
            val usuarioRef = db.collection("usuario").document(usuarioId)
            val carrinhoRef = usuarioRef.collection("carrinho")

            // Adiciona o item ao carrinho e captura o ID do documento
            val documentRef = carrinhoRef.add(item.toMap()).await()
            // Atribui o ID do documento ao item
            item.id = documentRef.id
            true
        } catch (e: Exception) {
            Log.w("Firestore", "Erro ao adicionar item", e)
            false
        }
    }

    // Função para ler os itens do carrinho do Firestore
    suspend fun buscarItensCarrinho(usuarioId: String): List<CartItem> {
        return try {
            val usuarioRef = db.collection("usuario").document(usuarioId)
            val carrinhoRef = usuarioRef.collection("carrinho")

            val snapshot = carrinhoRef.get().await()
            val itens = snapshot.map { document ->
                val item = document.toObject(CartItem::class.java)
                item.id = document.id  // Atribuir o ID do documento ao item
                item
            }

            Log.d("CarrinhoDAO", "Itens do carrinho recuperados: $itens")
            itens
        } catch (e: Exception) {
            Log.w("CarrinhoDAO", "Erro ao obter itens do carrinho", e)
            emptyList()
        }
    }

    // Função para remover item do carrinho
    suspend fun removerItemDoCarrinho(usuarioId: String, itemId: String): Boolean {
        return try {
            val usuarioRef = db.collection("usuario").document(usuarioId)
            val carrinhoRef = usuarioRef.collection("carrinho")

            carrinhoRef.document(itemId).delete().await()
            true
        } catch (e: Exception) {
            Log.w("Firestore", "Erro ao remover item", e)
            false
        }
    }


    // Função para registrar uma compra (mover itens do carrinho para uma subcoleção "compras")
    suspend fun registrarCompra(usuarioId: String, itens: List<CartItem>) {
        try {
            val usuarioRef = db.collection("usuario").document(usuarioId)
            val comprasRef = usuarioRef.collection("compras")

            itens.forEach { item ->
                comprasRef.add(item.toMap()).await() // Adiciona cada item à subcoleção "compras"
            }

            Log.d("CarrinhoDAO", "Compra registrada com sucesso!")
        } catch (e: Exception) {
            Log.w("CarrinhoDAO", "Erro ao registrar a compra", e)
        }
    }

    // Função para limpar o carrinho
    suspend fun limparCarrinho(usuarioId: String) {
        try {
            val usuarioRef = db.collection("usuario").document(usuarioId)
            val carrinhoRef = usuarioRef.collection("carrinho")

            val snapshot = carrinhoRef.get().await()
            for (document in snapshot.documents) {
                carrinhoRef.document(document.id).delete().await()
            }

            Log.d("CarrinhoDAO", "Carrinho limpo com sucesso!")
        } catch (e: Exception) {
            Log.w("CarrinhoDAO", "Erro ao limpar o carrinho", e)
        }
    }
}