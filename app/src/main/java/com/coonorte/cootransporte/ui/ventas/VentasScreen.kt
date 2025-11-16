package com.coonorte.cootransporte.ui.ventas

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coonorte.cootransporte.data.network.RetrofitClient
import com.coonorte.cootransporte.data.network.request.VentaRequest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

/* ============================================================
   UTIL: quitar tildes para que coincida con la API / DB
============================================================ */
fun limpiarTildes(texto: String): String {
    return texto
        .replace("á", "a")
        .replace("é", "e")
        .replace("í", "i")
        .replace("ó", "o")
        .replace("ú", "u")
        .replace("Á", "A")
        .replace("É", "E")
        .replace("Í", "I")
        .replace("Ó", "O")
        .replace("Ú", "U")
        .replace("ñ", "n")
        .replace("Ñ", "N")
}

/* ============================================================
   PANTALLA PRINCIPAL DE VENTAS
============================================================ */

@Composable
fun VentasScreen() {

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }

    // 👇 Fecha que se enviará a la API (yyyy-MM-dd)
    var fechaApi by remember { mutableStateOf("") }

    var documento by remember { mutableStateOf("") }
    var pasajeros by remember { mutableStateOf(1) }

    // 👉 lista que recibirá los asientos seleccionados
    val asientosSeleccionados = remember { mutableStateListOf<Int>() }

    // IMPORTANTE: usa los mismos nombres que tu backend
    val rutas = listOf("Medellin", "Andes", "Jardin", "Caldas", "Amaga")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),   // ✅ ahora toda la pantalla tiene scroll
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Comprar tiquete",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0A7E34)
        )

        Spacer(Modifier.height(20.dp))

        Selector("Origen", origen, rutas) { origen = it }
        Spacer(Modifier.height(10.dp))

        Selector("Destino", destino, rutas) { destino = it }
        Spacer(Modifier.height(10.dp))

        Selector("Hora", hora, listOf("05:00", "08:00", "10:00", "14:00", "16:00", "18:00")) {
            hora = it
        }
        Spacer(Modifier.height(10.dp))

        // Fecha bonita en pantalla, pero API recibe yyyy-MM-dd
        FechaSelector("Fecha de viaje") { fechaApi = it }
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = documento,
            onValueChange = { documento = it },
            label = { Text("Número de documento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        Selector("Pasajeros", pasajeros.toString(), (1..10).map { it.toString() }) {
            pasajeros = it.toInt()
            // si bajan el número de pasajeros y hay más asientos seleccionados, recorto
            if (asientosSeleccionados.size > pasajeros) {
                while (asientosSeleccionados.size > pasajeros) {
                    asientosSeleccionados.removeLast()
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // =============== BOTÓN COMPRAR ===============
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A7E34)),
            onClick = {

                if (
                    origen.isBlank() ||
                    destino.isBlank() ||
                    hora.isBlank() ||
                    fechaApi.isBlank() ||
                    documento.isBlank()
                ) {
                    Toast.makeText(
                        ctx,
                        "Todos los campos son obligatorios",
                        Toast.LENGTH_LONG
                    ).show()
                    return@Button
                }

                if (asientosSeleccionados.isEmpty()) {
                    Toast.makeText(
                        ctx,
                        "Selecciona al menos un asiento",
                        Toast.LENGTH_LONG
                    ).show()
                    return@Button
                }

                if (asientosSeleccionados.size != pasajeros) {
                    Toast.makeText(
                        ctx,
                        "Debes seleccionar exactamente $pasajeros asiento(s)",
                        Toast.LENGTH_LONG
                    ).show()
                    return@Button
                }

                val venta = VentaRequest(
                    usuario_id = 1,
                    referencia = "TKT-" + Date().time.toString().takeLast(6),
                    comprador = "Cliente App",
                    documento = documento,
                    origen = limpiarTildes(origen),
                    destino = limpiarTildes(destino),
                    hora = hora,
                    fecha = fechaApi,
                    fechaCompra = fechaApi,
                    horaCompra = hora,
                    modoPago = "efectivo",
                    asientos = asientosSeleccionados.toList(),
                    precio = 30000 * pasajeros,
                    pasajeros = pasajeros
                )

                scope.launch {
                    try {
                        val api = RetrofitClient.instance
                        api.registrarVenta(venta)
                        Toast.makeText(ctx, "Compra registrada!", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            ctx,
                            "Error al registrar venta: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        ) {
            Text("Comprar", color = Color.White, fontSize = 18.sp)
        }

        Spacer(Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Leyenda(Color(0xFF0A7E34), "Disponible")
            Leyenda(Color.Yellow, "Seleccionado")
            Leyenda(Color.Red, "Ocupado")
        }

        Spacer(Modifier.height(20.dp))

        // 🟩 Mapa de asientos conectado a la API
        AsientosMapa(
            pasajeros = pasajeros,
            origen = origen,
            destino = destino,
            hora = hora,
            fechaApi = fechaApi,
            asientosSeleccionados = asientosSeleccionados
        )
    }
}

/* ============================================================
   SELECTOR GENÉRICO
============================================================ */

@Composable
fun Selector(
    label: String,
    valor: String,
    opciones: List<String>,
    onSelect: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth()) {

        Text(label, color = Color(0xFF0A7E34))

        Box {
            OutlinedTextField(
                value = valor,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text(label) }
            )

            Box(
                Modifier
                    .matchParentSize()
                    .clickable { expanded = true }
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opciones.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onSelect(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

/* ============================================================
   SELECTOR DE FECHA
   Muestra dd/MM/yyyy, envía yyyy-MM-dd a la API
============================================================ */

@Composable
fun FechaSelector(label: String, onSelect: (String) -> Unit) {

    val ctx = LocalContext.current
    var valorVisual by remember { mutableStateOf("") } // lo que ve el usuario

    Column(Modifier.fillMaxWidth()) {

        Text(label, color = Color(0xFF0A7E34))

        Box {

            OutlinedTextField(
                value = valorVisual,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text(label) },
                trailingIcon = {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = null
                    )
                }
            )

            Box(
                Modifier
                    .matchParentSize()
                    .clickable {

                        val c = Calendar.getInstance()

                        DatePickerDialog(
                            ctx,
                            { _, y, m, d ->
                                // lo que ve el usuario
                                valorVisual = "%02d/%02d/%04d".format(d, m + 1, y)
                                // lo que se manda a la API
                                val fechaApi = "%04d-%02d-%02d".format(y, m + 1, d)
                                onSelect(fechaApi)
                            },
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
            )
        }
    }
}

/* ============================================================
   LEYENDA
============================================================ */

@Composable
fun Leyenda(color: Color, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(18.dp)
                .background(color, CircleShape)
        )
        Text("  $texto")
    }
}

/* ============================================================
   MAPA DE ASIENTOS (CONECTADO A LA API)
============================================================ */

@Composable
fun AsientosMapa(
    pasajeros: Int,
    origen: String,
    destino: String,
    hora: String,
    fechaApi: String,
    asientosSeleccionados: SnapshotStateList<Int>
) {

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val api = RetrofitClient.instance

    val total = 28
    val filas = total / 4

    // lista de asientos ocupados que viene del backend
    val ocupados = remember { mutableStateListOf<Int>() }

    // 🔄 cada vez que cambie origen/destino/hora/fecha, consulto API
    LaunchedEffect(origen, destino, hora, fechaApi) {
        if (origen.isNotBlank() && destino.isNotBlank() && hora.isNotBlank() && fechaApi.isNotBlank()) {
            try {
                val resp = api.asientosOcupados(
                    limpiarTildes(origen),
                    limpiarTildes(destino),
                    hora,
                    fechaApi
                )
                ocupados.clear()
                ocupados.addAll(resp.ocupados)   // AsientosResponse(val ocupados: List<Int>)
            } catch (e: Exception) {
                Toast.makeText(
                    ctx,
                    "No se pudieron cargar los asientos ocupados",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            ocupados.clear()
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        repeat(filas) { f ->

            Row(
                modifier = Modifier.padding(6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                (1..4).forEach { i ->

                    val asiento = f * 4 + i

                    val color = when {
                        ocupados.contains(asiento) -> Color.Red
                        asientosSeleccionados.contains(asiento) -> Color.Yellow
                        else -> Color(0xFF0A7E34)
                    }

                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(color, CircleShape)
                            .clickable {

                                // no permitir seleccionar ocupados
                                if (ocupados.contains(asiento)) return@clickable

                                if (asientosSeleccionados.contains(asiento)) {
                                    asientosSeleccionados.remove(asiento)
                                } else {
                                    // respetar número de pasajeros
                                    if (asientosSeleccionados.size >= pasajeros) {
                                        Toast
                                            .makeText(
                                                ctx,
                                                "Solo puedes seleccionar $pasajeros asiento(s)",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        return@clickable
                                    }
                                    asientosSeleccionados.add(asiento)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(asiento.toString(), color = Color.White)
                    }
                }
            }
        }
    }
}
