package bpmn2.storm.backend.delegate

import PdfPrinter
import bpmn2.storm.backend.model.PKI
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Component("monitorDelegate")
class Pki2PdfDelegate: JavaDelegate {
    @Value("\${smtpHost}")
    private var smtpHost: String? = null
    @Value("\${smtpPort}")
    private var smtpPort: Int? = null
    @Value("\${smtpUserName}")
    private var smtpUserName: String? = null
    @Value("\${smtpPassword}")
    private var smtpPassword: String? = null
    @Value("\${smtpFromName}")
    private var smtpFromName: String? = null
    @Value("\${smtpFromEmail}")
    private var smtpFromEmail: String? = null

    override fun execute(execution: DelegateExecution) {
        val d = execution.getVariable("application") as PKI

        val pdf = PdfPrinter()
        pdf.setFont(14f,Alignment.LEFT)
        pdf.textPrint(" ")
        pdf.textPrint("АО «ВЗМЭО»")
        pdf.setFont(10f,Alignment.RIGHT)
        var formatter = DateTimeFormatter.ofPattern("« dd » MMMM yyyyг.")
        var formattedDate = LocalDate.now().format(formatter)
        pdf.textPrint("СК. СТП.З-113-11\nУтверждаю:\nГенеральный директор\n_______________М.А. Святогоров\n"+ formattedDate.toString()+"\n")
        pdf.setFont(14f,Alignment.CENTER)
        pdf.textPrint("РАЗРЕШЕНИЕ НА ЗАМЕНУ № "+d.nomer+"\n")
        pdf.setFont(10f,Alignment.LEFT)
        pdf.textPrint("Наименование изделия: "+d.naim+"\nОбозначение изделия: "+d.obozn)
        pdf.startY = pdf.curY + pdf.leading
        pdf.isStart = true
        pdf.setFont(10f,Alignment.RIGHT)
        pdf.textPrint("Заказ:")
        pdf.textPrint("Заказ: "+d.zakaz)
        pdf.addCell(0f,50f,"Требование КД (ВСНРМ)",Alignment.CENTER,13)
        pdf.addCell(50f,100f,"Разрешено",Alignment.CENTER,11)
        pdf.printRow()
        pdf.addCell(0f,23f,"Марка материала", Alignment.CENTER,13)
        pdf.addCell(23f,32f,"Кол./кг",Alignment.CENTER)
        pdf.addCell(32f,41.5f,"Стоимость руб",Alignment.CENTER)
        pdf.addCell(41.5f,50f,"Σ, руб",Alignment.CENTER)
        pdf.addCell(50f,73f,"Марка материала", Alignment.CENTER)
        pdf.addCell(73f,82f,"Кол./кг",Alignment.CENTER)
        pdf.addCell(82f,91.5f,"Стоимость руб",Alignment.CENTER)
        pdf.addCell(91.5f,100f,"Σ, руб",Alignment.CENTER,11)
        pdf.printRow()
        pdf.addCell(0f,23f,d.material1,Alignment.LEFT,13)
        pdf.addCell(23f,32f,d.kol1,Alignment.CENTER)
        pdf.addCell(32f,41.5f,d.stoim1,Alignment.RIGHT)
        pdf.addCell(41.5f,50f,d.itog1,Alignment.RIGHT)
        pdf.addCell(50f,73f,d.material2)
        pdf.addCell(73f,82f,d.kol2,Alignment.CENTER)
        pdf.addCell(82f,91.5f,d.stoim2,Alignment.RIGHT)
        pdf.addCell(91.5f,100f,d.itog2,Alignment.RIGHT,11)
        pdf.printRow()
        pdf.addCell(0f,50f,"Причина/виновник",Alignment.LEFT,13)
        pdf.addCell(50f,73f,"Дополнительная трудоёмкость")
        pdf.addCell(73f,100f,d.trud + " " + d.p8,Alignment.LEFT,11)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,9f,"Составил",Alignment.LEFT,0)
        pdf.addCell(11f,30f," ",Alignment.LEFT,1)
        pdf.addCell(30f,50f,d.p1,Alignment.CENTER,0)
        pdf.addCell(50f,65f,"Начальник ОМТС",Alignment.LEFT,0)
        pdf.addCell(65f,80f," ",Alignment.LEFT,1)
        pdf.addCell(80f,100f,d.p2,Alignment.CENTER,0)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,100f,"Техническое заключение: "+d.to_zakl,Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,16f,"Ведущий технолог",Alignment.LEFT,0)
        pdf.addCell(16f,30f," ",Alignment.LEFT,1)
        pdf.addCell(30f,50f,d.p3,Alignment.CENTER,0)
        pdf.addCell(50f,69f,"Ведущий конструктор",Alignment.LEFT,0)
        pdf.addCell(69f,80f," ",Alignment.LEFT,1)
        pdf.addCell(80f,100f,d.p6,Alignment.CENTER,0)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,16f,"Главный технолог",Alignment.LEFT,0)
        pdf.addCell(16f,30f," ",Alignment.LEFT,1)
        pdf.addCell(30f,50f,d.p4,Alignment.CENTER,0)
        pdf.addCell(50f,69f,"Главный конструктор",Alignment.LEFT,0)
        pdf.addCell(69f,80f," ",Alignment.LEFT,1)
        pdf.addCell(80f,100f,d.p7,Alignment.CENTER,0)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,33f,"Согласовано: Представитель заказчика",Alignment.LEFT,0)
        pdf.addCell(34f,100f," ",Alignment.LEFT,1)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,61f,"Дополнительные затраты",Alignment.CENTER,1)
        pdf.printRow()
        pdf.addCell(0f,31f," ")
        pdf.addCell(31f,41f,"ОМТС",Alignment.CENTER)
        pdf.addCell(41f,51f,"ТОиМ",Alignment.CENTER)
        pdf.addCell(51f,61f,"Итого",Alignment.CENTER)
        pdf.printRow()
        pdf.addCell(0f,31f,"Материалы, руб")
        pdf.addCell(31f,41f,d.peo_mat1.toString(),Alignment.RIGHT)
        pdf.addCell(41f,51f,d.peo_mat2.toString(),Alignment.RIGHT)
        pdf.addCell(51f,61f,"   ",Alignment.RIGHT)
        pdf.printRow()
        pdf.addCell(0f,31f,"Заработная плата и отчисления, руб")
        pdf.addCell(31f,41f,d.peo_zar1.toString(),Alignment.RIGHT)
        pdf.addCell(41f,51f,d.peo_zar2.toString(),Alignment.RIGHT)
        pdf.addCell(51f,61f," ",Alignment.RIGHT)
        pdf.printRow()
        pdf.addCell(0f,31f,"Итого доп.затраты, руб")
        pdf.addCell(31f,41f," ",Alignment.RIGHT)
        pdf.addCell(41f,51f," ",Alignment.RIGHT)
        pdf.addCell(51f,61f," ",Alignment.RIGHT)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,9f,"Экономист",Alignment.LEFT,0)
        pdf.addCell(11f,30f," ",Alignment.LEFT,1)
        pdf.addCell(30f,50f,d.p9,Alignment.CENTER,0)
        pdf.addCell(50f,65f,"Начальник ПЭО",Alignment.LEFT,0)
        pdf.addCell(65f,80f," ",Alignment.LEFT,1)
        pdf.addCell(80f,100f,d.p10,Alignment.CENTER,0)
        pdf.printRow()
        pdf.addCell(0f,100f," ",Alignment.LEFT,0)
        pdf.printRow()
        pdf.addCell(0f,31f,"Согласовано: Технический директор",Alignment.LEFT,0)
        pdf.addCell(31f,45f," ",Alignment.LEFT,1)
        pdf.addCell(45f,61f,d.p11,Alignment.CENTER,0)
        pdf.printRow()

        //println("Program arguments: ${args.joinToString()}")
        pdf.savePdf(d.nomer + ".pdf")

        val lst = d.st.toLong() + 1
        d.st = d.st
        d.st = lst.toString()
        val st = execution.setVariable("application",lst)

        /*val emailTo = execution.processEngineServices.identityService.createUserQuery().userId(application.employee)?.singleResult()?.email

        val subject = "Your trip from ${application.from} to ${application.to} is ready!"
        var body = "Here you trip details \n" +
                "------ \n" +
                "From: ${application.from} \n" +
                "To:  ${application.to} \n" +
                "Fly description:  ${application.flyDescription} \n" +
                "Hotel description:  ${application.hotelDescription} \n" +
                "------ \n" +
                "Bye!"

        if (emailTo != null) {
            val email = EmailBuilder.startingBlank()
                    .from(smtpFromName, smtpFromEmail!!)
                    .to(emailTo)
                    .withSubject(subject)
                    .withPlainText(body)
                    .buildEmail()


            val mailer = MailerBuilder
                    .withSMTPServer(smtpHost, smtpPort, smtpUserName, smtpPassword)
                    .withTransportStrategy(TransportStrategy.SMTPS)
                    .clearEmailAddressCriteria() // turns off email validation
                    .buildMailer()

            mailer.sendMail(email)
        }*/
    }
}

