package com.example.projeto_jeitinho_brasileiro.repositorio.user

data class Usuario(val indice:String = "", val nome:String = "", val email:String = "", val senha:String = "") {
}

//class Cadastro {
//    private var cadastro = mutableListOf<Usuario>()
//
//    fun addPerfil(user: Usuario) {
//        cadastro.add(user)
//    }
//
//    fun removePerfil() {
//        cadastro.clear()
//    }
//
//    fun getListaUsuario(): List<Usuario>{
//        return cadastro
//    }
//
//    // Método público para obter o tamanho da lista repositorio
//    fun getTamanhoRepositorio(): Int {
//        return (cadastro.size-1)
//    }
//
//    fun getEmail(indice: Int): String?{
//        val usuario = cadastro.find { it.indice == indice }
//        return usuario?.email
//    }
//    fun getNome(indice: Int): String?{
//        val usuario = cadastro.find { it.indice == indice }
//        return usuario?.nome
//    }
//    fun getSenha(indice: Int): String?{
//        val usuario = cadastro.find { it.indice == indice }
//        return usuario?.senha
//    }
//}