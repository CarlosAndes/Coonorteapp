package com.coonorte.cootransporte.data.network.request

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val documento: String,
    val password: String
)
