package com.example.projeto_jeitinho_brasileiro.ui.telas

import android.util.TypedValue
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.projeto_jeitinho_brasileiro.R
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario

@Composable
fun TelaAbout(
    modifier: Modifier = Modifier,
    usuario: Usuario,
    onLogoffClick: () -> Unit,
    onBackClick: () -> Unit,
    onImageClick: () -> Unit // Callback para quando a imagem de perfil é clicada
) {
    var showLogoffConfirmation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF176)) // Cor de fundo amarela clara, como na TelaLogin
            .padding(16.dp)
    ) {
        // Topo com botão de voltar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color(0xFF4CAF50) // Cor verde, como na TopAppBar da TelaLogin
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Informações do Usuário",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50) // Verde para o título
                )
            )
        }

        Spacer(modifier = Modifier.size(32.dp))

        // Conteúdo principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem de perfil interativa com Glide
            AndroidView(
                factory = { ctx ->
                    ImageView(ctx).apply {
                        val sizeInPx = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            128f,
                            ctx.resources.displayMetrics
                        ).toInt()

                        layoutParams = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams(
                            sizeInPx,
                            sizeInPx
                        )
                        clipToOutline = true // Para cortar na borda circular
                        Glide.with(ctx)
                            .load(usuario.foto)
                            .apply(RequestOptions().circleCrop()) // Aplica o corte circular
                            .placeholder(R.drawable.silhueta_perfil) // Imagem de placeholder
                            .into(this)
                    }
                },
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFF4CAF50), CircleShape) // Borda verde ao redor da imagem
                    .clickable { onImageClick() }
            )

            Spacer(modifier = Modifier.size(16.dp)) // Espaço entre a imagem e os textos

            // Exibição do nome do usuário
            Text(
                text = "Nome: ${usuario.nome}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5D4037) // Cor marrom para o texto
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Exibição do e-mail do usuário
            Text(
                text = "E-mail: ${usuario.email}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5D4037) // Cor marrom para o texto
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.size(24.dp))

            // Botão de Desconectar com o estilo verde
            Button(
                onClick = { showLogoffConfirmation = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Cor verde para o botão
            ) {
                Text(text = "Desconectar", fontSize = 18.sp, color = Color.White)
            }
        }
    }

    // Confirmação de logoff
    if (showLogoffConfirmation) {
        AlertDialog(
            onDismissRequest = { showLogoffConfirmation = false },
            title = { Text(text = "Deseja desconectar?") },
            text = { Text("Tem certeza que deseja desconectar?") },
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
}
