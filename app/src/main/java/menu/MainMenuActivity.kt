package com.coonorte.cootransporte.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.coonorte.cootransporte.ui.login.LoginActivity
import com.coonorte.cootransporte.ui.ventas.VentasActivity
import com.coonorte.cootransporte.ui.encomiendas.EncomiendasActivity
import com.coonorte.cootransporte.ui.quejas.QuejasActivity

class MainMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainMenuScreen(
                onVentas = { startActivity(Intent(this, VentasActivity::class.java)) },
                onEncomiendas = { startActivity(Intent(this, EncomiendasActivity::class.java)) },
                onQuejas = { startActivity(Intent(this, QuejasActivity::class.java)) },

                // 🔴 cerrar sesión
                onLogout = {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            )
        }
    }
}
