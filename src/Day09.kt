import kotlin.math.abs


fun main() {

  fun solve(input: List<String>, knotCnt: Int): Int {
    val dirMap = mapOf(
      "L" to (0 to -1),
      "R" to (0 to 1),
      "U" to (-1 to 0),
      "D" to (1 to 0)
    )

    val tailPosSet = mutableSetOf("0_0")
    val positions = Array(knotCnt) { Array(2) { 0 } }

    fun isNeighbor(i: Int, j: Int): Boolean {
      val (ix, iy) = positions[i]
      val (jx, jy) = positions[j]

      return abs(ix - jx) <= 1 && abs(iy - jy) <= 1
    }

    fun move(dir: String, step: Int) {
      val (dx, dy) = dirMap[dir]!!

      repeat(step) {

        positions[0][0] += dx
        positions[0][1] += dy
        for (i in 1 until positions.size) {
          if (isNeighbor(i - 1, i)) break

          if (positions[i][0] != positions[i - 1][0]) positions[i][0] += (positions[i - 1][0] - positions[i][0]) / abs(positions[i - 1][0] - positions[i][0])
          if (positions[i][1] != positions[i - 1][1]) positions[i][1] += (positions[i - 1][1] - positions[i][1]) / abs(positions[i - 1][1] - positions[i][1])

          tailPosSet += "${positions[knotCnt - 1][0]}_${positions[knotCnt - 1][1]}"
        }
      }
    }

    input.forEach { line ->
      val splits = line.split(" ")
      move(splits[0], splits[1].toInt())
    }

    return tailPosSet.size
  }

  fun part1(input: List<String>): Int {
    return solve(input, 2)
  }

  fun part2(input: List<String>): Int {
    return solve(input, 10)
  }

  println(part1(readInput("data/Day09_test")))
  println(part1(readInput("data/Day09")))

  println(part2(readInput("data/Day09_test")))
  println(part2(readInput("data/Day09")))
}
