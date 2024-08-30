package com.example.projeto_jeitinho_brasileiro.ui.telas

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.projeto_jeitinho_brasileiro.R

import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(modifier: Modifier = Modifier, onLogoffClick: () -> Unit){
//    val usuario =
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Jeitinho Brasileiro")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
              
                    }
                    IconButton(onClick = {menuExpanded = !menuExpanded })
                    { Icon(imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu" )
                        
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Refresh") },
                            onClick = { /*TODO*/ }
                        )
                        DropdownMenuItem(
                            text = {
                                Row {
                                    Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout" )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "Logout")
                                }
                            },
                            onClick = { onLogoffClick() }
                        )
                    }

                }
//                navigationIcon = {
//                    IconButton(onClick = { onLogoffClick() }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.logout),
//                            contentDescription = "Logout",
//                        )
//                    }
//                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    textAlign = TextAlign.Center,
                    text = "Por: Simon Lucena de Castro",
                )
            }
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