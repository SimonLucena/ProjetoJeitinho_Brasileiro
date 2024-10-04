package com.example.projeto_jeitinho_brasileiro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaCart;
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projeto_jeitinho_brasileiro.ViewModel.Carrinho.CartViewModel
import com.example.projeto_jeitinho_brasileiro.ViewModel.usuario.UsuarioViewModel
import com.example.projeto_jeitinho_brasileiro.repositorio.user.UsuarioDAO
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaAbout
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaLogin
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaPrincipal
import com.example.projeto_jeitinho_brasileiro.ui.telas.TelaSignup
import com.example.projeto_jeitinho_brasileiro.ui.theme.ProjetoJeitinho_BrasileiroTheme

class MainActivity : ComponentActivity() {
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private val PICK_IMAGES_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Configuração do ActivityResultLauncher para a seleção de imagem
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    uploadImageToFirebase(it)
                }
            }
        }


        setContent {
            ProjetoJeitinho_BrasileiroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val usuarioDAO = UsuarioDAO()
                    val viewmodel: UsuarioViewModel = viewModel()
                    val usuarioState by viewmodel.usuario.collectAsState()

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            TelaLogin(innerPadding,
                                onSigninClick = { usuario ->
                                    viewmodel.login(usuario)
                                    navController.navigate("principal")
                                },
                                onSignupClick = {
                                    navController.navigate("signup")
                                }
                            )
                        }
                        composable("signup") {
                            var erro by remember { mutableStateOf<String?>(null) }

                            TelaSignup(innerPadding,
                                onSigninClick = { navController.navigate("login") },
                                signupClick = { user, login, senha ->
                                    usuarioDAO.buscarPorLogin(login, callBack = { usuario ->
                                        if (usuario != null && usuario.email == login) {
                                            erro = "Login já existe"
                                        } else {
                                            usuarioDAO.cadastrarUsuarioDAO(user, login, senha)
                                            navController.navigate("login")
                                        }
                                    })
                                },
                                erro = erro
                            )
                        }
                        composable("principal") {
                            usuarioState?.let { it1 ->
                                TelaPrincipal(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .background(Color.White),
                                    onLogoffClick = {
                                        viewmodel.logout()
                                        navController.navigate("login")
                                    },
                                    onUserClick = {
                                        navController.navigate("sobreUsuario")
                                    },
                                    onCartClick = {
                                        navController.navigate("carrinho") // Navegação para o carrinho
                                    },
                                    usuario = it1
                                )
                            }
                        }
                        composable("sobreUsuario") {
                            usuarioState?.let { it1 ->
                                TelaAbout(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .background(Color.White),
                                    onLogoffClick = {
                                        viewmodel.logout()
                                        navController.navigate("login")
                                    },
                                    usuario = it1,
                                    onBackClick = {
                                        navController.navigate("principal")
                                    },
                                    onImageClick = { openImagePicker() } // Integração com o processo de captura/seleção de imagem
                                )
                            }
                        }
                        // Rota para o carrinho
                        composable("carrinho") {
                            usuarioState?.let { it1 ->
                                val cartViewModel: CartViewModel = viewModel()

                                val usuarioId = it1.indice

                                TelaCart(
                                    usuarioId = usuarioId,
                                    viewModel = cartViewModel,
                                    onCheckoutClick = {

                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    // Função que abre o PhotoPicker ou a câmera
    private fun openImagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
            intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 1)
            imagePickerLauncher.launch(intent)
        } else {
            // Implementação para Android abaixo de 14
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }
    }

    // Função para lidar com o upload da imagem no Firebase
    private fun uploadImageToFirebase(imageUri: Uri) {
        val viewmodel: UsuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)
        viewmodel.uploadImageToFirebaseStorage(imageUri)
    }
}
