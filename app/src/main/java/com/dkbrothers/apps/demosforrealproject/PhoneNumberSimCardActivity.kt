package com.dkbrothers.apps.demosforrealproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_phone_number_simcard.*


class PhoneNumberSimCardActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_READ_PHONE_NUMBERS = 1097

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_simcard)
        if(checkfPermission()){
            tv_numero.text = obtenerNumero()
        }
    }


    /**
     * La disponibilidad depende estrictamente del operador
     *  y la forma en que el número está codificado en la tarjeta SIM. Si está codificado
     *  por la compañía que fabrica las SIM o por el operador de telefonía móvil. Esto devuelve
     *  lo mismo que en Configuración-> sobre el teléfono. Vacio o Nulo
     * */
    private fun obtenerNumero():String{
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var numero = telephonyManager.line1Number?:""
        if(numero.isNotEmpty()){
            numero = numero.substring(3)// le quito el indicativo de pais +57
        }
        return numero
    }


    private fun checkfPermission():Boolean{
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                val strings = arrayOf(Manifest.permission.READ_PHONE_STATE)
                ActivityCompat.requestPermissions(this, strings, PERMISSIONS_REQUEST_READ_PHONE_NUMBERS)
                return false
            }
        return true
    }


    /*
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_NUMBERS) {
            if (grantResults.size > 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                tv_numero.text = obtenerNumero()
                Toast.makeText(this,getString(R.string.permiso_concedido), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,getString(R.string.permiso_denegado), Toast.LENGTH_LONG).show()
            }
        }
    }
    */

}
