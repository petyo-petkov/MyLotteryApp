package com.example.mylotteryapp.getInfoLoterias.modelos.euromillones

import kotlinx.serialization.Serializable

@Serializable
data class EscrutinioMillon(
    val ganadores: Int? = null,
    val orden_escrutinio: String? = null,
    val premio: String? = null,
    val tipo: String? = null
)