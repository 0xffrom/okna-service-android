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
            "Ты студент и давно хотел приобрести в общежитие москитную сетку? Тогда тебе к нам ! Мы предоставляем каждому студенту скидку в размере 10% на покупку москитной сетки!"
            ),
            SaleCard("Акция 2020 года! Скидка 20% на монтаж.",
                "https://www.okna-servise.com/assets/images/%D0%B0%D0%B0/girl4.png",
                "Компания «Окна Сервис» предлагает уникальную акцию: остекляя трехкомнатную квартиру, Вы получаете 20% скидку на монтаж."
            ),
            SaleCard("Загородный дом",
                "https://www.okna-servise.com/assets/images/%D0%B0%D0%B0/k1.png",
                "Компания «Окна Сервис» предлагает дополнительные скидки владельцам загородных домов и коттеджей. Если вы закажите остекление своего дома у нас, то получите не только 20% скидку на установку окон, но и на сами окна. Узнайте подробности у менеджера.\n" +
                        "\n" +
                        "Выгода: Ваша экономия может составить до 100 000 рублей!"
            ),
            SaleCard("Окна ПВХ со скидкой 43%",
                "https://www.okna-servise.com/assets/images/%D0%B0%D0%B0/k2.png",
                "Всем кто закажет в компании «Окна Сервис» остекление балкона или установку новых пластиковых окон мы сделаем 43% скидку на изделия вне зависимости от цвета профиля, комплектации откосами и подоконниками и типа открывания створок.\n" +
                        "\n" +
                        "Выгода:В итоге вы получите новые окна ПВХ или остекление балкона почти за половину их реальной цены."
            ),
            SaleCard("Лови момент",
                "https://www.okna-servise.com/assets/images/%D0%B0%D0%B0/k4.png",
                "Если вы заключите договор на установку пластиковых окон или остекление балкона у себя на объекте, то мы предоставим вам дополнительную 5% скидку.\n" +
                        "\n" +
                        "Выгода:В итоге, если примите решение во время замера, то получите качественные пластиковые окна по выгодной цене."
            ),
            SaleCard("Москитная сетка",
                "https://www.okna-servise.com/assets/images/moskitnaya-setka-foto.png",
                "Каждый кто закажет в компании \"Окна Сервис\" пластиковые окна REHAU или остекление балкона из алюминиевых рам PROVEDAL получит москитную сетку в подарок\n" +
                        "\n" +
                        "Выгода:В вашу квартиру или на балкон не будут проникать комары и мошкара"
            )
        )
    }

    val arraySales: LiveData<Array<SaleCard>> = _arraySales
}