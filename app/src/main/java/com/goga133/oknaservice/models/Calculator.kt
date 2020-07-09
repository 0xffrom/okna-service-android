package com.goga133.oknaservice.models

import kotlin.math.roundToInt

class Calculator {
    /*
    N - кол-во створок
    Ng - кол-во глухих створок
    Np - кол-во поворотных створок
    No - кол-во поворотно-откидных створок
    Nh - кол-во перемычек
    Dp - кол-во поворотных дверей
    Do - кол-во поворотно-откидных дверей
*/
    data class Window(
        val minW: Int, val maxW: Int, val minH: Int,
        val maxH: Int, val N: Int, val Ng: Int, val Np: Int,
        val No: Int, val Nh: Int, val D: Int, val Dp: Int, val Do: Int
    )

    // sum - Общая сумма
    // sumW - Стоимость изделия
    // sumO - Стоимость опций
    // sumM - Стоимость монтажа
    // sumD - Стоимость доставки
    data class SummaryPrice(
        val sum: Int,
        val sumW: Int,
        val sumO: Int,
        val sumM: Int,
        val sumD: Int
    )

    class Price {
        fun getProfile(type: String, param: String): Double {
            return when (type) {
                // Бюджет:
                "p1" -> when (param) {
                    "prR" -> 253.5
                    "prH" -> 272.5
                    "prSp" -> 433.2
                    "prSo" -> 497.2
                    "prOne" -> 1628.0
                    "prTwo" -> 2253.0
                    else -> 0.0
                }
                // Оптиум
                "p2" -> when (param) {
                    "prR" -> 332.0
                    "prH" -> 321.0
                    "prSp" -> 581.0
                    "prSo" -> 686.0
                    "prOne" -> 1628.0
                    "prTwo" -> 2253.0
                    else -> 0.0
                }
                // Премиум
                "p3" -> when (param) {
                    "prR" -> 488.0
                    "prH" -> 473.0
                    "prSp" -> 720.0
                    "prSo" -> 826.0
                    "prOne" -> 2743.0
                    "prTwo" -> 2743.0
                    else -> 0.0
                }
                else -> 0.0
            }
        }

        val install = 55.25 // Работа
        val montage = 782 // Монтаж

        // Подоконник панельный, кирпичный:
        fun getSill(param: String): Int {
            return when (param) {
                "panel" -> 529
                "brick" -> 795
                else -> 0
            }
        }

        // Отлив панельный, кирпичный:
        fun getTide(param: String): Int {
            return when (param) {
                "panel" -> 195
                "brick" -> 350
                else -> 0
            }
        }

        // Откос панельный, кирпичный:
        fun getSlope(param: String): Int {
            return when (param) {
                "panel" -> 688
                "brick" -> 945
                else -> 0
            }
        }

        val grid = 0 // Москитная сетка
        val delivery = 1100 // Доставка
    }

    fun chooseWindow(type: String): Window {
        return when (type) {
            "1-1" -> Window(
                400,
                3000,
                400,
                3000,
                1,
                1,
                0,
                0,
                0,
                0,
                0,
                0
            )
            "1-2" -> Window(
                500,
                1000,
                500,
                2000,
                1,
                0,
                1,
                0,
                0,
                0,
                0,
                0
            )
            "1-3" -> Window(
                500,
                1000,
                500,
                2000,
                1,
                0,
                1,
                0,
                0,
                0,
                0,
                0
            )
            "1-4" -> Window(
                500,
                1000,
                500,
                2000,
                1,
                0,
                0,
                1,
                0,
                0,
                0,
                0
            )
            "1-5" -> Window(
                500,
                1000,
                500,
                2000,
                1,
                0,
                0,
                1,
                0,
                0,
                0,
                0
            )
            "1-6" -> Window(
                500,
                2500,
                500,
                2000,
                1,
                0,
                1,
                0,
                0,
                0,
                0,
                0
            )
            "2-1" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                2,
                0,
                0,
                1,
                0,
                0,
                0
            )
            "2-2" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                1,
                0,
                1,
                1,
                0,
                0,
                0
            )
            "2-3" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                1,
                0,
                1,
                1,
                0,
                0,
                0
            )
            "2-4" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                0,
                1,
                1,
                1,
                0,
                0,
                0
            )
            "2-5" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                0,
                1,
                1,
                1,
                0,
                0,
                0
            )
            "2-6" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                1,
                1,
                0,
                1,
                0,
                0,
                0
            )
            "2-7" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                1,
                1,
                0,
                1,
                0,
                0,
                0
            )
            "2-8" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                0,
                0,
                2,
                1,
                0,
                0,
                0
            )
            "2-9" -> Window(
                900,
                2000,
                500,
                2000,
                2,
                0,
                0,
                2,
                1,
                0,
                0,
                0
            )
            "3-1" -> Window(
                1400,
                3000,
                600,
                2000,
                3,
                3,
                0,
                0,
                2,
                0,
                0,
                0
            )
            "3-2" -> Window(
                1400,
                3000,
                600,
                2000,
                3,
                2,
                0,
                1,
                2,
                0,
                0,
                0
            )
            "3-3" -> Window(
                1400,
                3000,
                600,
                2000,
                3,
                2,
                1,
                0,
                2,
                0,
                0,
                0
            )
            "3-4" -> Window(
                1400,
                3000,
                600,
                2000,
                3,
                0,
                0,
                3,
                2,
                0,
                0,
                0
            )
            "3-5" -> Window(
                1400,
                3000,
                600,
                2000,
                3,
                0,
                1,
                2,
                2,
                0,
                0,
                0
            )
            "3-6" -> Window(
                1400,
                3000,
                600,
                2000,
                3,
                1,
                1,
                1,
                2,
                0,
                0,
                0
            )
            "3-7" -> Window(
                1400,
                3000,
                600,
                2000,
                3,
                0,
                2,
                1,
                2,
                0,
                0,
                0
            )
            "4-1" -> Window(
                1300,
                2000,
                1600,
                2200,
                1,
                0,
                0,
                1,
                0,
                1,
                1,
                0
            )
            "4-2" -> Window(
                1300,
                2000,
                1600,
                2200,
                1,
                1,
                0,
                0,
                0,
                1,
                1,
                0
            )
            "4-3" -> Window(
                1300,
                2000,
                1600,
                2200,
                1,
                1,
                0,
                0,
                0,
                1,
                0,
                1
            )
            "4-4" -> Window(
                2000,
                3000,
                1600,
                2200,
                2,
                1,
                0,
                1,
                1,
                1,
                1,
                0
            )
            "4-5" -> Window(
                2000,
                3000,
                1600,
                2200,
                2,
                1,
                0,
                1,
                1,
                1,
                1,
                0
            )
            "5-1" -> Window(
                1000,
                2000,
                1600,
                3000,
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            )
            "5-2" -> Window(
                1000,
                2000,
                1600,
                3000,
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            )
            "5-3" -> Window(
                1000,
                2000,
                1600,
                3000,
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            )
            "5-4" -> Window(
                1000,
                2000,
                1600,
                3000,
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            )
            "5-5" -> Window(
                1500,
                3000,
                1600,
                3000,
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            )
            else -> throw IllegalArgumentException()
        }
    }

    fun calculatePrice(
        window: Window, h: Int, w: Int,
        typeProfile: String, typeGlass: String, typeHome: String,
        isWinSill: Boolean, isWinTide: Boolean, isWinSlope: Boolean,
        isWinGrid: Boolean, isWinInstall: Boolean, isWinDelivery: Boolean
    ): SummaryPrice {

        // window - Параметры окна
        val sum: Int // Общая стоимость
        val sumW: Int // Стоимость изделия
        val sumR: Int // Стоимость рамы
        var sumG: Int = 0 // Стоимость стеклопакета
        var sumS: Int = 0 // Стоимость створок
        val sumH: Int // Стоимость перемычек
        val sumI: Int // Стоимость работы
        var sumD: Int = 0 // Стоимость доставки
        var sumO: Int = 0 // Стоимость опций
        var sumM: Int = 0 // Стоимость монтажа
        var priceSill: Int = 0 // Цена подоконника
        var priceTide: Int = 0// Цена отлива
        var priceSlope: Int = 0// Цена откоса

        val hr: Double = h / 1000.0; // высота
        val wr: Double = w / 1000.0; // ширина

        val h = hr - 0.1
        val w = wr - 0.1

        val price = Price()

        if (window.D == 0) {
            // Откуда взялись эти формулы, я сам не знаю, но дай Боже, что всё работает нормально.
            sumR = (2 * (hr + wr) * price.getProfile(typeProfile, "prR")).roundToInt()

            sumG = (((h * w * window.Ng / window.N) +
                    (((h - 0.1) * ((w / window.N) - 0.1)) * (window.Np + window.No))) * price.getProfile(
                typeProfile,
                typeGlass
            )).roundToInt()

            sumH = (hr * window.Nh * price.getProfile(typeProfile, "prH")).roundToInt()
            sumS = ((2 * price.getProfile(
                typeProfile,
                "prSp"
            ) * (hr + (wr / window.N))) * window.Np + (2 * price.getProfile(
                typeProfile,
                "prSo"
            ) * (hr + (wr / window.N))) * window.No).roundToInt()
            sumI =
                ((4 + window.Np * 4 + window.No * 4 + window.Nh * 2) * price.install).roundToInt()


            sumW = sumR + sumG + sumH + sumS + sumI

            if (isWinSill) {
                priceSill = ((wr + 0.2) * price.getSill(typeHome)).roundToInt()
                sumO += priceSill;
            }
            if (isWinTide) {
                priceTide = ((wr * price.getTide(typeHome))).roundToInt()
                sumO += priceTide;
            }
            if (isWinSlope) {
                priceSlope = ((wr + 2 * hr) * price.getSlope(typeHome)).roundToInt()
                sumO += priceSlope;
            }
            if (isWinGrid)
                sumO += price.grid;

            if (isWinInstall)
                sumM = ((hr * wr) * price.montage).roundToInt()
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
            sumR = (2 * (hwr + wwr) * price.getProfile(
                typeProfile,
                "prR"
            ) + 2 * (hr + wdr) * price.getProfile(typeProfile, "prR")).roundToInt()
            // Окно
            sumG =
                (((hw * ww * window.Ng / window.N) + (((hw - 0.1) * ((ww / window.N) - 0.1)) * (window.Np + window.No))) * price.getProfile(
                    typeProfile,
                    typeGlass
                )).roundToInt();
            // Дверь:
            sumG += ((hd - 0.1) * (wd - 0.1) * price.getProfile(
                typeProfile,
                typeGlass
            )).roundToInt();
            sumH = (hwr * window.Nh * price.getProfile(typeProfile, "prH")).roundToInt()

            // Окно
            sumS = ((2 * price.getProfile(
                typeProfile,
                "prSp"
            ) * (hwr + (wwr / window.N))) * window.Np +
                    (2 * price.getProfile(
                        typeProfile,
                        "prSo"
                    ) * (hwr + (wwr / window.N))) * window.No).roundToInt();
            //дверь
            sumS += ((2 * price.getProfile(typeProfile, "prSp") * (hr + wdr)) * window.Dp +
                    (2 * price.getProfile(
                        typeProfile,
                        "prSo"
                    ) * (hr + wdr)) * window.Do).roundToInt();

            sumI =
                ((4 + window.Np * 4 + window.No * 4 + window.Nh * 2 + 10) * price.install).roundToInt()

            sumW = sumR + sumG + sumH + sumS + sumI

            if (isWinSill) {
                priceSill = ((wr + 0.2) * price.getSill(typeHome)).roundToInt();
                sumO += priceSill;
            }
            if (isWinTide) {
                priceTide = (wr * price.getTide(typeHome)).roundToInt();
                sumO += priceTide;
            }
            if (isWinSlope) {
                priceSlope = ((wr + 2 * hr) * price.getSlope(typeHome)).roundToInt();
                sumO += priceSlope;
            }
            if (isWinGrid)
                sumO += price.grid;

            if (isWinInstall)
                sumM = (((hwr * wwr) + (hr * wdr)) * price.montage).roundToInt();
        }

        sumD = if (isWinDelivery) price.delivery else 0;
        sum = sumW + sumO + sumM;

        return SummaryPrice(
            sum = sum,
            sumW = sumW,
            sumO = sumO,
            sumD = sumD,
            sumM = sumM
        )

    }
}