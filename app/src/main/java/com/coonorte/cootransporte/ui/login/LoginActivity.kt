package com.coonorte.cootransporte.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.coonorte.cootransporte.data.network.RetrofitClient
import com.coonorte.cootransporte.data.network.request.LoginRequest
import com.coonorte.cootransporte.ui.menu.MainMenuActivity
import com.coonorte.cootransporte.ui.CoonorteAppTheme
import kotlinx.coroutines.*

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoonorteAppTheme {

                LoginScreen(
                    onLogin = { user, pass ->
                        login(user, pass)
                    },
                    onGoRegister = {
                        startActivity(Intent(this, com.coonorte.cootransporte.ui.register.RegisterActivity::class.java))
                    }
                )
            }
        }
    }

    private fun login(user: String, pass: String) {

        val request = LoginRequest(user, pass)

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = RetrofitClient.instance.login(request)

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@LoginActivity,
                        response.mensaje,
                        Toast.LENGTH_LONG
                    ).show()

                    if (response.usuario != null) {
                        startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
                        finish()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
