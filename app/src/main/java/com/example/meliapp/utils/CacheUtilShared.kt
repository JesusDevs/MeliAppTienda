package com.example.meliapp.utils

import android.content.Context
import com.example.meliapp.model.payment.PurchaseItem
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class CacheUtilShared {
    companion object{
    val SDF = "yyyy-MM-dd HH:mm:ss"

    fun guardarCachePrem(ctx: Context?, datos: Any?, key: String) {
        val adm = AdministradorPreferencias(ctx)
        val gSon = GsonBuilder().setDateFormat("MM dd, yyyy HH:mm:ss").create()
        //crear gson de fecha
        // Gson gSon = new GsonBuilder().setDateFormat(SDF).create();
        val datosJson = gSon.toJson(datos)
        adm.setPreferencia(key, datosJson)
        val dateFormat = SimpleDateFormat(SDF, Locale.US)
        val llaveCacheFecha = key + "_fecha"
        adm.setPreferencia(llaveCacheFecha, dateFormat.format(Date()))
    }
        fun obtenerDatosCache(ctx: Context?, key: String): Any? {
            val adm = AdministradorPreferencias(ctx)
            val valorGuardadoS = adm.obtenerValorPreferenciasString(key)

            return valorGuardadoS?.let { getDatosGuardados(key, it) }
        }
        private fun getDatosGuardados(key: String, valorGuardadoS: String): Any? {
            val gson = GsonBuilder().setDateFormat("MM dd, yyyy HH:mm:ss").create()
            return try {
                when (key) {
                    "purchase" -> gson.fromJson(
                        valorGuardadoS,
                        PurchaseItem::class.java
                    )
                    else -> null
                }
            } catch (e: JsonSyntaxException) {
                null
            }
        }

    }
}