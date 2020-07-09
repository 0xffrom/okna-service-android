package com.goga133.oknaservice.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goga133.oknaservice.objects.Office

class ContactsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Тут слайд с контактами"
    }
    val text: LiveData<String> = _text

    private val _arrayOffices = MutableLiveData<Array<Office>>().apply {
        value = arrayOf(
            Office(
                "Москва",
                "Московский офис",
                "ул. Кировоградская, д.15, ТЦ Электронный Рай, 2 этаж",
                "+7 (495) 500-09-68",
                "os@okna-servise.com",
                "09:00 — 18:00 будни",
                "55.6096164,37.6029449"
            ),
            Office(
                "Подольск",
                "Дополнительный офис №1",
                "ул. Вокзальная, д.2",
                "+7 (4967) 69-57-81",
                "os@okna-servise.com",
                "09:00 — 19:00 без выходных",
                "55.4312949,37.5524684"
            ),
            Office(
                "Подольск",
                "Дополнительный офис №2",
                "ул. Советская, Бизнес-центр «Пахра», офис 35",
                "+7 (4967) 58-65-14",
                "os@okna-servise.com",
                "09:00 — 18:00 будни",
                "55.4325217,37.5411622"
            ),
            Office(
                "Климовск",
                "Климовский офис",
                "ул. Симферопольская, д.11, пав.8",
                "+7 (495) 500-09-68",
                "os@okna-servise.com",
                "10:00 — 18:00 будни",
                "55.3761927,37.5268727"
            ),
            Office(
                "Троицк",
                "Троицкий офис",
                "Академическая пл., д.4",
                "+7 (495) 840-83-75",
                "os@okna-servise.com",
                " 10:00 — 19:00 Вт-Сб (Вс и Пн -выходные)",
                "55.4854777,37.2989496"
            ),
            Office(
                "Подольск",
                "Производство/Дилерский отдел",
                "ул. Б. Серпуховская, 43",
                "+7 (495) 505-65-14, +7 (4967) 58-65-14",
                "os@okna-servise.com",
                "09:00 — 18:00 будни",
                "55.4220819,37.5388602"
            )
        )
    }

    val arrayOffices: LiveData<Array<Office>> = _arrayOffices
}