
package com.example.appsupabase

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appsupabase.data.SupabaseManager
import com.example.appsupabase.models.Alumno
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var txtAlumnos: TextView
    private lateinit var progressMaterias: ProgressBar
    private lateinit var btnConsultar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            v.setPadding(
                bars.left,
                bars.top,
                bars.right,
                bars.bottom
            )
            insets
        }

        txtAlumnos = findViewById(R.id.txtAlumnos)
        progressMaterias = findViewById(R.id.progressMaterias)
        btnConsultar = findViewById(R.id.btnConsultar)

        btnConsultar.setOnClickListener {
            consultarAlumnos()
        }

        consultarAlumnos()
    }

    private fun consultarAlumnos() {

        progressMaterias.visibility = View.VISIBLE
        btnConsultar.isEnabled = false

        lifecycleScope.launch {
            try {

                val alumnos = SupabaseManager.client
                    .from("alumnos")
                    .select {
                        order("nombres", Order.ASCENDING)
                    }
                    .decodeList<Alumno>()

                txtAlumnos.text = if (alumnos.isEmpty()) {
                    "No hay alumnos registrados."
                } else {
                    alumnos.mapIndexed { i, a ->
                        "${i + 1}. ${a.nombres}\n" +
                                "Paralelo: ${a.paralelo}\n" +
                                "Correo: ${a.correo ?: "Sin correo"}\n" +
                                "Teléfono: ${a.telefono ?: "Sin teléfono"}"
                    }.joinToString("\n\n")
                }

            } catch (e: Exception) {

                txtAlumnos.text =
                    "Error al consultar: ${e.message}"

            } finally {

                progressMaterias.visibility = View.GONE
                btnConsultar.isEnabled = true

            }
        }
    }
}
