fun main() {

    fun String.toRange() = this.split("-").map { it.toInt() }.let { it[0]..it[1] }

    fun part1(input: List<String>): Int {

        return input.count { line ->
            val r1 = line.split(",").first().toRange()
            val r2 = line.split(",").last().toRange()

            r1.all { it in r2 } || r2.all { it in r1 }
        }
    }

    fun part2(input: List<String>): Int {

        return input.count { line ->
            val r1 = line.split(",").first().toRange()
            val r2 = line.split(",").last().toRange()

            r1.any { it in r2 }
        }
    }





    println(part1(readInput("data/Day04_test")))
    println(part1(readInput("data/Day04")))

    println(part2(readInput("data/Day04_test")))
    println(part2(readInput("data/Day04")))

}
