package com.example.mylotteryapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boleto
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class RealmViewModel(
    private val realmRepo: RealmRepository
) : ViewModel() {

    var selectedCard by mutableStateOf(false)

    var boletos by mutableStateOf(emptyList<Boleto>())
    var boleto: Boleto? by mutableStateOf(Boleto())

    var gastado by mutableStateOf(0.0)
    //var ganado by mutableStateOf(0.0)


    fun getBoletos() {
        viewModelScope.launch() {
            realmRepo.getBoletos().collect {
                boletos = it
            }
        }

    }

    fun getPrecios() {
        viewModelScope.launch() {
            realmRepo.getPrecios().collect{
                gastado = it
            }
        }
    }

//    fun getPremio() {
//        viewModelScope.launch {
//            realmRepo.getPremio().collect{
//                ganado = it
//            }
//        }
//    }

    fun deleteAllBoletos() {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.deleteAll()

        }
    }


    fun deleteBoleto(id: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.deleteBoleto(id)
        }
    }

    fun sortByDates(startDay: RealmInstant, endDay: RealmInstant) {
        viewModelScope.launch(Dispatchers.IO) {
            realmRepo.rangoFechas(startDay, endDay)
        }
    }

    fun insertBoleto(boleto: Boleto){
        viewModelScope.launch {
            realmRepo.insertarBoleto(boleto)
        }
    }


}