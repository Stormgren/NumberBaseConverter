import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    // write your code here
    val starting = readLine()!!.toBigDecimal()
    val interest = readLine()!!.toBigDecimal()
    val years = readLine()!!.toInt()
    // var sol = starting * (BigDecimal.ONE + interest.setScale(2 + interest.scale()) / BigDecimal("100")).pow(years)
    val equation = interest.divide(BigDecimal(100)).setScale(2, RoundingMode.CEILING)
    val math = starting * (BigDecimal.ONE + equation).pow(years)
    println("Amount of money in the account: " + math.setScale(2, RoundingMode.CEILING))
}
