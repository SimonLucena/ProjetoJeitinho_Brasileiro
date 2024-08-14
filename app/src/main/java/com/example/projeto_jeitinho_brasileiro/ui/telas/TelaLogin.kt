package com.example.projeto_jeitinho_brasileiro.ui.telas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Cadastro
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import com.example.projeto_jeitinho_brasileiro.repositorio.user.UsuarioDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val usuarioDAO: UsuarioDAO = UsuarioDAO()
@Composable
fun TelaLogin(innerPadding: PaddingValues, onSigninClick: () -> Unit, onSignupClick: () -> Unit, cadastro: Cadastro) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var login by  remember{mutableStateOf("")}
    var senha by  remember{mutableStateOf("")}
    var mensagemErro by remember { mutableStateOf<String?>("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            ),
            text = "Login"
        )
        TextField(value = login, onValueChange = {login = it}, placeholder = {
            Text(text = "E-mail")
        })
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = senha, visualTransformation = PasswordVisualTransformation(), onValueChange = {senha = it}, placeholder = {
            Text(text = "Senha")
        })
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Column {
                Button(onClick = {
                    scope.launch(Dispatchers.IO) {
                        usuarioDAO.buscarPorLogin(login, callBack = { usuario ->
                            if(usuario != null && usuario.senha == senha){
                                onSigninClick()
                            }else{
                                mensagemErro = "Login ou senha inválidos"
                            }
                        })
                    }
                }) {
                    Text(text = "Entrar")
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Button(onClick = {
                    onSignupClick()
                }) {
                    Text(text = "Cadastrar")
                }
            }
        }
        mensagemErro?.let {
//            Text(
//                text = it,
//                color = MaterialTheme.colorScheme.error
//            )
            LaunchedEffect(it) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//                delay(5000)
                mensagemErro = null
            }
        }
    }
}