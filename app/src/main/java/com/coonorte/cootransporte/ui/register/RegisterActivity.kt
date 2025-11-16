package com.coonorte.cootransporte.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.coonorte.cootransporte.data.network.RetrofitClient
import com.coonorte.cootransporte.data.network.request.RegisterRequest
import kotlinx.coroutines.*

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            RegisterScreen(
                onRegister = { nombre, email, documento, pass, confirm ->

                    if (pass != confirm) {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                        return@RegisterScreen
                    }

                    registrar(nombre, email, documento, pass)
                },
                onGoLogin = { finish() }
            )
        }
    }

    private fun registrar(nombre: String, email: String, documento: String, password: String) {

        val request = RegisterRequest(nombre, email, documento, password)

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = RetrofitClient.instance.register(request)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, response.mensaje, Toast.LENGTH_LONG).show()
                    finish()
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
