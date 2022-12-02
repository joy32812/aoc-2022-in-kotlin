const val SCORE_WIN = 6
const val SCORE_DRAW = 3
val selectScore = listOf(1, 2, 3)

fun Char.toIntNum() = when (this) {
  in "AX" -> 0
  in "BY" -> 1
  else -> 2
}

fun getScore(a: Int, b: Int): Int {
  val matchScore = when {
    a == b -> SCORE_DRAW
    a + 1 == b || (a == 2 && b == 0) -> SCORE_WIN
    else -> 0
  }
  return matchScore + selectScore[b]
}

fun part1(input: List<String>): Int {
  return input.sumOf { getScore(it[0].toIntNum(), it[2].toIntNum()) }
}

fun part2(input: List<String>): Int {

  return input.sumOf {
    val a = it[0].toIntNum()
    val b = when (it[2]) {
      'X' -> (3 + a - 1) % 3
      'Y' -> a
      else -> (a + 1) % 3
    }

    getScore(a, b)
  }

}

fun main() {
  println(part1(readInput("Day02_test")))
  println(part1(readInput("Day02_part1")))

  println(part2(readInput("Day02_test")))
  println(part2(readInput("Day02_part1")))
}
