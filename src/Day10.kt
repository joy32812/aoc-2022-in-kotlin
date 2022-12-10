fun main() {

    fun part1(input: List<String>): Int {
        var x = 1
        var cycle = 0

        var ans = 0
        fun checkCycle() {
            if ((cycle - 20) % 40 == 0) {
                ans += cycle * x
            }
        }

        input.forEach { line ->
            val splits = line.split(" ")
            val command = splits[0]

            if (command == "noop") {
                cycle ++
                checkCycle()
            } else {
                cycle ++
                checkCycle()
                cycle ++
                checkCycle()
                x += splits[1].toInt()
            }
        }

        return ans
    }


    fun part2(input: List<String>): Int {
        var x = 1
        var cycle = 0

        val crt = Array(6) { Array(40) { '.' } }
        fun checkCycle() {
            val z = cycle - 1
            val i = z / 40
            val j = z % 40

            if (j in (x - 1 .. x + 1)) {
                crt[i][j] = '#'
            }
        }


        input.forEach { line ->
            val splits = line.split(" ")
            val command = splits[0]

            if (command == "noop") {
                cycle ++
                checkCycle()
            } else {
                cycle ++
                checkCycle()
                cycle ++
                checkCycle()
                x += splits[1].toInt()
            }
        }

        crt.forEach { println(it.joinToString("")) }

        return 0
    }


    println(part1(readInput("data/Day10_test")))
    println(part1(readInput("data/Day10")))

    println(part2(readInput("data/Day10_test")))
    println(part2(readInput("data/Day10")))
}
