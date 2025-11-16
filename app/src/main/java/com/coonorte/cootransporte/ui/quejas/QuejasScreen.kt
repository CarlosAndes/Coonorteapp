package com.coonorte.cootransporte.ui.quejas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuejasScreen() {

    var cedula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    val correo = "contacto@coonorte.com"
    var mensaje by remember { mutableStateOf("") }

    var enviando by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf<String?>(null) }
    var tipoResultado by remember { mutableStateOf("exito") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "📢 Buzón de Quejas y Sugerencias",
            fontSize = 22.sp,
            color = Color(0xFF0A7E34),
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Tu opinión es muy importante para nosotros. Cuéntanos tu queja o sugerencia.",
            fontSize = 15.sp
        )

        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = cedula,
            onValueChange = { cedula = it },
            label = { Text("Cédula *") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo *") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {},
            readOnly = true,
            label = { Text("Correo de contacto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            label = { Text("Queja o sugerencia *") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )

        Spacer(Modifier.height(25.dp))

        Button(
            onClick = {
                if (cedula.isBlank() || nombre.isBlank() || mensaje.isBlank()) {
                    tipoResultado = "error"
                    resultado = "❌ Por favor completa todos los campos obligatorios."
                    return@Button
                }

                scope.launch {
                    enviando = true
                    delay(1500)  // simulación

                    tipoResultado = "exito"
                    resultado = "✅ Tu queja ha sido enviada correctamente."

                    cedula = ""
                    nombre = ""
                    mensaje = ""

                    enviando = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0A7E34))
        ) {
            Text(text = if (enviando) "Enviando..." else "Enviar Queja",
                color = Color.White,
                fontSize = 17.sp
            )
        }

        Spacer(Modifier.height(20.dp))

        resultado?.let { msg ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (tipoResultado == "exito") Color(0xFFDFF6DD) else Color(0xFFFFE0E0),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(15.dp)
            ) {
                Text(
                    text = msg,
                    color = if (tipoResultado == "exito") Color(0xFF0A7E34) else Color.Red,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
