package sv.edu.udb.crudfirebase050926

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import sv.edu.udb.crudfirebase050926.datos.Persona

class AddPersonaActivity : AppCompatActivity() {

    private var txtNombre: EditText? = null
    private var txtDUI: EditText? = null
    private var key = ""
    private var accion = ""
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_persona)
        inicializar()
    }

    private fun inicializar() {
        txtNombre = findViewById(R.id.txtNombre)
        txtDUI = findViewById(R.id.txtDUI)

        val datos: Bundle? = intent.extras
        if (datos != null) {
            key = datos.getString("key").toString()
            txtDUI?.setText(datos.getString("dui"))
            txtNombre?.setText(datos.getString("nombre"))
            accion = datos.getString("accion").toString()
        }
    }

    fun guardar(v: View?) {
        val nombre: String = txtNombre?.text.toString().trim()
        val dui: String = txtDUI?.text.toString().trim()

        if (nombre.isEmpty() || dui.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        database = FirebaseDatabase.getInstance()
            .getReference("users")
            .child(user.uid)
            .child("personas")

        val persona = Persona(dui, nombre)

        if (accion == "a") {
            val newKey = database.push().key
            if (newKey != null) {
                database.child(newKey).setValue(persona).addOnSuccessListener {
                    Toast.makeText(this, "Se guardó con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se pudo generar una clave", Toast.LENGTH_SHORT).show()
            }
        } else if (accion == "e") {
            if (key.isNotEmpty()) {
                database.child(key).setValue(persona)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Se actualizó con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error al actualizar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "No se encontró la clave del registro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun cancelar(v: View?) {
        finish()
    }
}