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
import com.example.bebagua_lembrete.databinding.ActivityMainBinding
import com.example.bebagua_lembrete.model.CalcularIngestaoDiaria
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultMl = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendar: Calendar
    var currentTime = 0 // hora atual
    var currentMinute = 0 // minutual atual


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//  Escondendo a BARRA DE AÇÃO
        supportActionBar!!.hide()

//        IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

    val bt_calcular = binding.btCalcular

        bt_calcular.setOnClickListener {
            if (binding.editPeso.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if (binding.editIdade.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            } else {
                val peso = binding.editPeso.text.toString().toDouble()

                val idade = binding.editIdade.text.toString().toInt()

                calcularIngestaoDiaria.CalcularTotalML(peso, idade)
                resultMl = calcularIngestaoDiaria.ResultMl()
//  getNumberInstance() = formatar o numero (colocar uma virgula) com base na localida
                val format = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                format.isGroupingUsed = false // por padrao vem true
                binding.txtResultMl.text = format.format(resultMl) + " " + "ml"

            }
        }

        // val ic_define_data = binding.icRedefinir // OU

        // criando tela de Alert (informaçoes)
        binding.icRedefinir.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_desc)
                    //Criando o botão positivo
                .setPositiveButton("Ok") { dialogInterface, i ->
                    binding.editPeso.setText("") // Limpando os campos
                    binding.editIdade.setText("")
                    binding.txtResultMl.text = ""
                }
            alertDialog.setNegativeButton("Cancelar") { dialogInterface, i ->
                // ficará em branco, pois caso o usuário clique em cancelar não acontecerá nada
            }
            // executando o código
            val dialog = alertDialog.create()
            dialog.show()
        }
// Criando o método do LEMBRETE
        binding.btDefineReminder.setOnClickListener {
            calendar = Calendar.getInstance() // recuperandoa instância
            currentTime = calendar.get(Calendar.HOUR_OF_DAY) // pegando a hora do dia
            currentMinute = calendar.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, {timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.txtHour.text = String.format("%02d", hourOfDay) // %02d = é padrão do formato da hora
                binding.txtMin.text = String.format("%02d", minutes)
            }, currentTime, currentMinute, true) // para o formato 24h colocar true
            timePickerDialog.show()
        }

    binding.btAlarm.setOnClickListener {

        val txt_hour = binding.txtHour
        val txt_min = binding.txtMin

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

}


//    private lateinit var edit_peso: EditText
//    private lateinit var edit_idade: EditText
//    private lateinit var bt_calcular: Button
//    private lateinit var txt_result_ml: TextView
//    private lateinit var bt_define_reminder: Button
//    private lateinit var ic_define_data: ImageView
//    private lateinit var bt_reminder: Button
//    private lateinit var bt_alarm: Button
//    private lateinit var txt_hour: TextView
//    private lateinit var txt_min: TextView
