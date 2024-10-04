package com.example.projeto_jeitinho_brasileiro.repositorio.receita

import android.util.Log
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class ReceitaDAO {
    val db = FirebaseFirestore.getInstance()

    fun listarReceitas(callBack: (List<Receita>?) -> Unit) {
        db.collection("receita").get()
            .addOnSuccessListener { documents ->
                val receitas = documents.map { document ->
                    val receita = document.toObject(Receita::class.java)
                    receita.id = document.id // Atribuir o ID do documento à receita
                    receita
                }
                callBack(receitas)
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }
}