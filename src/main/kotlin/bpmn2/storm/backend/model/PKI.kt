package bpmn2.storm.backend.model

data class PKI (
    val st: Int = 1,
    val naim: String = "",
    val obozn: String = "",
    val zakaz: String = "",
    val material1: String = "",
    val material2: String = "",
    val kol1: String = "",
    val kol2: String = "",
    val stoim1: Double = 0.0,
    val stoim2: Double = 0.0,
    val itog1:  Double = 0.0,
    val itog2:  Double = 0.0,
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
    val trud:  String = ""
)
