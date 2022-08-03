import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode.FLOOR
import java.math.RoundingMode.HALF_UP

val BASES = 2..36

fun main() {
    val message = "Enter two numbers in format: {source base} {target base} (To quit type /exit) "

    while (true) {
        when (val command = getString(message)) {
            "/exit" -> return
            else -> baseToBase(command.split(" "))
        }
        println()
    }
}

fun baseToBase(stringBases: List<String>) {
    if (stringBases.size == 2) {
        val source = getBase(stringBases[0]) ?: return
        val target = getBase(stringBases[1]) ?: return
        val message = "Enter number in base $source to convert to base $target (To go back type /back) "
        var command = getString(message)

        while (command != "/back") {
            val negative = command.startsWith('-')
            val number = if (negative) command.substring(1) else command
            var result = getResult(source, target, number)

            if (negative && result != null) result = "-$result"
            println(if (result != null) "Conversion result: $result" else "$command is invalid for base $source")
            command = getString("\n" + message)
        }
    } else println("Please enter two numbers within ($BASES)")
}

fun getBase(number: String): BigInteger? {
    val base = number.toIntOrNull()

    return if (base == null || base !in BASES) {
        println("$number is not a valid base ($BASES)")
        null
    } else base.toBigInteger()
}

fun getResult(source: BigInteger, target: BigInteger, number: String): String? {
    return if (number.count { it == '.' } == 1) {
        getFraction(source.toBigDecimal(), target.toBigDecimal(), number)
    } else getWholeNumber(source, target, number)
}

fun getWholeNumber(source: BigInteger, target: BigInteger, number: String): String? {
    val decimal = (if (source == BigInteger.TEN) number.toBigIntegerOrNull() else
        (baseToBigInt(number, source))) ?: return null

    return if (target == BigInteger.TEN) decimal.toString() else bigIntToBase(decimal, target)
}

fun getFraction(source: BigDecimal, target: BigDecimal, number: String): String? {
    val decimal = (if (source.compareTo(BigDecimal.TEN) == 0) number.toBigDecimalOrNull() else
        baseToBigDecimal(number, source)) ?: return null

    return if (target.compareTo(BigDecimal.TEN) == 0) decimal.setScale(5, HALF_UP).toString()
    else bigDecimalToBase(decimal.setScale(7, HALF_UP), target.toInt())
}

fun bigIntToBase(number: BigInteger, base: BigInteger): String {
    var result = ""
    var quotient = number

    while (quotient >= base) {
        result = getBaseConversion(quotient.remainder(base).toInt()) + result
        quotient /= base
    }
    return getBaseConversion(quotient.toInt()) + result
}

fun bigDecimalToBase(number: BigDecimal, base: Int): String {
    var result = ""
    val wholeNumber = number.setScale(0, FLOOR)
    var fraction = (number - wholeNumber).toDouble()

    while (fraction > 0 && result.length < 5) {
        fraction *= base
        val wholeNum = fraction.toInt()
        fraction -= wholeNum
        result += getBaseConversion(wholeNum)
    }
    if (result.length < 5) result += "0".repeat(5 - result.length)
    return bigIntToBase(wholeNumber.toBigInteger(), base.toBigInteger()) + ".$result"
}

fun getBaseConversion(remainder: Int) = (if (remainder >= 10) 'a' + remainder - 10 else remainder).toString()

fun baseToBigInt(number: String, base: BigInteger): BigInteger? {
    val list = if (isValid(number)) number.reversed().toCharArray()
        .map { getDecimalConversion(it).toBigInteger() } else emptyList()

    return if (list.isEmpty() || list.any { it >= base }) null else {
        var multiply = BigInteger.ONE
        list.sumOf { it * multiply.also { multiply *= base } }
    }
}

fun baseToBigDecimal(number: String, base: BigDecimal): BigDecimal? {
    val (whole, fraction) = number.split(".").map { it.ifEmpty { "0" } }
    val wholeNum = (baseToBigInt(whole, base.toBigInteger()) ?: return null).toBigDecimal()
    val list = if (isValid(fraction)) fraction.toCharArray()
        .map { getDecimalConversion(it).toBigDecimal() } else emptyList()

    return if (list.isEmpty() || list.any { it >= base }) null else {
        var divisor = base
        wholeNum + list.sumOf { it.divide(divisor, 10, HALF_UP).also { divisor *= base } }
    }
}

fun isValid(number: String) = number.isNotEmpty() && number.all { it.isLetterOrDigit() }

fun getDecimalConversion(number: Char) = if (number.isLetter()) number.code - 87 else number.digitToInt()

fun getString(message: String): String {
    print(message)
    return readLine()!!.lowercase()
}