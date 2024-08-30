package com.example.projeto_jeitinho_brasileiro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Cadastro
import com.example.projeto_jeitinho_brasileiro.repositorio.user.Usuario
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaLogin
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaPrincipal
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaSignup
import com.example.projeto_jeitinho_brasileiro.ui.theme.ProjetoJeitinho_BrasileiroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoJeitinho_BrasileiroTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            TelaLogin(innerPadding,
                                onSigninClick = {
                                    navController.navigate("principal")
                                },
                                onSignupClick = {
                                    navController.navigate("signup")
                                }
                            )
                        }
                        composable("signup") {
                            TelaSignup(innerPadding,
                                onSigninClick = {
                                    navController.navigate("login")
                                },
                                cancelarSignupClick = {
                                    navController.navigate("login")
                                }
                            )
                        }
                        composable("principal") {
                            TelaPrincipal(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .background( Color.White ),
                                onLogoffClick = {
                                    navController.navigate("login")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetoJeitinho_BrasileiroTheme {
//        Greeting("Android")
    }
}