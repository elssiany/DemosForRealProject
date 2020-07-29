package com.dkbrothers.apps.demosforrealproject.viewPinchZoomable

import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.dkbrothers.apps.demosforrealproject.R
import kotlinx.android.synthetic.main.activity_change_scale_gesture.tv
import kotlinx.android.synthetic.main.activity_dual_touch.*

class DualTouchActivity : AppCompatActivity() {

    private var puntoInicialX = 0
    private var puntoInicialY = 0
    private var puntoFinalX = 0
    private var puntoFinalY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dual_touch)
        initViews()
    }


    private fun initViews(){
        main.setOnTouchListener(getDualTouchListener())
        tv.setOnTouchListener(getMultiTouchListener())
        tv.tag = FullCoordenada()
    }

    private val onGestureControl = object :
        MultiTouchListener.OnGestureControl {
        override fun onLongClick() {

        }
        override fun onClick() {
            Toast.makeText(applicationContext, "Clic en una vista",
                Toast.LENGTH_SHORT).show()
        }
    }

    private val onMultiTouchListener = object :
        MultiTouchListener.OnMultiTouchListener {
        override fun actionDown(view: View) {

        }
    }

    private val onDualTouchListener = object :
        DualTouchListener.OnDualTouchListener {
        override fun actionTouch(event: MotionEvent) {
            val action = event.action
            //Log.d("DualTouchActivity","action: $action")
            when (action and event.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    val pointerIdToAdd = event.getPointerId(event.actionIndex)
                    val pathToAdd = Path().apply {
                        moveTo(event.getX(event.actionIndex),
                            event.getY(event.actionIndex))
                    }


                    if(event.pointerCount == 2){

                        val coordenadaActual = tv.tag as FullCoordenada
                        coordenadaActual.xPuntoInicial = event.getX(1).toInt()
                        coordenadaActual.yPuntoInicial = event.getY(1).toInt()

                        tv.tag = coordenadaActual

                        if(puntoInicialX==0)
                            puntoInicialX = event.getX(1).toInt()

                        if(puntoInicialY==0)
                            puntoInicialY = event.getY(1).toInt()

                        //puntoFinalX = puntoInicialX
                        //puntoFinalY = puntoInicialY
                    }


                }
                MotionEvent.ACTION_MOVE -> {
                    for (i in 0 until event.pointerCount) {
                        //val path = pointerIdToPathMap[event.getPointerId(i)]
                        //path.lineTo(event.getX(i), event.getY(i))
                    }
                    if(event.pointerCount == 2){
                        val eventDescription = event.multiTouchDescription()
                        tv_coordenadas.text = eventDescription
                        puntoFinalX = event.getX(1).toInt()
                        puntoFinalY = event.getY(1).toInt()
                        val diferenciaX = puntoFinalX - puntoInicialX
                        val diferenciaY = puntoFinalY - puntoInicialY
                        Log.d("DualTouchActivity", "puntoInicialX:$puntoInicialX")
                        Log.d("DualTouchActivity", "puntoInicialY:$puntoInicialY")
                        Log.d("DualTouchActivity", "-------------------------------------------")
                        Log.d("DualTouchActivity", "diferenciaX:$diferenciaX")
                        Log.d("DualTouchActivity", "diferenciaY:$diferenciaY")
                        Log.d("DualTouchActivity", "-------------------------------------------")
                        cambiarTamanoDeElemento(1,diferenciaX,
                            diferenciaY)
                        //cambiarTamanoDeElementoV2(puntoFinalX, puntoFinalY)
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    val pointerToRemove = event.getPointerId(event.actionIndex)
                    puntoInicialX = 0
                    puntoInicialY = 0
                    cambiarTamanoDeElemento(0,0,0)
                    tv_coordenadas.text = "Bienvenido"
                }
            }
        }
    }

    private val onDualTouchGestureControl = object :
        DualTouchListener.OnGestureControl {
        override fun onLongClick() {

        }
        override fun onClick() {
            Toast.makeText(applicationContext, "Clic en una vista",
                Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * Create a new instance and scalable touchview
     *
     * @return scalable multitouch listener
     */
    private fun getMultiTouchListener(): MultiTouchListener {
        val multiTouchListener = MultiTouchListener(this,tv, false)
        multiTouchListener.setOnGestureControl(onGestureControl)
        multiTouchListener.setOnMultiTouchListener(onMultiTouchListener)
        return multiTouchListener
    }

    /**
     * Create a new instance and scalable touchview
     *
     * @return scalable multitouch listener
     */
    private fun getDualTouchListener(): DualTouchListener {
        val dualTouchListener = DualTouchListener(this)
        dualTouchListener.setOnGestureControl(onDualTouchGestureControl)
        dualTouchListener.setOnDualTouchListener(onDualTouchListener)
        return dualTouchListener
    }


    fun cambiarTamanoDeElemento(UP:Int,coordenadaX: Int, coordenadaY: Int) {
        val layoutParamsAnterior = tv.layoutParams as RelativeLayout.LayoutParams
        val coordenadaActual = tv.tag as FullCoordenada
        var height:Int
        var width:Int
        if(coordenadaActual.xTamInicial==0){
            height = layoutParamsAnterior.height
            width = layoutParamsAnterior.width
        }else{
            width = coordenadaActual.xTamInicial
            height = coordenadaActual.yTamInicial
        }
        if(UP==0){
            coordenadaActual.xTamInicial = layoutParamsAnterior.width
            coordenadaActual.yTamInicial = layoutParamsAnterior.height
            tv.tag = coordenadaActual
        }else{

            if(coordenadaActual.xRedimensionado == 0 && coordenadaActual.yRedimensionado==0) {
                val nuevaCoordenada = FullCoordenada(width, height)
                tv.tag = nuevaCoordenada
            }else{
                val coordenadaInicial = tv.tag as FullCoordenada
                width = coordenadaInicial.xRedimensionado
                height = coordenadaInicial.yRedimensionado
                tv.tag = coordenadaInicial
            }

            if(width < coordenadaX){
                width += coordenadaX
                Log.d("DualTouchActivity", "coordenadaX:$coordenadaX")
            }
            if(height < coordenadaY){
                height += coordenadaY
                Log.d("DualTouchActivity", "coordenadaY:$coordenadaY")
            }
            Log.d("DualTouchActivity", "-------------------------------------------")
            //width = coordenadaX
            //height = coordenadaY
            val layoutParams = RelativeLayout.LayoutParams(width, height)
            layoutParams.leftMargin = layoutParamsAnterior.leftMargin
            layoutParams.topMargin = layoutParamsAnterior.topMargin
            layoutParams.rightMargin = layoutParamsAnterior.rightMargin
            layoutParams.bottomMargin = layoutParamsAnterior.bottomMargin
            tv.layoutParams = layoutParams

        }
    }


    fun cambiarTamanoDeElementoV2(puntoFinalX: Int, puntoFinalY: Int) {
        val layoutParamsAnterior = tv.layoutParams as RelativeLayout.LayoutParams
        var height = layoutParamsAnterior.height
        var width = layoutParamsAnterior.width
        val coordenadaActual = tv.tag as FullCoordenada
        if(coordenadaActual.xTamInicial==0){
            coordenadaActual.xTamInicial = width
            coordenadaActual.yTamInicial = height
            tv.tag = coordenadaActual
        }
        //(puntoFinalX - coordenadaActual.xPuntoInicial) % 2 == 0
        if((puntoFinalX - coordenadaActual.xPuntoInicial) % 2 == 0){
            if(puntoFinalX > coordenadaActual.xPuntoInicial){
                width += 2
            }else{
                width -= 2
            }
            Log.d("DualTouchActivity", "coordenadaX:")
        }
        if((puntoFinalY - coordenadaActual.yPuntoInicial) % 2 == 0){//Para calibrarlo
            if(puntoFinalY > coordenadaActual.yPuntoInicial){
                height += 2
            }else{
                height -= 2
            }
            Log.d("DualTouchActivity", "coordenadaY:")
        }
        Log.d("DualTouchActivity", "-------------------------------------------")
        val layoutParams = RelativeLayout.LayoutParams(width, height)
        layoutParams.leftMargin = layoutParamsAnterior.leftMargin
        layoutParams.topMargin = layoutParamsAnterior.topMargin
        layoutParams.rightMargin = layoutParamsAnterior.rightMargin
        layoutParams.bottomMargin = layoutParamsAnterior.bottomMargin
        tv.layoutParams = layoutParams
    }


    fun MotionEvent.multiTouchDescription(): String {
        val eventLiteral = when (actionMasked) {
            MotionEvent.ACTION_DOWN -> "Down"
            MotionEvent.ACTION_UP -> "Up"
            MotionEvent.ACTION_MOVE -> "Move"
            MotionEvent.ACTION_CANCEL -> "Cancel"
            MotionEvent.ACTION_OUTSIDE -> "Outside"
            MotionEvent.ACTION_POINTER_DOWN -> "Pointer down"
            MotionEvent.ACTION_POINTER_UP -> "Pointer up"
            else -> ""
        }
        return if (eventLiteral.isEmpty()) {
            ""
        } else {
            val stringBuilder = StringBuilder("$eventLiteral action")
            if (actionMasked == MotionEvent.ACTION_POINTER_DOWN || actionMasked == MotionEvent.ACTION_POINTER_UP) {
                stringBuilder.append(" (pointer id: ${actionIndex})")
            }
            stringBuilder.append(" {")
            /*
            for (i in 0 until pointerCount) {
                stringBuilder.append("\n\t\tPointer with id ${getPointerId(i)} at (${getX(i).toInt()}-X, ${getY(i).toInt()}-Y)")
            }
            */
            if(pointerCount ==2) {
                stringBuilder.append("\n\t\tPunto id ${getPointerId(0)} at (${getX(0).toInt()}X, ${getY(0).toInt()}Y)")
                stringBuilder.append("\n\t\tPunto id ${getPointerId(1)} at (${getX(1).toInt()}X, ${getY(1).toInt()}Y)")
            }

            stringBuilder.append("\n}")
            stringBuilder.toString()
        }
    }

}