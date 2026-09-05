package sv.edu.udb.crudfirebase050926

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Opcion2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcion2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Opción 2"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}