package com.example.bebagua_lembrete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bebagua_lembrete.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var edit_peso: EditText
    private lateinit var edit_idade: EditText
    private lateinit var bt_calcular: Button
    private lateinit var txt_result_ml: TextView
    private lateinit var bt_define_reminder: Button
    private lateinit var ic_define_data: ImageView

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultMl = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//  Escondendo a BARRA DE AÇÃO
        supportActionBar!!.hide()

        IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        bt_calcular.setOnClickListener {
            if (edit_peso.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if (edit_idade.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            } else {
                val peso = edit_peso.text.toString().toDouble()
                val idade = edit_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalML(peso, idade)
                resultMl = calcularIngestaoDiaria.ResultMl()
//  getNumberInstance() = formatar o numero (colocar uma virgula) com base na localida
                val format = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                format.isGroupingUsed = false // por padrao vem true
                txt_result_ml.text = format.format(resultMl) + " " + "ml"
            }
        }

    }
    private fun IniciarComponentes() {
        edit_peso = findViewById(R.id.edit_peso)
        edit_idade = findViewById(R.id.edit_idade)
        bt_calcular = findViewById(R.id.bt_calcular)
        txt_result_ml = findViewById(R.id.txt_result_ml)
        bt_define_reminder = findViewById(R.id.bt_define_reminder)
        ic_define_data = findViewById(R.id.ic_redefinir)
    }


}