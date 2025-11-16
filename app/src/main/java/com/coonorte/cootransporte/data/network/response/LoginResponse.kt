package com.coonorte.cootransporte.data.network.response

data class LoginResponse(
    val mensaje: String,
    val usuario: Usuario? = null
)

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val documento: String,
    val rol: String
)
