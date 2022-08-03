import java.math.BigDecimal

fun main() {
    // write your code here
    val str1 = readLine()!!.toBigDecimal()
    val str2 = readLine()!!.toBigDecimal()

    val equation = str1 * str2
    println(equation)
}