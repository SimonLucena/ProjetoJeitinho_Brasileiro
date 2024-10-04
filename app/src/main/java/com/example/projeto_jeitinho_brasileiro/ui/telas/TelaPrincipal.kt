package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(
    modifier: Modifier = Modifier,
    onLogoffClick: () -> Unit,
    usuario: Usuario,
    onUserClick: () -> Unit,
    onCartClick: () -> Unit // Callback para o carrinho
) {
    var showLogoffConfirmation by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Dialog para confirmação de logoff
    if (showLogoffConfirmation) {
        AlertDialog(
            onDismissRequest = { showLogoffConfirmation = false },
            title = { Text("Deseja sair?") },
            text = { Text("Tem certeza que deseja voltar para o login?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoffConfirmation = false
                    onLogoffClick()
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoffConfirmation = false }) {
                    Text("Não")
                }
            }
        )
    }

    // Drawer sem mudanças de cores, retornando ao original
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Cabeçalho do Drawer com o nome do usuário
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = usuario.nome,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Fechar Drawer",
                        modifier = Modifier.clickable { scope.launch { drawerState.close() } }
                    )
                }
                Divider()
                // Itens do Drawer
                NavigationDrawerItem(
                    label = { Text(text = "Usuário") },
                    selected = false,
                    onClick = { onUserClick() }
                )
                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(
                    label = { Text(text = "Sair") },
                    selected = false,
                    onClick = { showLogoffConfirmation = true }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF4CAF50), // Cor verde para a TopBar
                        titleContentColor = Color.White, // Cor branca para o título
                    ),
                    title = { Text("Jeitinho Brasileiro") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu",
                                tint = Color.White // Ícone branco
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onCartClick) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Carrinho",
                                tint = Color.White // Ícone branco
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White), // Fundo branco para o conteúdo
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TelaConteudo() // Conteúdo da tela
            }
        }
    }
}
