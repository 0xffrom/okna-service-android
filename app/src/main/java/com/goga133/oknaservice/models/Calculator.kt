package com.goga133.oknaservice.models

import com.goga133.oknaservice.models.price.TotalCost
import com.goga133.oknaservice.models.price.UserWindow
import java.util.*
import kotlin.math.roundToInt

object Calculator {
    fun calculatePrice(userWindow: UserWindow): TotalCost {
        val sum: Int // Общая стоимость
        var sumW: Int // Стоимость изделия
        val sumR: Int // Стоимость рамы
        var sumG: Int = 0 // Стоимость стеклопакета
        var sumS: Int = 0 // Стоимость створок
        val sumH: Int // Стоимость перемычек
        val sumI: Int // Стоимость работы
        var sumD: Int = 0 // Стоимость доставки
        var sumO: Int = 0 // Стоимость опций
        var sumM: Int = 0 // Стоимость монтажа

        val hr: Double = userWindow.height / 1000.0; // высота
        val wr: Double = userWindow.width / 1000.0; // ширина

        val h = hr - 0.1
        val w = wr - 0.1

        if (userWindow.window.D == 0) {
            // Откуда взялись эти формулы, я сам не знаю, но дай Боже, что всё работает нормально.
            sumR = (2 * (hr + wr) * userWindow.profile.prR).roundToInt()
            sumG =
                (((h * w * userWindow.window.Ng / userWindow.window.N) + (((h - 0.1) * ((w / userWindow.window.N) - 0.1)) * (userWindow.window.Np + userWindow.window.No))) * userWindow.profile.getPriceGlassUnit(
                    userWindow.typeGlass
                )).roundToInt()

            sumH = (hr * userWindow.window.Nh * userWindow.profile.prH).roundToInt()
            sumS =
                ((2 * userWindow.profile.prSp * (hr + (wr / userWindow.window.N))) * userWindow.window.Np + (2 * userWindow.profile.prSo * (hr + (wr / userWindow.window.N))) * userWindow.window.No).roundToInt()
            sumI =
                ((4 + userWindow.window.Np * 4 + userWindow.window.No * 4 + userWindow.window.Nh * 2) * userWindow.additionalPrice.install).roundToInt()


            sumW = sumR + sumG + sumH + sumS + sumI


            // ДОПОЛНИТЕЛЬНЫЕ УСЛУГИ:
            for (extra in userWindow.extras) {
                if (extra.selected) {
                    sumO += when (extra.name.toLowerCase(Locale.ROOT)) {
                        "москитная сетка" -> extra.getPriceByTypeHome(userWindow.typeHome)
                            .roundToInt()
                        "подоконник" -> ((wr + 0.2) * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt()
                        "отлив" -> (wr * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt()
                        "откос" -> ((wr + 2 * hr) * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt();
                        else -> ((wr + 0.2) * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt()
                    }
                }
            }

            if (userWindow.isWinInstall)
                sumM = ((hr * wr) * userWindow.additionalPrice.montage).roundToInt()

        } else {
            // Дверь:
            val wdr = 0.7;
            val hd = hr - 0.1;
            val wd = wdr - 0.1;

            // Окно:
            val hwr = 1.4;
            val wwr = wr - 0.7;
            val hw = hwr - 0.1;
            val ww = wwr - 0.1;

            // Передаю привет в 2011 год, когда этот код писался на JS, я в душе не чаю, что тут происходит:
            // Окно + дверь
            sumR =
                (2 * (hwr + wwr) * userWindow.profile.prR + 2 * (hr + wdr) * userWindow.profile.prR).roundToInt()
            // Окно
            sumG =
                (((hw * ww * userWindow.window.Ng / userWindow.window.N) + (((hw - 0.1) * ((ww / userWindow.window.N) - 0.1)) * (userWindow.window.Np + userWindow.window.No))) * userWindow.profile.getPriceGlassUnit(
                    userWindow.typeGlass
                )).roundToInt();
            // Дверь:
            sumG += ((hd - 0.1) * (wd - 0.1) * userWindow.profile.getPriceGlassUnit(userWindow.typeGlass)).roundToInt();
            sumH = (hwr * userWindow.window.Nh * userWindow.profile.prH).roundToInt()

            // Окно
            sumS =
                ((2 * userWindow.profile.prSp * (hwr + (wwr / userWindow.window.N))) * userWindow.window.Np +
                        (2 * userWindow.profile.prSo * (hwr + (wwr / userWindow.window.N))) * userWindow.window.No).roundToInt();
            //дверь
            sumS += ((2 * userWindow.profile.prSp * (hr + wdr)) * userWindow.window.Dp +
                    (2 * userWindow.profile.prSo * (hr + wdr)) * userWindow.window.Do).roundToInt();

            sumI =
                ((4 + userWindow.window.Np * 4 + userWindow.window.No * 4 + userWindow.window.Nh * 2 + 10) * userWindow.additionalPrice.install).roundToInt()

            sumW = sumR + sumG + sumH + sumS + sumI

            // ДОПОЛНИТЕЛЬНЫЕ УСЛУГИ:
            for (extra in userWindow.extras) {
                if (extra.selected) {
                    sumO += when (extra.name.toLowerCase(Locale.ROOT)) {
                        "москитная сетка" -> extra.getPriceByTypeHome(userWindow.typeHome)
                            .roundToInt()
                        "подоконник" -> ((wr + 0.2) * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt()
                        "отлив" -> (wr * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt()
                        "откос" -> ((wr + 2 * hr) * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt();
                        else -> ((wr + 0.2) * extra.getPriceByTypeHome(userWindow.typeHome)).roundToInt()
                    }
                }
            }

            if (userWindow.isWinInstall)
                sumM =
                    (((hwr * wwr) + (hr * wdr)) * userWindow.additionalPrice.montage).roundToInt();
        }

        sumW = (sumW * userWindow.additionalPrice.increasePercent).roundToInt()
        sumD =
            if (userWindow.isWinDelivery) userWindow.additionalPrice.delivery.roundToInt() else 0;
        sum = sumW + sumO + sumM;

        return TotalCost(
            sum = sum,
            sumW = sumW,
            sumO = sumO,
            sumD = sumD,
            sumM = sumM
        )

    }
}