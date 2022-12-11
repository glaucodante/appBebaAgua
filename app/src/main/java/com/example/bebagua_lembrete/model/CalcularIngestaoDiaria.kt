package com.example.bebagua_lembrete.model

class CalcularIngestaoDiaria {

    private var ML_JOVEM = 40.0
    private var ML_ADULTO = 35.0
    private var ML_IDOSO = 30.0
    private var ML_MAIS_DE_66_ANOS = 25.0

    private var resultMl = 0.0
    private var result_total_ml = 0.0

    fun CalcularTotalML(peso: Double, idade: Int) {
        if (idade <= 17){
            resultMl = peso * ML_JOVEM
            result_total_ml = resultMl

        } else if (idade <= 55){
            resultMl = peso * ML_ADULTO
            result_total_ml = resultMl

        } else if (idade <= 65){
            resultMl = peso * ML_IDOSO
            result_total_ml = resultMl

        } else {
            resultMl = peso * ML_MAIS_DE_66_ANOS
            result_total_ml = resultMl
        }

    }

    fun ResultMl(): Double {
        return result_total_ml
    }
}