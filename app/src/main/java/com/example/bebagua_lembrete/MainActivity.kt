package com.example.bebagua_lembrete

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.bebagua_lembrete.model.CalcularIngestaoDiaria
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var edit_peso: EditText
    private lateinit var edit_idade: EditText
    private lateinit var bt_calcular: Button
    private lateinit var txt_result_ml: TextView
    private lateinit var bt_define_reminder: Button
    private lateinit var ic_define_data: ImageView
    private lateinit var bt_reminder: Button
    private lateinit var bt_alarm: Button
    private lateinit var txt_hour: TextView
    private lateinit var txt_min: TextView

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultMl = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendar: Calendar
    var currentTime = 0 // hora atual
    var currentMinute = 0 // minutual atual


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

        // criando tela de Alert (informaçoes)
        ic_define_data.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_desc)
                    //Criando o botão positivo
                .setPositiveButton("Ok") { dialogInterface, i ->
                    edit_peso.setText("") // Limpando os campos
                    edit_idade.setText("")
                    txt_result_ml.text = ""
                }
            alertDialog.setNegativeButton("Cancelar") { dialogInterface, i ->
                // ficará em branco, pois caso o usuário clique em cancelar não acontecerá nada
            }
            // executando o código
            val dialog = alertDialog.create()
            dialog.show()
        }
// Criando o método do LEMBRETE
        bt_define_reminder.setOnClickListener {
            calendar = Calendar.getInstance() // recuperandoa instância
            currentTime = calendar.get(Calendar.HOUR_OF_DAY) // pegando a hora do dia
            currentMinute = calendar.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, {timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                txt_hour.text = String.format("%02d", hourOfDay) // %02d = é padrão do formato da hora
                txt_min.text = String.format("%02d", minutes)
            }, currentTime, currentMinute, true) // para o formato 24h colocar true
            timePickerDialog.show()
        }

    bt_alarm.setOnClickListener {
        if (!txt_hour.text.toString().isEmpty() && !txt_min.text.toString().isEmpty()) {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
            intent.putExtra(AlarmClock.EXTRA_HOUR, txt_hour.text.toString().toInt())
            intent.putExtra(AlarmClock.EXTRA_MINUTES, txt_min.text.toString().toInt())
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarm_message))
            startActivity(intent) // inicializando a intencao

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
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
        bt_reminder = findViewById(R.id.bt_define_reminder)
        bt_alarm = findViewById(R.id.bt_alarm)
        txt_hour = findViewById(R.id.txt_hour)
        txt_min = findViewById(R.id.txt_min)

    }



}