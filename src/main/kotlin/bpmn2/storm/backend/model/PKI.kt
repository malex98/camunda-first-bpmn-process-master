package bpmn2.storm.backend.model

data class PKI (
    val st: Int = 1,
    val nomer: String="Номер",//номер ПКИ
    val naim: String = "",//наименование изделия
    val obozn: String = "",//Обозначение
    val zakaz: String = "",//Номер заказа
    val material1: String = "",//Материал что меняем
    val material2: String = "",//Материал на что меняем
    val kol1: String = "",//Количество материала что меняем
    val kol2: String = "",//Количество материала на что меняем
    val stoim1: Double = 0.0,//Стоимость (цена) материала что меняем
    val stoim2: Double = 0.0,//Стоимость (цена) материала на что меняем
    val itog1:  Double = 0.0,//Стоимость (итог) материала что меняем
    val itog2:  Double = 0.0,////Стоимость (итог) материала на что меняем
    val to_zakl: String = "",
    val to_sogl: String = "",
    val peo_mat1: Double = 0.0,
    val peo_mat2: Double = 0.0,
    val peo_zar1: Double = 0.0,
    val peo_zar2: Double = 0.0,
    val is_tp: Boolean = true,
    val is_es: Boolean = false,
    val is_so: Boolean = false,
    val is_tr: Boolean = false,
    val trud:  String = "",
    val selected: Int
)
