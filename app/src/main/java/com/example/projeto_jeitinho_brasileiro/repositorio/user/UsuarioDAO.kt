package com.example.projeto_jeitinho_brasileiro.repositorio.user

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class UsuarioDAO {
    val db = FirebaseFirestore.getInstance()

    fun listarUsuarios(callBack: (List<Usuario>?) -> Unit){
        db.collection("usuario").get()
            .addOnSuccessListener { document ->
                val usuarios = document.toObjects<Usuario>()
                callBack(usuarios)
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }

    fun buscarPorLogin(login: String, callBack: (Usuario?) -> Unit){
        db.collection("usuario").whereEqualTo("login", login).get()
            .addOnSuccessListener { documentUsuario ->
                if(!documentUsuario.isEmpty){
                    val usuario = documentUsuario.documents[0].toObject<Usuario>()
                    callBack(usuario)
                }else{
                    callBack(null)
                }
            }
            .addOnFailureListener {
                callBack(null)
            }
            .addOnFailureListener { exception ->
                // Optionally log the error for debugging
                Log.e("FirestoreError", "Error fetching user by login", exception)
                callBack(null)
            }
    }

    fun cadastrarUsuarioDAO(nome: String, email: String, senha: String){
        db.collection("usuario").add(
            hashMapOf(
                "login" to email,
                "nome" to nome,
                "senha" to senha
            )
        )
    }
}