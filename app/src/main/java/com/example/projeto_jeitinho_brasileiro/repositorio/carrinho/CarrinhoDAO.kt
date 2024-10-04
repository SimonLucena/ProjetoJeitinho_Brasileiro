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

            // Adiciona o item ao carrinho
            carrinhoRef.add(item.toMap()).await()
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
            val itens = snapshot.toObjects(CartItem::class.java)

            // LOG para verificar se os itens foram recuperados
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
}
