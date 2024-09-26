package com.example.projeto_jeitinho_brasileiro.viewModel.receita

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.Receita
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.ReceitaDAO
import kotlinx.coroutines.launch

class ReceitaViewModel : ViewModel() {
    private val receitaDAO = ReceitaDAO()

    fun fetchReceitas(callBack: (List<Receita>?) -> Unit) {
        viewModelScope.launch {
            receitaDAO.listarReceitas { receitas ->
                callBack(receitas)
            }
        }
    }
}