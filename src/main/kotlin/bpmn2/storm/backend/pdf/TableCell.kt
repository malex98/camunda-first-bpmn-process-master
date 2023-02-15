import org.apache.pdfbox.pdmodel.font.PDFont

class TableCell constructor(
    inText:String,
    startProcent:Float,
    endProcent:Float,
    mediaMargin:Float,
    mediaWidth:Float,
    val cellMargin: Float,
    private val fontSize: Float,
    private val fontAlign: Alignment,
    private val font: PDFont,
    val frame: Int, //1 - снизу, 2 - слева, 4 - справа, 8 - сверху
    frameUpY:Float,
){
    val frameUpY:Float = frameUpY + fontSize * 0.8f
    private var downY: Float = 0f
    var frameDownY:Float = 0f
    var frameLeftX  = (mediaWidth - 2 * mediaMargin) * startProcent / 100 + mediaMargin
    var frameRightX = (mediaWidth - 2 * mediaMargin) *   endProcent / 100 + mediaMargin
    private val leftX = when(frame and 2) {
        2 ->    frameLeftX + cellMargin
        else -> frameLeftX
    }
    private val rightX = when(frame and 4) {
        4 ->    frameRightX - cellMargin
        else -> frameRightX
    }
    private val upY = when(frame and 8) {
        8 ->    frameUpY - cellMargin
        else -> frameUpY
    }
    val textLine = ArrayList< PdfTableCellLine>()

    init {
        var text = inText
        var lastSpace = -1
        var oldTextSize = 0f
        while (text.isNotEmpty()) {
            var spaceIndex = text.indexOf(' ', lastSpace + 1)
            if (spaceIndex < 0)
                spaceIndex = text.length
            var subString = text.substring(0, spaceIndex)
            val textSize = fontSize * font.getStringWidth(subString) / 1000f
            if (textSize > (rightX - leftX)) {
                if (lastSpace < 0)
                    lastSpace = spaceIndex;
                addText(text.substring(0, lastSpace), oldTextSize)
                text = text.substring(lastSpace).trim()
                lastSpace = -1
            } else if (spaceIndex == text.length) {
                addText(text, textSize)
                text = ""
            } else {
                lastSpace = spaceIndex
            }
            oldTextSize = textSize
        }
    }

    private fun addText(text:String, textSize:Float) {
        val x = when(fontAlign) {
            Alignment.RIGHT -> rightX - textSize
            Alignment.CENTER -> (leftX + rightX - textSize) / 2f
            Alignment.LEFT -> leftX
        }
        downY = upY - fontSize * 1.5f * textLine.size
        if((frame and 8) > 0)     downY -= cellMargin
        textLine.add(PdfTableCellLine(text,x,downY))
        frameDownY = when (frame and 1) {
            1 -> downY - cellMargin - fontSize * 0.2f
            else -> downY - fontSize * 0.2f
        }
    }
}