
fun main() {
    fun Char.toScore() = when (this) {
        in 'a'..'z' -> 1 + (this - 'a')
        else -> 27 + (this - 'A')
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { (a, b, c) ->
            a.toSet().intersect(b.toSet()).intersect(c.toSet()).first().toScore()
        }
    }


    fun part1(input: List<String>): Int {
        return input.sumOf {
            val firstHalf = it.substring(0, it.length / 2)
            val secondHalf = it.substring(it.length / 2)
            firstHalf.toSet().intersect(secondHalf.toSet()).first().toScore()
        }
    }


    println(part1(readInput("data/Day03_test")))
    println(part1(readInput("data/Day03")))

    println(part2(readInput("data/Day03_test")))
    println(part2(readInput("data/Day03")))
}
