package com.dkbrothers.apps.demosforrealproject.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Base64
import androidx.core.text.HtmlCompat
import java.io.ByteArrayOutputStream


/**
* Convierte text a string HTML
* */
fun String.toHtmlText() : Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}



/**
 * Cambiar el color de una palabra o frase que especifiques
 * @param textToChange text que quieres colorear
 * @param color color que le quieres poner al text especificado
 *
 * Ojo este metodo solo sirve para versiones Android 7 hacia arriba
 **/
fun String.changeColorOfText(textToChange:String, color:Int):SpannableStringBuilder {
    val spannable: Spannable = SpannableString(this)
    val str = spannable.toString()
    val iStart = str.indexOf(textToChange)
    val iEnd = iStart + textToChange.length
    val ssText = SpannableStringBuilder(this)
    ssText.setSpan(ForegroundColorSpan(color), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return ssText
}

fun convertBitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}


fun convertBase64ToBitmap(imageBase64: String): Bitmap {
    val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}


fun String.limpiarStrings():String{
    return this.replace("'","")
        .replace("\"","").replace(",","")
}




