fun main() {

  fun solve(s: String, num: Int): Int {
    return s.windowed(num).indexOfFirst { it.toCharArray().toSet().size == num } + num
  }

  fun part1(input: List<String>): List<Int> {
    return input.map { solve(it, 4) }
  }

  fun part2(input: List<String>): List<Int> {
    return input.map { solve(it, 14) }
  }


  println(part1(readInput("data/Day06_test")))
  println(part1(readInput("data/Day06")))

  println(part2(readInput("data/Day06")))
}