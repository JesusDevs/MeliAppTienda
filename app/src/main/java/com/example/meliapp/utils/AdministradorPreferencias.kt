package com.example.meliapp.utils

import android.content.Context
import android.content.SharedPreferences

class AdministradorPreferencias {
    /**
     * preferencias del sistema.
     */
    private lateinit var preferencias: SharedPreferences

    /**
     * editor de preferencias.
     */
    private lateinit var editor: SharedPreferences.Editor

    /**
     * Contructor de la clase.
     * @param context contexto de android.
     */
    constructor(context: Context?) {
        if (context != null) {
            preferencias = context.getSharedPreferences(PREF_NOMBRE, Context.MODE_PRIVATE)
            editor = preferencias.edit()
            editor.apply()
        }
    }

    /**
     * Constructor de la clase
     * @param context contexto de la app
     * @param fileName archivo de preferencias compartidas
     */
    constructor(context: Context?, fileName: String?) {
        if (context != null) {
            preferencias = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            editor = preferencias.edit()
            editor.apply()
        }
    }

    /**
     * metodo que retorna el valor booleano de una preferencia.
     * @return valor de la variable.
     */
    fun obtenerValorPreferenciasBoolean(key: String?, porDefecto: Boolean): Boolean {
        return preferencias.getBoolean(key, porDefecto)
    }

    /**
     * metodo que retorna el valor String de una preferencia.
     * @return valor de la variable.
     */
    fun obtenerValorPreferenciasString(key: String?): String? {
        return preferencias.getString(key, "")
    }

    /**
     * metodo que retorna el valor booleano de una preferencia.
     * @return valor de la variable.
     */
    fun obtenerValorPreferenciasInt(key: String?): Int {
        return preferencias.getInt(key, 0)
    }

    /**
     * metodo que retorna el valor booleano de una preferencia.
     * @return valor de la variable.
     */
    fun obtenerValorPreferenciasLong(key: String?): Long {
        return preferencias.getLong(key, 0)
    }

    /**
     * metodo para insertar un valor en las preferencias de android.
     * @param llave identificador de la preferencias
     * @param obj valor a guardar
     * @return
     *
     *TRUE:si el valor se persistio correctamente
     *
     * FALSE: si el valor no se persistio.
     */
    fun setPreferencia(llave: String, obj: Any?): Boolean {
        try {
            if (obj is Boolean) {
                editor!!.putBoolean(llave, obj)
            } else if (obj is String) {
                editor!!.putString(llave, obj as String?)
            } else if (obj is Int) {
                editor!!.putInt(llave, obj)
            } else if (obj is Long) {
                editor!!.putLong(llave, obj)
            } else if (obj is Float) {
                editor!!.putFloat(llave, obj)
            } else {
                return false
            }
            return editor!!.commit()
        } catch (e: Exception) {
        }
        return false
    }

    /**
     * Metodo que elmina la preferencia correspondiente
     *
     * @param llave identificador de la preferencia a eliminar
     */
    fun eliminarPreferencia(llave: String?) {
        editor!!.remove(llave)
        editor!!.commit()
    }


    /**
     * elimina toda la informacion de las preferencias.
     */
    fun eliminarContenido(context: Context?) {
        if (context != null && editor != null) {
            if (editor!!.clear().commit()) {
            } else {
            }
            editor!!.clear().apply()
        }
    }

    /**
     * verifica si existe una key en el archivo de preferencias.
     * @param key
     */
    fun containsKey(key: String?): Boolean {
        return preferencias!!.contains(key)
    }

    companion object {
        /**
         * nombre del archivo de preferencias compartidas.
         */
        private const val PREF_NOMBRE = "propertiesWOM"

        /**
         * nombre de archivo preferencias cambio de plan
         */
        const val PREF_CAMBIO_PLAN = "cambioPlanWom"

        /**
         * nombre de archivo preferencias cambio de plan
         */
        const val PREF_CAMBIO_PLAN_GRUPAL = "cambioPlanGrupalWom"

        /**
         * nombre de archivo preferencias para favoritos
         */
        const val PREF_FAVORITOS = "favoritosWom"
        const val PROPERTIE_FILE = "PROPERTIE_FILE"
        const val DEVICE_RENEWAL_DATE = "DEVICE_RENEWAL_DATE"

        /**
         * nombre de archivo preferencias para enrolamiento
         */
        const val PREF_ENROLAMIENTO = "enrolamientoWom"
    }
}
