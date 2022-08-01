package converter

    fun convertor(num: Int, base: Int){
        var n = num
        var remainder: Int
        var step = 0
        var hexChars = "ABCDEF"
        var res = ""
        while (n != 0) {
            remainder = n % base
            n /= base
            if(remainder > 9){
                var ind = remainder - 9

                res+="${hexChars.get(ind - 1)}"
            } else {
                res += "$remainder"
            }
            step++
        }
        //1100111
        println("Conversion result: ${res.reversed()}")
    }
    fun decimalConvertor(num: String, base: Int){
        var hex = "0123456789abcdef"
        var i = 0
        var sol = 0

        if(base == 16) {
            for (i in 0..num.length - 1) {
                val char = num.get(i)
                val value = hex.indexOf(char)
                sol = 16 * sol + value
            }

        } else {
            var numberify = num.toInt()
            while (numberify != 0) {
                sol += (numberify % 10 * Math.pow(base.toDouble(), i.toDouble())).toInt()
                ++i
                numberify /= 10
            }
        }

        println("Conversion to decimal result: $sol")
    }

fun main() {
    var state = true
    while(state){
        println("Do you want to convert /from decimal or /to decimal? (to quit type /exit)")
        val str = readLine()!!
        if(str == "/exit"){
            state = false
            break
        } else if (str == "/from"){
            println("Enter number in decimal system:")
            val num = readLine()!!.toInt()
            println("Enter target base: ")
            val base = readLine()!!.toInt()
            convertor(num, base)
        } else if (str == "/to"){
            println("Enter source number:")
            val num = readLine()!!
            println("Enter source base: ")
            val base = readLine()!!.toInt()
            decimalConvertor(num, base)
        }
    }

}