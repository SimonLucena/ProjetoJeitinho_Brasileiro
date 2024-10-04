package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto_jeitinho_brasileiro.R
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.Receita
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto_jeitinho_brasileiro.ViewModel.receita.ReceitaViewModel

// Carregando a fonte Oswald do Google Fonts

@Composable
fun TelaConteudo() {
    val viewModelReceitas: ReceitaViewModel = viewModel()
    var receitas by remember { mutableStateOf<List<Receita>?>(null) }

    LaunchedEffect(viewModelReceitas) {
        viewModelReceitas.fetchReceitas { fetchedReceitas: List<Receita>? ->
            receitas = fetchedReceitas
        }
    }

    // Layout principal da tela
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF176)) // Fundo amarelo claro
            .padding(8.dp)
    ) {
        receitas?.let { list ->
            items(list) { receita ->
                val arquivo = receita.arquivo
                val titulo = receita.nome
                val descricao = receita.descricao

                val resourceId = when (arquivo) {
                    "pacoca" -> R.drawable.pacoca
                    "feijao_tropeiro" -> R.drawable.feijao_tropeiro
                    "camarao_moranga" -> R.drawable.camarao_moranga
                    "mousse_maracuja" -> R.drawable.mousse_maracuja
                    "biscoito_para" -> R.drawable.biscoito_para
                    else -> R.drawable.kelly_pool
                }

                // Card para cada item da lista
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xfffdfdfd)), // Cartão branco
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(bottom = 8.dp))
                        .clickable { /* Ação ao clicar no item */ }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        // Imagem da receita
                        Image(
                            painter = painterResource(id = resourceId),
                            contentDescription = "Imagem da Receita",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 16.dp)
                        )

                        // Título e descrição da receita
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 8.dp)
                        ) {
                            Text(
                                text = titulo ?: "Título indisponível",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily, // Fonte Oswald
                                    color = Color(0xFF5D4037) // Marrom para combinar com a paleta
                                )
                            )
                            Text(
                                text = descricao ?: "Descrição indisponível",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = fontFamily, // Fonte Oswald
                                    color = Color.Gray
                                ),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
