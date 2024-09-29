package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import kotlinx.coroutines.launch
import androidx.compose.material3.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(
    modifier: Modifier = Modifier,
    onLogoffClick: () -> Unit,
    usuario: Usuario,
    onUserClick: () -> Unit,
    onCartClick: () -> Unit // Callback para o carrinho
){
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    var showLogoffConfirmation by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
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
                        contentDescription = "Close Drawer",
                        modifier = Modifier.clickable { scope.launch { drawerState.close() } }
                    )
                }
                Divider()
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
        },
        gesturesEnabled = false
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
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
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        // Navegação para o carrinho
                        IconButton(onClick = onCartClick) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Carrinho"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TelaConteudo()
            }
        }
    }
}
