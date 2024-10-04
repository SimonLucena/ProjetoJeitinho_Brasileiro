package com.example.projeto_jeitinho_brasileiro.repositorio.user

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class UsuarioDAO {
    val db = FirebaseFirestore.getInstance()

    // Função para listar todos os usuários
    fun listarUsuarios(callBack: (List<Usuario>?) -> Unit) {
        db.collection("usuario").get()
            .addOnSuccessListener { documentSnapshot ->
                // Mapeia os documentos para a classe Usuario e inclui o ID do documento como 'indice'
                val usuarios = documentSnapshot.documents.map { document ->
                    Usuario(
                        indice = document.id, // Pega o ID do documento
                        nome = document.getString("nome") ?: "",
                        email = document.getString("login") ?: "",
                        senha = document.getString("senha") ?: ""
                    )
                }
                callBack(usuarios)
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }

    // Função para buscar um usuário por login
    fun buscarPorLogin(login: String, callBack: (Usuario?) -> Unit) {
        db.collection("usuario").whereEqualTo("login", login).get()
            .addOnSuccessListener { documentUsuario ->
                if (!documentUsuario.isEmpty) {
                    val document = documentUsuario.documents[0]
                    val usuario = Usuario(
                        indice = document.id, // Pega o ID do documento
                        nome = document.getString("nome") ?: "",
                        email = document.getString("login") ?: "",
                        senha = document.getString("senha") ?: "",
                        foto = document.getString("foto") ?: "",
                    )
                    callBack(usuario)
                } else {
                    callBack(null)
                }
            }
            .addOnFailureListener {
                callBack(null)
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Erro ao buscar usuário por login", exception)
                callBack(null)
            }
    }

    fun cadastrarUsuarioDAO(nome: String, email: String, senha: String){
        db.collection("usuario").add(
            hashMapOf(
                "login" to email,
                "nome" to nome,
                "senha" to senha,
                "foto" to ""
            )
        )
    }

    // Função para atualizar a URL da imagem de perfil no Firestore
    fun updateUserProfileImage(usuarioId: String, imageUrl: String) {
        val usuarioRef = db.collection("usuario").document(usuarioId)

        usuarioRef.update("foto", imageUrl)
            .addOnSuccessListener {
                Log.d("Firestore", "Imagem de perfil atualizada com sucesso!")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao atualizar a imagem de perfil", e)
            }
    }
}