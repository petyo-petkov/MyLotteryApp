package com.example.mylotteryapp.getInfoLoterias.modelos.loteriaNacional

import kotlinx.serialization.Serializable

@Serializable
data class PremioEspecial(
    val fila: Int? = null,
    val fraccion: Int? = null,
    val literalPremio: LiteralPremio? = null,
    val numero: String? = null,
    val orden: Int? = null,
    val premio: Int? = null,
    val serie: Int? = null,
    val showFolded: Boolean? = null
)