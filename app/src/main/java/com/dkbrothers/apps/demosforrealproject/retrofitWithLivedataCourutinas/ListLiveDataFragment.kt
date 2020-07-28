package com.dkbrothers.apps.demosforrealproject.retrofitWithLivedataCourutinas


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.dialogs.CustomShowDialog


class ListLiveDataFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_livedata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btn_ir).setOnClickListener {
            hacerPeticion()
        }
    }

    fun hacerPeticion(){
        Toast.makeText(requireContext(),getString(R.string.cargando),
            Toast.LENGTH_LONG).show()
        Handler().postDelayed({
            ApiClientRetrofitBuilder(requireContext()).consultarComentarios().observe(viewLifecycleOwner,
                Observer {
                it.subscribe { response ->
                    Toast.makeText(requireContext(),"Ya respondio el servicio",
                        Toast.LENGTH_SHORT).show()
                    requireActivity().CustomShowDialog("Hola el LiveData sirvio, muestra el dialogo cuando la app esta activa")
                }
            })
        }, 10000)
    }


}