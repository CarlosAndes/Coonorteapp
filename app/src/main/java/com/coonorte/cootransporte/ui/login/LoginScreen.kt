package com.coonorte.cootransporte.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.coonorte.cootransporte.R

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onGoRegister: () -> Unit
) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.bus_coonorte),
            contentDescription = "Imagen Login",
            modifier = Modifier.size(220.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Iniciar Sesión",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Correo o Nombre", color = Color(0xFF006400))

        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ingresa tu correo o nombre") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Contraseña", color = Color(0xFF006400))

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("•••••••") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onLogin(user, pass) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A7E34)),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Ingresar", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "¿No tienes cuenta? Regístrate aquí",
            color = Color(0xFF0A7E34),
            modifier = Modifier.clickable { onGoRegister() }
        )
    }
}
