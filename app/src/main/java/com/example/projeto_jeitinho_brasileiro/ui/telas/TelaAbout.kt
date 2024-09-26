package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario

@Composable
fun TelaAbout(
    usuario: Usuario,
    onLogoffClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Informações do Usuário",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Nome: ${usuario.nome}",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "E-mail: ${usuario.email}",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = onLogoffClick) {
            Text(text = "Sair", fontSize = 18.sp)
        }
    }
}