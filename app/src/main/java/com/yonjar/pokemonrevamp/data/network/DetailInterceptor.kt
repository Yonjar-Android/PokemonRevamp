package com.yonjar.pokemonrevamp.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class DetailInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val response: Response = chain.proceed(request)


        val responseBody = response.peekBody(Long.MAX_VALUE)

        val estadoSolicitud = response.code
        val datos = responseBody.string()
        val mensaje = response.message

        println("Estado de la solicitud: $estadoSolicitud")
        println("Datos devueltos: $datos")
        println("Mensaje: $mensaje")

        return response
    }
}