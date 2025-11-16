package com.coonorte.cootransporte.data.network.response

data class EncomiendaResponse(
    val id: Int,
    val cedulaRemitente: String,
    val nombreRemitente: String,
    val lugarRemitente: String,
    val cedulaDestinatario: String,
    val nombreDestinatario: String,
    val lugarEntrega: String,
    val fechaEnvio: String,
    val reclamado: Int
)
