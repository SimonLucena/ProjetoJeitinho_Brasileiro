package com.example.projeto_jeitinho_brasileiro.ui.telas;

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto_jeitinho_brasileiro.R
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import com.example.projeto_jeitinho_brasileiro.repositorio.user.UsuarioDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Carregando a fonte Oswald do Google Fonts
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val fontName = GoogleFont("Oswald")
val fontFamily = FontFamily(Font(googleFont = fontName, fontProvider = provider))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(innerPadding: PaddingValues, onSigninClick: (Usuario) -> Unit, onSignupClick: () -> Unit) {
    val usuarioDAO: UsuarioDAO = UsuarioDAO()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color(0xFFFFF176), // Amarelo claro como fundo principal
        topBar = {
            TopAppBar(
                title = {

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF4CAF50)) // Cor verde
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF4CAF50), // Cor verde
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Descubra o sabor único da culinária brasileira!",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFF176)), // Cor de fundo amarelo claro
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Banner no topo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Ajustando a altura do banner
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner), // Substitua pelo seu banner
                    contentDescription = "Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Texto sobreposto ao banner, centralizado e justificado
                Text(
                    text = "CADA PRATO CONTA UMA HISTÓRIA, REVELANDO TRADIÇÕES, INGREDIENTES LOCAIS E TÉCNICAS CULINÁRIAS ÚNICAS.",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily, // Usar a fonte Oswald do Google Fonts
                        color = Color.White // Texto branco sobre o banner
                    ),
                    modifier = Modifier
                        .align(Alignment.Center) // Centraliza o texto no meio do banner
                        .padding(horizontal = 16.dp), // Padding para dar espaço lateral ao texto
                    textAlign = TextAlign.Justify // Justificar o texto
                )
            }

            // Logo e Nome do Aplicativo
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.braza), // Substitua pelo seu logo
                contentDescription = "Logo do Aplicativo",
                modifier = Modifier
                    .height(100.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "Jeitinho Brasileiro",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D4037) // Marrom para combinar com a paleta
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de texto para o login
            TextField(
                value = login,
                onValueChange = { login = it.trim() },
                placeholder = { Text(text = "E-mail") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFECB3), // Fundo creme
                    focusedIndicatorColor = Color(0xFF4CAF50), // Indicador verde
                    unfocusedIndicatorColor = Color(0xFF4CAF50) // Indicador verde
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 10.dp)
            )

            // Campo de texto para a senha
            TextField(
                value = senha,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { senha = it },
                placeholder = { Text(text = "Senha") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFECB3), // Fundo creme
                    focusedIndicatorColor = Color(0xFF4CAF50), // Indicador verde
                    unfocusedIndicatorColor = Color(0xFF4CAF50) // Indicador verde
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 10.dp)
            )

            // Botões de Entrar e Cadastrar
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 16.dp)
            ) {
                // Botão de Entrar
                Button(
                    onClick = {
                        // Lógica de login
                        scope.launch(Dispatchers.IO) {
                            usuarioDAO.buscarPorLogin(login) { usuario ->
                                if (usuario != null && usuario.senha == senha) {
                                    onSigninClick(usuario)
                                } else {
                                    mensagemErro = "Login ou senha inválidos"
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(end = 8.dp), // Espaço entre os botões
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde
                ) {
                    Text(text = "Entrar", color = Color.White)
                }

                // Botão de Cadastrar
                Button(
                    onClick = {
                        // Ação de cadastro
                        onSignupClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(start = 8.dp), // Espaço entre os botões
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde
                ) {
                    Text(text = "Cadastrar", color = Color.White)
                }
            }

            // Exibir mensagem de erro, se houver
            mensagemErro?.let {
                LaunchedEffect(it) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    mensagemErro = null // Limpa o erro após exibir
                }
            }
        }
    }
}
