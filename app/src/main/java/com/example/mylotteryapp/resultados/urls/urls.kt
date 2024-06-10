package com.example.mylotteryapp.resultados.urls


const val GET_SORTEOS_CELEBRADOS_FECHAS = "https://www.loteriasyapuestas.es/servicios/buscadorSorteos?game_id=TODOS&celebrados=true&fechaInicioInclusiva=20240310&fechaFinInclusiva=20240610"
const val GET_PROXIMOS_SORTEOS_TODOS = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=TODOS&num=2"

// Loteria Nacional
const val GET_PROXIMOS_SORTEOS_LNAC = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=LNAC&num=10"
const val GET_ULTIMOS_SORTEOS_LNAC = "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosLoteriaNacional"

//La Primitiva
const val GET_PROXIMOS_SORTEOS_LAPR = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=LAPR&num=2"
const val GET_ULTIMOS_SORTEOS_LAPR = "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosPrimitiva"

//EuroMilliones
const val GET_PROXIMOS_SORTEOS_EMIL = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=EMIL&num=2"
const val GET_ULTIMOS_SORTEOS_EMIL = "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosEuromillones"

// El Gordo
const val GET_PROXIMOS_SORTEOS_ELGR = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=ELGR&num=2"
const val GET_ULTIMOS_SORTEOS_ELGR = "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosGordoPrimitiva"

// Bonoloto
const val GET_PROXIMOS_SORTEOS_BONO = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=BONO&num=2"
const val GET_ULTIMOS_SORTEOS_BONO = "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosBonoloto"

// Euro Dreams
const val GET_PROXIMOS_SORTEOS_EDMS = "https://www.loteriasyapuestas.es/servicios/proximosv3?game_id=EDMS&num=2"
const val GET_ULTIMOS_SORTEOS_EDMS = "https://www.loteriasyapuestas.es/servicios/buscadorUltimosSorteosCelebradosEurodreams"