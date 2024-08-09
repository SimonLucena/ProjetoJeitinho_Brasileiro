package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto_jeitinho_brasileiro.repositorio.conteudo.Receitas
import com.example.projeto_jeitinho_brasileiro.repositorio.conteudo.Repositorio
import com.example.projeto_jeitinho_brasileiro.R

@Composable
fun TelaConteudo() {

    // Criar uma instância de Galeria
    val receita = Receitas()

    receita.addReceita(Repositorio(0, "kelly_pool", "“Kelly Pool”", "Um grupo de seis cães se diverte ao redor de uma mesa de bilhar jogando Kelly Pool. Os outros cinco cães estão entusiasticamente importunando o jogador, na tentativa de melhorar sua própria posição.\nÉ interessante notar que Coolidge escolheu colocar os cães envolvidos em um jogo conhecido por apostas."))
    receita.addReceita(Repositorio(1, "ten_miles_to_a_garage", "“Ten Miles To A Garage”", "Uma família de cães está a caminho de um piquenique de verão (talvez comemorando o dia 4 de julho) quando enfrenta problemas com o carro em um trecho solitário de uma estrada rural. Eles provavelmente estão na zona rural de Nova York, pois esse é o estado da placa do carro. Os filhotes brincam no carro usando bonés e carregando cestas de comida, enquanto a maioria dos cães mais velhos tenta consertar o automóvel. Porém, normalmente, apenas um cachorro rastejou para baixo do carro e está realmente trabalhando com ferramentas, enquanto outros três ficam sentados de braços cruzados. Um deles está até deitado debaixo do carro, relaxando à sua sombra. Todos os cães adultos também aproveitaram a parada não programada para fumar um cigarro."))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        for (item in receita.getListaRepositorio()){
            val arquivo = receita.getArquivo(item.indice)
            val titulo = receita.getTitulo(item.indice)
            val descricao = receita.getDescricao(item.indice)

            // Mapeia o título para o ID do recurso correspondente
            val resourceId = when (arquivo) {
                "kelly_pool" -> R.drawable.kelly_pool
                "ten_miles_to_a_garage" -> R.drawable.ten_miles_to_a_garage
                else -> R.drawable.kelly_pool// R.drawable.kelly_pool // Um recurso de imagem padrão para quando não há correspondência
            }

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(bottom = 8.dp))
            ) {
                Row {
                    Column(modifier = Modifier.weight(2f)) {
                        Image(
                            painter = painterResource(id = resourceId),
                            contentDescription = "Example Image",
                            modifier = Modifier
                                .width(400.dp)
                                .padding(16.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .align(Alignment.CenterVertically)
                    ) {
                        Row {
                            Text(
                                text = titulo!!,
                                style = TextStyle(
                                    fontSize = 24.sp
                                )
                            )
                        }
                    }
                }
            }

//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(PaddingValues(bottom = 8.dp))
//            ) {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Image(
//                        painter = painterResource(id = resourceId),
//                        contentDescription = "Imagem",
//                        modifier = Modifier
//                            .padding(2.dp)
//                            .width(1.dp)
//                            .height(1.dp)
//                    )
//                }
////                Column(modifier = Modifier.weight(2f)) {
////                    Row {
////                        Text(text = titulo!!)
////                    }
////                    Row {
////                        Text(text = descricao!!)
////                    }
////                }
//            }
        }
    }
}