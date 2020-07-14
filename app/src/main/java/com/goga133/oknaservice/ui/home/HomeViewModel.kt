package com.goga133.oknaservice.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goga133.oknaservice.models.Office
import com.goga133.oknaservice.models.SaleCard
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class HomeViewModel : ViewModel() {

    private val _arraySales = MutableLiveData<Array<SaleCard>>().apply {
        // TODO: Поменять данные:
        value = arrayOf(
            SaleCard("Скидки всем студентам на москитные сетки!",
            "https://tu.market/uploads/tu/0/idKA_23274/%D0%A1%D0%B5%D1%82%D0%BA%D0%B0%20%D0%BC%D0%BE%D1%81%D0%BA%D0%B8%D1%82%D0%BD%D0%B0%D1%8F.jpg",
            "Ты студент и давно хотел приобрести в общежитие москитную сетку? Тогда тебе к нам ! Мы предоставляем каждому студенту скидку в размере 10% на покупку москитной сетки!",
            Date(1,1,1))
        )
    }

    val arraySales: LiveData<Array<SaleCard>> = _arraySales
}