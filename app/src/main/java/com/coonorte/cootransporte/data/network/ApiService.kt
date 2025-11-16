package com.coonorte.cootransporte.data.network

import com.coonorte.cootransporte.data.network.request.*
import com.coonorte.cootransporte.data.network.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    /* =======================================================
       LOGIN
    ======================================================== */
    @POST("/api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse


    /* =======================================================
       REGISTRO
    ======================================================== */
    @POST("/api/registro")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse


    /* =======================================================
       ASIENTOS OCUPADOS (VENTAS)
    ======================================================== */
    @GET("/api/asientos-ocupados")
    suspend fun asientosOcupados(
        @Query("origen") origen: String,
        @Query("destino") destino: String,
        @Query("hora") hora: String,
        @Query("fecha") fecha: String
    ): AsientosResponse


    /* =======================================================
       REGISTRAR VENTA
    ======================================================== */
    @POST("/api/ventas")
    suspend fun registrarVenta(
        @Body venta: VentaRequest
    ): VentaResponse


    /* =======================================================
       CONSULTAR ENCOMIENDAS
       por CÉDULA del Remitente o Destinatario
    ======================================================== */
    @GET("/api/encomiendas")
    suspend fun getEncomiendas(
        @Query("cedula") cedula: String
    ): List<EncomiendaResponse>
}
