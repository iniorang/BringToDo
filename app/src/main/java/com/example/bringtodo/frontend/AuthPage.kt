package com.example.bringtodo.frontend

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bringtodo.PreferencesManager
import com.example.bringtodo.backend.controller.AuthController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPage(navController: NavController, modifier: Modifier = Modifier, context: Context = LocalContext.current){
    val preferencesManager = remember { PreferencesManager(context = context) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var isLogin by remember { mutableStateOf(true) }


    Scaffold (
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray)
                .padding(30.dp)
                .size(10.dp, 10.dp)

        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
                    .size(10.dp, 10.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(text = if (isLogin) "Login" else "Register", modifier = Modifier
                    .padding(25.dp)
                    ,fontSize = 40.sp
                    , fontFamily = FontFamily.SansSerif
                    , fontStyle = FontStyle.Normal

                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { newText -> username = newText },
                        label = {
                            Text(
                                text = "Username",
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    if (!isLogin) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { newText -> email = newText },
                            label = {
                                Text(
                                    text = "Email",
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }
                    OutlinedTextField(
                        value = password,
                        onValueChange = { newText -> password = newText },
                        label = { Text("Password") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = { passwordVisibility = !passwordVisibility }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = {
                            if (isLogin) {
                                AuthController.login(username.text, password.text, navController, preferencesManager) {}
                            } else {
                                AuthController.register(email.text, username.text, password.text, navController, preferencesManager) {}
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = if (isLogin) "Login" else "Register")
                    }
                    ClickableText(
                        text = AnnotatedString(if (isLogin) "Daftar" else "Login"),
                        onClick = {
                            isLogin = !isLogin
                        },
                    )
                }
            }
        }
    }

}