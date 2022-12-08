fun main() {

  fun left2right(grid: List<String>, dp: Array<Array<Boolean>>) {
    for (i in grid.indices) {
      var max = -1
      for (j in grid[0].indices) {
        if (grid[i][j] - '0' > max) dp[i][j] = true
        max = maxOf(grid[i][j] - '0', max)
      }
    }
  }

  fun right2left(grid: List<String>, dp: Array<Array<Boolean>>) {
    for (i in grid.indices) {
      var max = -1
      for (j in grid[0].indices.reversed()) {
        if (grid[i][j] - '0' > max) dp[i][j] = true
        max = maxOf(grid[i][j] - '0', max)
      }
    }
  }

  fun top2down(grid: List<String>, dp: Array<Array<Boolean>>) {
    for (j in grid[0].indices) {
      var max = -1
      for (i in grid.indices) {
        if (grid[i][j] - '0' > max) dp[i][j] = true
        max = maxOf(grid[i][j] - '0', max)
      }
    }
  }

  fun down2top(grid: List<String>, dp: Array<Array<Boolean>>) {
    for (j in grid[0].indices) {
      var max = -1
      for (i in grid.indices.reversed()) {
        if (grid[i][j] - '0' > max) dp[i][j] = true
        max = maxOf(grid[i][j] - '0', max)
      }
    }
  }

  fun part1(input: List<String>): Int {
    val m = input.size
    val n = input[0].length
    val dp = Array(m) { Array(n) { false } }

    left2right(input, dp)
    right2left(input, dp)
    top2down(input, dp)
    down2top(input, dp)

    return dp.flatMap { it.toList() }.count { it }
  }


  fun part2(input: List<String>): Int {
    val m = input.size
    val n = input[0].length

    fun work(x: Int, y: Int): Int {

      var a = 0
      for (j in y - 1 downTo 0) {
        a ++
        if (input[x][j] >= input[x][y]) break
      }

      var b = 0
      for (j in y + 1 until n) {
        b ++
        if (input[x][j] >= input[x][y]) break
      }

      var c = 0
      for (i in x - 1 downTo 0) {
        c ++
        if (input[i][y] >= input[x][y]) break
      }

      var d = 0
      for (i in x + 1 until  m) {
        d ++
        if (input[i][y] >= input[x][y]) break
      }
      return a * b * c * d
    }


    var ans = -1
    for (i in input.indices) {
      for (j in input[0].indices) {
        ans = maxOf(ans, work(i, j))
      }
    }

    return ans
  }

  println(part1(readInput("data/Day08_test")))
  println(part1(readInput("data/Day08")))

  println(part2(readInput("data/Day08_test")))
  println(part2(readInput("data/Day08")))
}
