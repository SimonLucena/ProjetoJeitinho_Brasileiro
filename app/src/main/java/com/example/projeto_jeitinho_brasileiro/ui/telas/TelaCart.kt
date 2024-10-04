package com.example.projeto_jeitinho_brasileiro.ui.telas;

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.runtime.LaunchedEffect // Importar LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projeto_jeitinho_brasileiro.R
import com.example.projeto_jeitinho_brasileiro.ViewModel.Carrinho.CartViewModel
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.Receita
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.ReceitaDAO

// Dados de um item do carrinho
data class CartItem(
    var id: String? = null,
    val receita_id: String = "",  // Substituir name por receita_id
    var quantidade: Int = 1,      // Usar quantidade, não quantity
    var price: Double = 0.0
) {
    // Transforma o item em um Map para ser enviado ao Firestore
    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "receita_id" to receita_id,
            "quantidade" to quantidade,
            "price" to price
        )
    }
}

@Composable
fun TelaCart(
    modifier: Modifier,
    usuarioId: String,
    viewModel: CartViewModel,
    receitaDAO: ReceitaDAO,
    onCheckoutClick: () -> Unit
) {
    // Coletar o carrinho local do usuário
    val carrinhoUsuario by viewModel.carrinhoUsuario.collectAsState()

    // Estado para armazenar os nomes das receitas
    var receitasMap by remember { mutableStateOf<Map<String, Pair<String, Double>>>(emptyMap()) }

    // Buscar os itens do carrinho
    LaunchedEffect(usuarioId) {
        viewModel.fetchCartItems(usuarioId)

        // Carregar as receitas e mapear pelo ID
        receitaDAO.listarReceitas { receitas ->
            receitas?.let {
                receitasMap = it.associateBy({ it.id ?: "" }, { Pair(it.nome ?: "Nome desconhecido", it.preco ?: 0.0) })
            }
        }
    }


    // Se o carrinho estiver nulo, mostrar um texto de carregamento
    if (carrinhoUsuario == null) {
        Text("Carregando carrinho...")
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFFF176)) // Fundo amarelo claro
                .padding(16.dp)
        ) {
            Text(
                text = "Carrinho de Compras",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50), // Título em verde
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Lista de produtos no carrinho
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Dentro da renderização da lista de itens do carrinho
                items(carrinhoUsuario!!.itens.size) { index ->
                    val item = carrinhoUsuario!!.itens[index]

                    // Buscar o nome e o preço da receita pelo ID
                    val (nomeReceita, precoReceita) = receitasMap[item.receita_id] ?: Pair("Receita Desconhecida", 0.0)

                    CartItemCard(
                        cartItem = item.copy(receita_id = nomeReceita), // Agora estamos usando o nome da receita no lugar do id
                        receitaPreco = precoReceita, // Passa o preço correto da receita
                        onIncreaseQuantity = {
                            viewModel.updateItemQuantity(usuarioId, item.id, item.quantidade + 1)
                        },
                        onDecreaseQuantity = {
                            if (item.quantidade > 1) {
                                viewModel.updateItemQuantity(usuarioId, item.id, item.quantidade - 1)
                            }
                        },
                        onRemoveItem = {
                            viewModel.removeItemFromCart(usuarioId, item.id)
                        }
                    )
                }

            }


            Spacer(modifier = Modifier.padding(16.dp))

            // Exibir o total e o botão de finalizar compra
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Total: R$ ${"%.2f".format(viewModel.calcularTotal())}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D4037), // Marrom para combinar com a paleta
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        // Simula o checkout
                        viewModel.registrarCompra(usuarioId)
                        viewModel.limparCarrinho(usuarioId)
                        onCheckoutClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde para o botão
                ) {
                    Text(text = "Finalizar Compra", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    receitaPreco: Double, // Recebe o preço da receita diretamente
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemoveItem: () -> Unit
) {
    // Associa a imagem ao nome do produto
    val imageResId = when (cartItem.receita_id) {
        "Paçoca" -> R.drawable.pacoca
        "Feijão Tropeiro" -> R.drawable.feijao_tropeiro
        "Camarão na Moranga" -> R.drawable.camarao_moranga
        else -> R.drawable.silhueta_perfil
    }

    // Card de cada item no carrinho
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFECB3)), // Cor creme para o fundo do card
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(bottom = 8.dp))
    ) {
        Row(modifier = Modifier.padding(16.dp)) {

            // Nome e preço do item
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = cartItem.receita_id, // Nome da receita
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D4037) // Marrom para o texto
                )
                Text(
                    text = "R$ ${"%.2f".format(receitaPreco * cartItem.quantidade)}", // Usar o preço da receita multiplicado pela quantidade
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Botões para alterar quantidade
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onIncreaseQuantity) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Aumentar quantidade")
                }
                Text(
                    text = cartItem.quantidade.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF5D4037)
                )
                IconButton(onClick = onDecreaseQuantity) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Diminuir quantidade")
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Botão para remover o item
            Button(onClick = onRemoveItem, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) {
                Text("Remover", color = Color.White)
            }
        }
    }
}



// Função para calcular o preço total do carrinho com base na receita e quantidade
fun calculateTotalPrice(items: List<CartItem>, receitasMap: Map<String, Receita>): Double {
    return items.sumOf { item ->
        val receita = receitasMap[item.receita_id]
        (receita?.preco ?: 0.0) * item.quantidade // Calcula o total com base no preço da receita
    }
}