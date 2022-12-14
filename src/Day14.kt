fun main() {

  fun getGrid(input: List<String>, hasFloor: Boolean = false): Array<Array<Char>> {
    val grid = Array(1000) { Array(1000) { '.' } }
    var maxX = Int.MIN_VALUE

    fun addPath(s: String, t: String) {
      val (sy, sx) = s.split(",").map { it.toInt() }
      val (ty, tx) = t.split(",").map { it.toInt() }

      val xRange = minOf(sx, tx) .. maxOf(sx, tx)
      val yRange = minOf(sy, ty) .. maxOf(sy, ty)

      maxX = maxOf(maxX, sx, tx)

      for (i in xRange) {
        for (j in yRange) {
          grid[i][j] = '#'
        }
      }
    }

    input.forEach { s -> s.split(" -> ").zipWithNext().forEach { addPath(it.first, it.second) } }

    if (hasFloor) {
      for (j in grid[0].indices) grid[maxX + 2][j] = '#'
    }

    return grid
  }

  fun dropSand(grid: Array<Array<Char>>): Boolean {
    var x = 0
    var y = 500

    if (grid[x][y] == '#') return false

    while (x < 999) {
      if (grid[x + 1][y] == '.') {
        x ++
      } else if (grid[x + 1][y - 1] == '.') {
        x ++
        y --
      } else if (grid[x + 1][y + 1] == '.') {
        x ++
        y ++
      } else {
        break
      }
    }

    if (x >= 999) return false
    grid[x][y] = '#'
    return true
  }

  fun part1(input: List<String>): Int {
    val grid = getGrid(input)

    var i = 0
    while (dropSand(grid)) i ++
    return i
  }

  fun part2(input: List<String>): Int {
    val grid = getGrid(input, hasFloor = true)

    var i = 0
    while (dropSand(grid)) i ++
    return i
  }

  println(part1(readInput("data/Day14_test")))
  println(part1(readInput("data/Day14")))

  println(part2(readInput("data/Day14_test")))
  println(part2(readInput("data/Day14")))
}