package sv.edu.udb.crudfirebase050926

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import sv.edu.udb.crudfirebase050926.datos.Persona

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var consultaOrdenada: Query
    var personas: MutableList<Persona>? = null
    lateinit var listaPersonas: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user == null) {
            irALogin()
            return
        }

        consultaOrdenada = refPersonas.orderByChild("nombre")

        setContentView(R.layout.activity_main)
        inicializar()
    }

    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById(R.id.fab_agregar)
        listaPersonas = findViewById(R.id.ListaPersonas)

        listaPersonas.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(baseContext, AddPersonaActivity::class.java)
                intent.putExtra("accion", "e")
                intent.putExtra("key", personas!![i].key)
                intent.putExtra("nombre", personas!![i].nombre)
                intent.putExtra("dui", personas!![i].dui)
                startActivity(intent)
            }
        })

        listaPersonas.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                val ad = AlertDialog.Builder(this@MainActivity)
                ad.setMessage("¿Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Sí") { dialog, id ->
                    personas!![position].key?.let {
                        refPersonas.child(it).removeValue()
                    }
                    Toast.makeText(this@MainActivity, "¡Registro borrado!", Toast.LENGTH_SHORT).show()
                }
                ad.setNegativeButton("No") { dialog, id ->
                    Toast.makeText(this@MainActivity, "¡Operación de borrado cancelada!", Toast.LENGTH_SHORT).show()
                }
                ad.show()
                return true
            }
        }

        fab_agregar.setOnClickListener {
            val i = Intent(baseContext, AddPersonaActivity::class.java)
            i.putExtra("accion", "a")
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("dui", "")
            startActivity(i)
        }

        personas = ArrayList()

        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                personas!!.clear()
                for (dato in dataSnapshot.children) {
                    val persona: Persona? = dato.getValue(Persona::class.java)
                    persona?.key = dato.key
                    if (persona != null) {
                        personas!!.add(persona)
                    }
                }
                val adapter = AdaptadorPersona(this@MainActivity, personas as ArrayList<Persona>)
                listaPersonas.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error de lectura: ${databaseError.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_opcion1 -> {
                startActivity(Intent(this, Opcion1Activity::class.java))
                true
            }
            R.id.action_opcion2 -> {
                startActivity(Intent(this, Opcion2Activity::class.java))
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                irALogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun irALogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()

        val refPersonas: DatabaseReference
            get() {
                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "invitados"
                return database.getReference("users").child(uid).child("personas")
            }
    }
}