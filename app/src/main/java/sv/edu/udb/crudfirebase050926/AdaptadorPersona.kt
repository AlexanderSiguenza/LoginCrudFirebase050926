package sv.edu.udb.crudfirebase050926

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import sv.edu.udb.crudfirebase050926.datos.Persona

class AdaptadorPersona(
    private val context: Activity,
    private var personas: List<Persona>
) : ArrayAdapter<Persona>(context, R.layout.persona_layout, personas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = convertView ?: context.layoutInflater.inflate(R.layout.persona_layout, parent, false)

        val tvNombre = rowView.findViewById<TextView>(R.id.tvNombre)
        val tvDUI = rowView.findViewById<TextView>(R.id.tvDUI)

        val persona = personas[position]
        tvNombre.text = "Nombre : ${persona.nombre}"
        tvDUI.text = "DUI : ${persona.dui}"

        return rowView
    }
}