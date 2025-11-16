package com.coonorte.cootransporte.data.network.request

data class VentaRequest(
    val usuario_id: Int?,
    val referencia: String,
    val comprador: String,
    val documento: String,
    val origen: String,
    val destino: String,
    val hora: String,
    val fecha: String,
    val fechaCompra: String,
    val horaCompra: String,
    val modoPago: String,
    val asientos: List<Int>,
    val precio: Int,
    val pasajeros: Int
)
