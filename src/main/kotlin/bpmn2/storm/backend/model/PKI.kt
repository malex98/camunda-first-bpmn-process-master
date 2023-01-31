package bpmn2.storm.backend.model
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

val mapper = ObjectMapper().registerModule(KotlinModule())
data class PKI (
    var st: String = "1",
    val nomer: String="Номер",//номер ПКИ
    val naim: String = "",//наименование изделия
    val obozn: String = "",//Обозначение
    val zakaz: String = "",//Номер заказа
    val material1: String = "",//Материал что меняем
    val material2: String = "",//Материал на что меняем
    val kol1: String = "",//Количество материала что меняем
    val kol2: String = "",//Количество материала на что меняем
    val stoim1: String = "",//Стоимость (цена) материала что меняем
    val stoim2: String = "",//Стоимость (цена) материала на что меняем
    val itog1:  String = "",//Стоимость (итог) материала что меняем
    val itog2:  String = "",////Стоимость (итог) материала на что меняем
    val to_zakl: String = "",
    val to_sogl: String = "",
    val peo_mat1: String = "",
    val peo_mat2: String = "",
    val peo_zar1: String = "",
    val peo_zar2: String = "",
    val tp: String = "",
    val es: String = "",
    val so: String = "",
    val tr: String = "",
    val trud: String = "",
    var selected: String = "1"
)

