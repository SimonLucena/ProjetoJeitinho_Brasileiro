package com.example.projeto_jeitinho_brasileiro.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import kotlinx.coroutines.delay

@Composable
fun TelaSignup(
    innerPadding: PaddingValues,
    onSigninClick: () -> Unit,
    signupClick: (String, String, String) -> Unit,
    erro: String?
) {
    var user by  remember{ mutableStateOf<String?>("")}
    var login by  remember{mutableStateOf<String?>("")}
    var senha by  remember{mutableStateOf<String?>("")}
    var mensagemErro by  remember{mutableStateOf<String?>(erro)}

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
        OutlinedTextField(value = user.toString(), onValueChange = {user = it}, placeholder = {
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
        Row {
            Column {
                Button(onClick = {
                    when {
                        user.isNullOrEmpty() || login.isNullOrEmpty() || senha.isNullOrEmpty() -> {
                            mensagemErro = "Preencha todos os campos"
                        }
                        else ->{
                            signupClick(user!!, login!!, senha!!)
                        }
                    }
                }) {
                    Text(text = "Cadastrar")
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Button(onClick = {
                    onSigninClick()
                }) {
                    Text(text = "Cancelar")
                }
            }
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