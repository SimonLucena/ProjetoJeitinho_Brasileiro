package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Cadastro
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import com.example.projeto_jeitinho_brasileiro.repositorio.user.UsuarioDAO
import com.example.projeto_jeitinho_brasileiro.usuarioDAO
import kotlinx.coroutines.delay

@Composable
fun TelaSignup(innerPadding: PaddingValues, onSigninClick: () -> Unit) {
    var user by  remember{ mutableStateOf<String?>("")}
    var login by  remember{mutableStateOf<String?>("")}
    var senha by  remember{mutableStateOf<String?>("")}
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
            text = "Cadastro"
        )
        OutlinedTextField(value = user.toString(), onValueChange = {user = it.ifEmpty { null }}, placeholder = {
            Text(text = "Usuário")
        })
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = login.toString(), onValueChange = {login = it}, placeholder = {
            Text(text = "E-mail")
        })
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = senha.toString(), visualTransformation = PasswordVisualTransformation(), onValueChange = {senha = it}, placeholder = {
            Text(text = "Senha")
        })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            when {
                user.isNullOrEmpty() || login.isNullOrEmpty() || senha.isNullOrEmpty() -> {
                    mensagemErro = "Preencha todos os campos"
                }
                else ->{
                    usuarioDAO.buscarPorLogin(login!!, callBack = { usuario ->
                        if(usuario != null && usuario.email == login){
                            mensagemErro = "Login já existe"
                        }
                        else{
                            usuarioDAO.cadastrarUsuarioDAO(user!!, login!!, senha!!)
                            onSigninClick()
                        }
                    })
                }
            }
        }) {
            Text(text = "Cadastrar")
        }

        mensagemErro?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
            LaunchedEffect(Unit) {
                delay(5000)
                mensagemErro = null
            }
        }
    }
}