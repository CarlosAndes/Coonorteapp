package com.coonorte.cootransporte.ui.encomiendas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coonorte.cootransporte.data.network.RetrofitClient
import com.coonorte.cootransporte.data.network.response.EncomiendaResponse
import kotlinx.coroutines.launch

@Composable
fun EncomiendasScreen() {

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    var cedula by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }
    var consultado by remember { mutableStateOf(false) }
    var lista by remember { mutableStateOf(listOf<EncomiendaResponse>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "📦 Consulta de Envíos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = cedula,
            onValueChange = { cedula = it },
            label = { Text("Ingrese su cédula") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

        Button(
            onClick = {
                if (cedula.isBlank()) {
                    Toast.makeText(ctx, "Ingrese su cédula", Toast.LENGTH_LONG).show()
                    return@Button
                }

                cargando = true
                consultado = true

                scope.launch {
                    try {
                        val api = RetrofitClient.instance
                        val res = api.getEncomiendas(cedula)
                        lista = res
                    } catch (e: Exception) {
                        Toast.makeText(ctx, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    cargando = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A4CE5)
            )
        ) {
            Text("Consultar", color = Color.White, fontSize = 18.sp)
        }

        Spacer(Modifier.height(20.dp))

        // ================= MENSAJES =================
        if (cargando) {
            Text("Cargando...", fontSize = 16.sp)
        }

        if (!cargando && consultado && lista.isEmpty()) {
            Text("No se encontraron encomiendas para esta cédula")
        }

        // ================= LISTA =================
        lista.forEach { item ->
            EncomiendaCard(item)
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun EncomiendaCard(item: EncomiendaResponse) {

    val reclamado = item.reclamado == 1  // 🔥 CAMBIO IMPORTANTE

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4))
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text("📦 Envío #${item.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(Modifier.height(10.dp))

            Text("Estado: ${if (reclamado) "Entregado ✅" else "En camino 🚚"}")
            Text("Origen: ${item.lugarRemitente}")
            Text("Destino: ${item.lugarEntrega}")
            Text("Fecha envío: ${item.fechaEnvio}")

            Spacer(Modifier.height(10.dp))

            Divider()

            Spacer(Modifier.height(10.dp))

            Text("Remitente: ${item.nombreRemitente} (${item.cedulaRemitente})")
            Text("Destinatario: ${item.nombreDestinatario} (${item.cedulaDestinatario})")
        }
    }
}
