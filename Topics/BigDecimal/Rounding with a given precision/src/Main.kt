import java.math.BigDecimal
import java.math.RoundingMode

fun main() {             
    // write your code here
    var str = readLine()!!
    val newScale = readLine()!!.toInt()
    val bd = str.toBigDecimal()
    println(bd.setScale(newScale, RoundingMode.HALF_DOWN))
}