package com.example.projeto_jeitinho_brasileiro.ui.telas;

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
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
import com.example.projeto_jeitinho_brasileiro.repositorio.receita.ReceitaDAO

// Dados de um item do carrinho
data class CartItem(
    var id: String? = null,
    val receita_id: String = "",  // Substituir name por receita_id
    var quantidade: Int = 1,      // Usar quantidade
    var price: Double = 0.0
) {
    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "receita_id" to receita_id,
            "quantidade" to quantidade,
            "price" to price
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCart(
    usuarioId: String,
    viewModel: CartViewModel,
    receitaDAO: ReceitaDAO,
    onCheckoutClick: () -> Unit,
    onBackClick: () -> Unit // jesuscristo
) {
    // Coletar o carrinho local do usuário
    val carrinhoUsuario by viewModel.carrinhoUsuario.collectAsState()

    // Estado para armazenar os nomes das receitas
    var receitasMap by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    // Buscar os itens do carrinho
    LaunchedEffect(usuarioId) {
        viewModel.fetchCartItems(usuarioId)

        // Carregar as receitas e mapear pelo ID
        receitaDAO.listarReceitas { receitas ->
            receitas?.let {
                receitasMap = it.associateBy({ it.indice.toString() }, { it.nome })
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrinho de Compras") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50), // Cor verde para a TopBar
                    titleContentColor = Color.White // Cor branca para o título
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Adicionando o padding para evitar que o conteúdo bata na TopBar
                    .background(Color(0xFFFFF176)) // Fundo amarelo claro
            ) {
                if (carrinhoUsuario == null) {
                    Text("Carregando carrinho...")
                } else {
                    // Lista de produtos no carrinho
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(carrinhoUsuario!!.itens.size) { index ->
                            val item = carrinhoUsuario!!.itens[index]
                            // Buscar o nome da receita pelo ID
                            val nomeReceita = receitasMap[item.receita_id] ?: "Receita Desconhecida"

                            CartItemCard(
                                cartItem = item.copy(receita_id = nomeReceita), // Usar o nome buscado
                                onIncreaseQuantity = {
                                    viewModel.addItemToCart(usuarioId, item.copy(quantidade = item.quantidade + 1))
                                },
                                onDecreaseQuantity = {
                                    if (item.quantidade > 1) {
                                        viewModel.addItemToCart(usuarioId, item.copy(quantidade = item.quantidade - 1))
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
    )
}


@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemoveItem: () -> Unit
) {
    val imageResId = when (cartItem.receita_id) {
        "Paçoca" -> R.drawable.pacoca
        "Feijão Tropeiro" -> R.drawable.feijao_tropeiro
        "Camarão na Moranga" -> R.drawable.camarao_moranga
        else -> R.drawable.silhueta_perfil
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFECB3)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(bottom = 8.dp))
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = cartItem.receita_id,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = cartItem.receita_id,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D4037)
                )
                Text(
                    text = "R$ ${"%.2f".format(cartItem.price)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onIncreaseQuantity) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Aumentar quantidade")
                }
                Text(text = cartItem.quantidade.toString(), style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = onDecreaseQuantity) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Diminuir quantidade")
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = onRemoveItem, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) {
                Text("Remover", color = Color.White)
            }
        }
    }
}
