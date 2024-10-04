package com.example.projeto_jeitinho_brasileiro.ViewModel.usuario

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import com.example.projeto_jeitinho_brasileiro.repositorio.user.UsuarioDAO
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class UsuarioViewModel: ViewModel() {
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()
    private val usuarioDAO = UsuarioDAO()

    fun login(usuario: Usuario) {
        _usuario.value = usuario
    }
    fun logout() {
        _usuario.value = null
    }

    // Função para fazer upload da imagem para o Firebase Storage
    fun uploadImageToFirebaseStorage(imageUri: Uri, usuarioId: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("profile_images/${UUID.randomUUID()}.jpg")

        // Upload do arquivo para o Firebase Storage
        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                // Após o upload, obter a URL de download da imagem
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()

                    // Atualizar o Firestore com a URL da imagem
                    usuarioDAO.updateUserProfileImage(usuarioId, imageUrl)
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseStorage", "Erro ao fazer upload da imagem", e)
            }
    }
}