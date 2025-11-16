package com.coonorte.cootransporte.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coonorte.cootransporte.R

@Composable
fun MainMenuScreen(
    onVentas: () -> Unit,
    onEncomiendas: () -> Unit,
    onQuejas: () -> Unit,
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A662F)), // Verde Coonorte
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔵 BARRA SUPERIOR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A662F))
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LOGO COONORTE
            Image(
                painter = painterResource(id = R.drawable.bus_coonorte),
                contentDescription = "Logo Coonorte",
                modifier = Modifier.size(70.dp)
            )

            // BOTÓN CERRAR SESIÓN
            Text(
                text = "Cerrar sesión",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable { onLogout() }
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🟩 Caja blanca centrada
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Menú Principal",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 🚌 COMPRAR TIQUETE
                Button(
                    onClick = { onVentas() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A7E34)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_bus),
                            contentDescription = "icon bus",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Comprar Tiquete", color = Color.White, fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                // 📦 ENCOMIENDAS
                Button(
                    onClick = { onEncomiendas() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A7E34)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_box),
                            contentDescription = "icon encomiendas",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Encomiendas", color = Color.White, fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                // 📣 QUEJAS
                Button(
                    onClick = { onQuejas() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A7E34)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_megaphone),
                            contentDescription = "icon quejas",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Quejas", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Logo SuperTransporte
        Image(
            painter = painterResource(id = R.drawable.ic_supertransporte),
            contentDescription = "logo supertransporte",
            modifier = Modifier.size(170.dp)
        )

        Text(
            text = "© 2025 Coonorte - Todos los derechos reservados",
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
