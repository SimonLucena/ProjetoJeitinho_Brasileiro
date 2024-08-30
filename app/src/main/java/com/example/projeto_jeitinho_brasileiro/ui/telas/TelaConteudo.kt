package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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

    receita.addReceita(Repositorio(0, "pacoca", "Paçoca Nordestina", "Que tal preparar uma paçoca de carne de sol, delicioso e inusitado acompanhamento? \n Trata-se de uma farofa salgada, prato perfeito para a sua refeição de domingo."))
    receita.addReceita(Repositorio(1, "feijao_tropeiro", "Feijão Tropeiro", "O Feijão tropeiro mineiro, é um prato que leva feijão-carioca, bacon, calabresa, couve, ovos e farinha de mandioca."))
    receita.addReceita(Repositorio(2, "camarao_moranga", "Camarão na moranga", "Perfeito para ocasiões especiais e para impressionar os convidados, o camarão na moranga é um prato que combina simplicidade com sofisticação, \n trazendo à mesa um pedacinho da riqueza da culinária brasileira."))
    receita.addReceita(Repositorio(3, "mousse_maracuja", "Mousse de Maracujá", "A combinação equilibrada entre a acidez característica do maracujá e a suavidade dos laticínios torna esta sobremesa um verdadeiro deleite para o paladar."))
    receita.addReceita(Repositorio(4, "biscoito_para", "Biscoito de Castanha-do-Pará", "Imagine o sabor autêntico da Amazônia em cada mordida. \n Os Biscoitos de Castanha do Pará são uma verdadeira joia da culinária brasileira em um lanche simples."))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        for (item in receita.getListaRepositorio()) {
            val arquivo = receita.getArquivo(item.indice)
            val titulo = receita.getTitulo(item.indice)
            val descricao = receita.getDescricao(item.indice)

            val resourceId = when (arquivo) {
                "pacoca" -> R.drawable.pacoca
                "feijao_tropeiro" -> R.drawable.feijao_tropeiro
                "camarao_moranga" -> R.drawable.camarao_moranga
                "mousse_maracuja" -> R.drawable.mousse_maracuja
                "biscoito_para" -> R.drawable.biscoito_para
                else -> R.drawable.kelly_pool
            }

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(bottom = 8.dp))
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
                                color = Color.White
                            )
                        )
                        Text(
                            text = descricao!!,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.LightGray
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
