fun main() {

    fun String.toSNAFU(): List<Int> {
        return this.map {
            when (it) {
                '=' -> -2
                '-' -> -1
                else -> it - '0'
            }
        }.reversed()
    }

    fun computeCarry(sum: Int): Pair<Int, Int> {
        var cur = sum % 5
        var newCarry = sum / 5
        when (sum % 5) {
            3 -> {
                cur = -2
                newCarry++
            }
            4 -> {
                cur = -1
                newCarry++
            }
            -3 -> {
                cur = 2
                newCarry--
            }
            -4 -> {
                cur = 1
                newCarry--
            }
        }

        return cur to newCarry
    }

    fun add(a: List<Int>, b: List<Int>): List<Int> {
        val result = mutableListOf<Int>()
        var carry = 0
        for (i in 0 until maxOf(a.size, b.size)) {
            val sum = (a.getOrNull(i) ?: 0) + (b.getOrNull(i) ?: 0) + carry
            val (cur, newCarry) = computeCarry(sum)

            result.add(cur)
            carry = newCarry
        }

        while (carry != 0) {
            val (cur, newCarry) = computeCarry(carry)
            result.add(cur)
            carry = newCarry
        }

        return result
    }


    fun part1(input: List<String>): String {
        var ans = listOf(0)
        for (sna in input) {
            ans = add(ans, sna.toSNAFU())
        }

        return ans.map {
            when (it) {
                -2 -> '='
                -1 -> '-'
                else -> '0' + it
            }
        }.reversed().joinToString("")
    }

    println(part1(readInput("data/Day25_test")))
    println(part1(readInput("data/Day25")))
}
