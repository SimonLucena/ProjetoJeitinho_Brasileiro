package com.example.projeto_jeitinho_brasileiro.repositorio.user

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
    }
}