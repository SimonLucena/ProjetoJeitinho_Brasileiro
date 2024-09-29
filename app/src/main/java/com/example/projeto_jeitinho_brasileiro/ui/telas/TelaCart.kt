package com.example.projeto_jeitinho_brasileiro.ui.telas

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projeto_jeitinho_brasileiro.R

// Dados de um item do carrinho
data class CartItem(val name: String, val price: Double, var quantity: Int)

@Composable
fun TelaCart(
    onCheckoutClick: () -> Unit
) {
    // Lista provisória de itens no carrinho
    val items = remember {
        mutableStateListOf(
            CartItem("Produto A", 10.0, 1),
            CartItem("Produto B", 20.0, 2),
            CartItem("Produto C", 30.0, 1)
        )
    }

    // Estado para o preço total
    val totalPrice = remember { mutableStateOf(calculateTotalPrice(items)) }

    // Layout da tela do carrinho
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Carrinho de Compras",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de produtos no carrinho
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items.size) { index ->
                val item = items[index]
                CartItemCard(
                    cartItem = item,
                    onIncreaseQuantity = {
                        item.quantity++
                        totalPrice.value = calculateTotalPrice(items)
                    },
                    onDecreaseQuantity = {
                        if (item.quantity > 1) {
                            item.quantity--
                            totalPrice.value = calculateTotalPrice(items)
                        }
                    },
                    onRemoveItem = {
                        items.removeAt(index) // Remover o item
                        totalPrice.value = calculateTotalPrice(items)
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
                text = "Total: R$ ${"%.2f".format(totalPrice.value)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(onClick = onCheckoutClick) {
                Text(text = "Finalizar Compra")
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemoveItem: () -> Unit
) {
    // Associa a imagem ao nome do produto
    val imageResId = when (cartItem.name) {
        "Produto A" -> R.drawable.pacoca
        "Produto B" -> R.drawable.feijao_tropeiro
        "Produto C" -> R.drawable.camarao_moranga
        else -> R.drawable.silhueta_perfil
    }

    // Card de cada item no carrinho
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xfffdfdfd)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(bottom = 8.dp))
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Imagem do item
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = cartItem.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Nome e preço do item
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = cartItem.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "R$ ${"%.2f".format(cartItem.price)}",
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
                Text(text = cartItem.quantity.toString(), style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = onDecreaseQuantity) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Diminuir quantidade")
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Botão para remover o item
            Button(onClick = onRemoveItem) {
                Text("Remover")
            }
        }
    }
}

// Função para calcular o preço total do carrinho
fun calculateTotalPrice(items: List<CartItem>): Double {
    return items.sumOf { it.price * it.quantity }
}
