package com.example.sewastudio.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bringtodo.PreferencesManager
import com.example.bringtodo.R
import com.example.bringtodo.backend.controller.AuthController

@Composable
fun AuthUI(navController: NavController, modifier: Modifier = Modifier, context: Context = LocalContext.current) {
    val preferencesManager = remember { PreferencesManager(context = context) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var isLogin by remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = R.drawable.background),
//            contentDescription = "",
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier.matchParentSize()
//        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp)
        ) {
            Text(text = if (isLogin) "Login Here" else "Register", modifier = Modifier
                .padding(25.dp)
                ,fontSize = 40.sp
                , fontFamily = FontFamily.SansSerif
                , fontStyle = FontStyle.Normal
                , fontWeight = FontWeight(700)
            )

            Spacer(modifier = Modifier.padding(top = 120.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(29.dp, Alignment.Top),
            ) {
                TextField(
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
                        .height(64.dp)
                        .background(color = Color(0xFFF1F4FF), shape = RoundedCornerShape(size = 2.dp))
                        .border(width = 2.dp, color = Color(0xFF1F41BB), shape = RoundedCornerShape(size = 8.dp)))
                if (!isLogin) {
                    TextField(
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
                            .height(64.dp)
                            .background(color = Color(0xFFF1F4FF), shape = RoundedCornerShape(size = 2.dp))
                            .border(width = 2.dp, color = Color(0xFF1F41BB), shape = RoundedCornerShape(size = 8.dp))
                    )
                }
                TextField(
                    value = password,
                    onValueChange = { newText -> password = newText },
                    label = {
                        Text(
                            text = "Password",
                        )
                    },
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
                        .padding(bottom = 8.dp)
                        .height(64.dp)
                        .background(color = Color(0xFFF1F4FF), shape = RoundedCornerShape(size = 2.dp))
                        .border(width = 2.dp, color = Color(0xFF1F41BB), shape = RoundedCornerShape(size = 8.dp)))

//                    if (!isLogin) {
//                        Row {
//                            roleOptions.forEach { text -> Row {
//                                Row (verticalAlignment = Alignment.CenterVertically){
//                                    RadioButton(selected = (text == selectedOption), onClick = {
//                                        onOptionSelected(text)
//                                    })
//                                    Text(
//                                        text = text,
//                                    )
//                                }
//                            } }
//                        }
//                    }
                Button(
                    onClick = {
                        if (isLogin) {
                            AuthController.login(username.text, password.text, navController, preferencesManager) {}
                        } else {
                            AuthController.register(email.text, username.text, password.text, navController, preferencesManager) {}
                        }
                    },
                    modifier = Modifier
                        .shadow(elevation = 20.dp, spotColor = Color(0xFFCBD6FF), ambientColor = Color(0xFFCBD6FF))
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(color = Color(0xFF1F41BB), shape = RoundedCornerShape(size = 10.dp))
                        .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F41BB))
                ) {
                    Text(text = if (isLogin) "Login" else "Register",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        ) )
                }
                ClickableText(
                    text = AnnotatedString(if (isLogin) "Don't have an account?" else "Already registered?"),
                    onClick = {
                        isLogin = !isLogin
                    },
                )
            }

        }
    }
}