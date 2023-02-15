import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.awt.Color
import java.io.File

class PdfPrinter {
    private val cs : PDPageContentStream
    private val doc = PDDocument()
    private val page = PDPage()
    var mediabox: PDRectangle = PDRectangle()
    private val pdfFont  = PDType0Font.load(doc, File("src/main/resources/UniNeueBook.ttf"))
    private val pdfFontB = PDType0Font.load(doc, File("src/main/resources/UniNeueBold.ttf"))
    private val pdfFontI = PDType0Font.load(doc, File("src/main/resources/UniNeueBook-Italic.ttf"))
    private var fontSize = 25f//размер шрифта текущий
    private var fontHeight = 0f
    private var align: Alignment = Alignment.LEFT
    private var margin = 0f
    var width = 0f
    var startX = 0f
    var startY = 0f
    var curX = 0f
    var curY = 0f
    private var newX = 0f
    private var newY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    var isStart = true
    var leading = 0f
    val koef = 2.834646f
    var nextLine = false
    val tableRow = ArrayList<TableCell>()


    init {
        val page = PDPage(PDRectangle.A4)
        doc.addPage(page)
        this.cs = PDPageContentStream(doc, page)
        setBox(page.getMediaBox())
        //val fontFile = File("src/main/resources/unineuelight.ttf")
        //val fontFile =
        //val fontFile = File(PdfFont.Italic)
        startX = mediabox.lowerLeftX + margin //Начало текстового блока
        startY = mediabox.upperRightY - margin
    }

    fun setBox(mediabox: PDRectangle, margin: Float = 10f)  {
        this.margin = margin * koef
        this.mediabox = mediabox
        width = mediabox.getWidth() - 2 * margin
    }

    fun setFont(
        fontSize: Float = 10f,
        align: Alignment = Alignment.LEFT
    ) {
        this.align = align
        this.fontSize = fontSize

        this.leading = 1.5f * fontSize
        this.fontHeight = ( pdfFont.getFontDescriptor().getCapHeight()) / 1000 * this.fontSize
        cs.setFont(pdfFont, fontSize)
    }

    private fun gotoXY() {
        if(isStart) {
            curX = mediabox.lowerLeftX
            curY = mediabox.lowerLeftY
            newX = startX
            newY = startY
            isStart = false
        }
        cs.newLineAtOffset(newX - curX, newY - curY)
        curX = newX
        curY = newY
    }

    private fun fAlign(stringWidth: Float = 0f) {
        newX = when(align) {
            Alignment.LEFT -> mediabox.lowerLeftX  + margin
            Alignment.RIGHT -> mediabox.upperRightX - margin - fontSize * stringWidth / 1000
            Alignment.CENTER -> (mediabox.upperRightX + mediabox.lowerLeftX) / 2 - fontSize * stringWidth / 2000
        }
        System.out.printf("stringWidth = %f \t", stringWidth)
        offsetX = newX - curX
        gotoXY()
    }

    fun textPrint(intext :String) {
        val ltext = intext.split("\n")

        val lines = ArrayList<String>()
        for(stext in ltext)
        {
            var text = stext
            var lastSpace = -1
            while (text.isNotEmpty()) {
                var spaceIndex = text.indexOf(' ', lastSpace + 1)
                if (spaceIndex < 0)
                    spaceIndex = text.length
                var subString = text.substring(0, spaceIndex)
                val size = fontSize * pdfFont.getStringWidth(subString) / 1000f
                if (size > width) {
                    if (lastSpace < 0)
                        lastSpace = spaceIndex
                    subString = text.substring(0, lastSpace)
                    lines.add(subString)
                    text = text.substring(lastSpace).trim()
                    System.out.printf("'%s' is line\n", subString)
                    lastSpace = -1
                } else if (spaceIndex == text.length) {
                    lines.add(text)
                    System.out.printf("'%s' is line\n", text)
                    text = ""
                } else {
                    lastSpace = spaceIndex
                }
            }
        }
        if(!nextLine) {
            cs.beginText()
            gotoXY()
            isStart = true
            gotoXY()
        }
        gotoXY()
        System.out.printf("lowerLeftX = %f\tupperRightX = %f\toffsetX = %f\tcurX = %f\tnewX = %f\nlowerLeftY = %f\tupperRightY = %f\toffsetY = %f\t\tcurY = %f\tnewY = %f\tmargin = %f\n",
            mediabox.lowerLeftX,mediabox.upperRightX,offsetX,curX,newX,mediabox.lowerLeftY,mediabox.upperRightY,offsetY,curY,newY,margin)

        for (line in lines)
        {
            fAlign(pdfFont.getStringWidth(line))
            System.out.printf("'%s' :( %f : %f )\n", line, curX, curY)
            cs.showText(line)
            newX -= offsetX
            newY -= leading
            gotoXY()
        }
        nextLine = intext.endsWith('\n')
        if(!nextLine) {
            cs.endText()
        }
    }

    fun addCell(
        startProcent:Float = 0f,
        endProcent:Float = 100f,
        text:String,
        cellFontAlign:Alignment = Alignment.LEFT,
        frame:Int = 15,
        cellPdfFont:Int = 0,
    ) {
        var cellPdfFont2 = when(cellPdfFont) {
            1 -> pdfFontB
            2 -> pdfFontI
            else -> {pdfFont}
        }
        tableRow.add(TableCell(
            text,
            startProcent,
            endProcent,
            margin,
            mediabox.width,
            1f,// * leading,
            fontSize,
            cellFontAlign,
            cellPdfFont2,
            frame,
            curY
        ))
    }

    fun printRow() {
        var minY = mediabox.upperRightY
        for(cell in tableRow) {
            if(cell.frameDownY < minY && cell.textLine.size > 0)
                minY = cell.frameDownY
        }
        startY = curY
        for(cell in tableRow) {
            cell.frameDownY = minY
            cs.beginText()
            curY = 0f
            curX = 0f
            for(line in cell.textLine) {
                newX = line.x
                newY = line.y
                System.out.printf("'%s'\tcur(%f, %f)\tnew(%f,%f)\t%f\n",
                    line.text,curX,curY,newX,newY,line.y)
                gotoXY()
                cs.showText(line.text)
            }
            cs.endText()
            cs.setStrokingColor(Color.BLACK)
            cs.setLineWidth(1f);
            cs.moveTo(cell.frameLeftX,     cell.frameUpY)
            System.out.printf("%f,\t%f) - (%f,\t%f),\t%f\n",
                cell.frameLeftX, cell.frameUpY, cell.frameRightX, cell.frameDownY, cell.cellMargin)
            if(cell.frame and 8 > 0)
                cs.lineTo(cell.frameRightX,   cell.frameUpY)
            else
                cs.moveTo(cell.frameRightX,   cell.frameUpY)
            if(cell.frame and 4 > 0)
                cs.lineTo(cell.frameRightX,   cell.frameDownY)
            else
                cs.moveTo(cell.frameRightX,   cell.frameDownY)
            if(cell.frame and 1 > 0)
                cs.lineTo(cell.frameLeftX, cell.frameDownY)
            else
                cs.moveTo(cell.frameLeftX, cell.frameDownY)
            if(cell.frame and 2 > 0)
                cs.lineTo(cell.frameLeftX, cell.frameUpY)
            else
                cs.moveTo(cell.frameLeftX, cell.frameUpY)
            cs.stroke();
        }
        curY = tableRow[0].frameDownY - fontSize * 0.8f
        tableRow.clear()
    }

    fun savePdf(filename: String) {
        cs.close()
        doc.use { doc ->
            doc.save(filename)
        }
    }
}