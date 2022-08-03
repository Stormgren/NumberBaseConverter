import java.math.BigDecimal

fun main() {      
    // write yor code here
    val dwalin = readLine()!!.toBigDecimal()
    val balin = readLine()!!.toBigDecimal()
    val thorind = readLine()!!.toBigDecimal()

    val totalGold = dwalin + balin + thorind

    println(totalGold)
}