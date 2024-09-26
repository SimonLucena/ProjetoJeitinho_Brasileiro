package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
<<<<<<< Updated upstream
import androidx.compose.foundation.layout.width
=======
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
>>>>>>> Stashed changes
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
<<<<<<< Updated upstream
import com.example.projeto_jeitinho_brasileiro.repositorio.conteudo.Receitas
import com.example.projeto_jeitinho_brasileiro.repositorio.conteudo.Repositorio
import com.example.projeto_jeitinho_brasileiro.R
=======
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto_jeitinho_brasileiro.R
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.Receita
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.ReceitaDAO
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.Receitas
import com.example.projeto_jeitinho_brasileiro.viewModel.receita.ReceitaViewModel
>>>>>>> Stashed changes

@Composable
fun TelaConteudo() {
    val viewModel: ReceitaViewModel = viewModel()
    var receitas by remember { mutableStateOf<List<Receita>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchReceitas { fetchedReceitas: List<Receita>? ->
            receitas = fetchedReceitas
        }
    }



//    receita.addReceita(Repositorio(0, "pacoca", "Paçoca Nordestina", "Que tal preparar uma paçoca de carne de sol, delicioso e inusitado acompanhamento? \n Trata-se de uma farofa salgada, prato perfeito para a sua refeição de domingo."))
//    receita.addReceita(Repositorio(1, "feijao_tropeiro", "Feijão Tropeiro", "O Feijão tropeiro mineiro, é um prato que leva feijão-carioca, bacon, calabresa, couve, ovos e farinha de mandioca."))
//    receita.addReceita(Repositorio(2, "camarao_moranga", "Camarão na moranga", "Perfeito para ocasiões especiais e para impressionar os convidados, o camarão na moranga é um prato que combina simplicidade com sofisticação, \n trazendo à mesa um pedacinho da riqueza da culinária brasileira."))
//    receita.addReceita(Repositorio(3, "mousse_maracuja", "Mousse de Maracujá", "A combinação equilibrada entre a acidez característica do maracujá e a suavidade dos laticínios torna esta sobremesa um verdadeiro deleite para o paladar."))
//    receita.addReceita(Repositorio(4, "biscoito_para", "Biscoito de Castanha-do-Pará", "Imagine o sabor autêntico da Amazônia em cada mordida. \n Os Biscoitos de Castanha do Pará são uma verdadeira joia da culinária brasileira em um lanche simples."))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
//            .verticalScroll(rememberScrollState())
            .background(Color.White)
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

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xfffdfdfd),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(bottom = 8.dp))
                        .clickable { /*  */ }
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = resourceId),
                            contentDescription = "Imagem",
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .aspectRatio(1f)
                                .padding(16.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = titulo!!,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            )
                            Text(
                                text = descricao!!,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                ),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
//        for (item in receitas.getListaRepositorio()) {
//        }
    }
}
