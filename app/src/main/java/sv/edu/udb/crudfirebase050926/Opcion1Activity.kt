package sv.edu.udb.crudfirebase050926

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Opcion1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcion1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Opción 1"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}