fun main() {

    fun List<String>.toCalories(): List<Int> {
        return joinToString(",")
            .split(",,")
            .map { it.split(",").sumOf { it.toInt() } }
    }

    fun part1(input: List<String>): Int {
        return input.toCalories().max()
    }

    fun part2(input: List<String>): Int {
        return input.toCalories().sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("data/Day01_test")
    check(part1(testInput) == 24000)
    println(part2(testInput))

    val input = readInput("data/Day01")
    println(part1(input))
    println(part2(input))
}
