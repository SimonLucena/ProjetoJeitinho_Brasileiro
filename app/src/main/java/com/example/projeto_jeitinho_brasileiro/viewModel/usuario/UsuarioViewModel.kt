package com.example.projeto_jeitinho_brasileiro.viewModel.usuario

import androidx.lifecycle.ViewModel
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsuarioViewModel: ViewModel() {
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()

    fun login(usuario: Usuario) {
        _usuario.value = usuario
    }
    fun logout() {
        _usuario.value = null
    }
}