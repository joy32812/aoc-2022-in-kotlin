
data class Status(val x: Int, val y: Int, val dir: Int)

fun main() {
  val RIGHT = 0
  val DOWN = 1
  val LEFT = 2
  val UP = 3

  // right, down, left, up
  val dx = listOf(0, 1, 0, -1)
  val dy = listOf(1, 0, -1, 0)

  fun parseCommand(s: String): List<String> {
    val commands = mutableListOf<String>()

    var i = 0
    var step = 0
    while (i < s.length) {
      if (s[i].isDigit()) {
        step = step * 10 + (s[i] - '0')
      } else {
        commands += "$step"
        commands += "${s[i]}"
        step = 0
      }
      i ++
    }

    if (step != 0) commands += "$step"
    return commands
  }

  fun Status.getNext1(grid: List<String>): Status {
    var (x, y, dir) = this
    val m = grid.size
    val n = grid.maxOf { it.length }


    while (true) {
      x = (x + dx[dir] + m) % m
      y = (y + dy[dir] + n) % n

      if (y >= grid[x].length || grid[x][y] == ' ') continue
      return this.copy(x = x, y = y)
    }
  }


  fun Status.getNext2(grid: List<String>): Status {
    val (x, y, dir) = this
    val tx = x + dx[dir]
    val ty = y + dy[dir]

    // Still in the grid.
    if (tx in grid.indices && ty in grid[tx].indices && grid[tx][ty] != ' ') return this.copy(x = tx, y = ty)

    val L = grid.size / 3


    val baseMap = mutableMapOf(
      1 to (0 to 2 * L),
      2 to (L to 0),
      3 to (L to L),
      4 to (L to 2 * L),
      5 to (2 * L to 2 * L),
      6 to (2 * L to 3 * L)
    )


    fun transferStatus(label: Int, dir: Int, x: Int, y: Int): Status {
      val (baseX, baseY) = baseMap[label]!!

      return Status(
        x = baseX + x,
        y = baseY + y,
        dir = dir
      )
    }

    fun toBeInL(x: Int, y: Int): Pair<Int, Int> {
      var zx = x
      var zy = y
      while (zx >= L) zx -= L
      while (zy >= L) zy -= L
      return zx to zy
    }

    val (zx, zy) = toBeInL(x, y)

    // 1 right
    if (x in 0 until L && y == 3 * L - 1 && dir == 0) {
      return transferStatus(6, LEFT, L - zx, L - 1)
    }

    // 1 left
    if (x in 0 until L && y == 2 * L && dir == 2) {
      return transferStatus(3, DOWN, 0, zx)
    }

    // 1 up
    if (x == 0 && y in 2 * L until 3 * L && dir == 3) {
      return transferStatus(2, DOWN, 0, L - 1 - zy)
    }


    // 2 down
    if (x == 2 * L - 1 && y in 0 until L && dir == 1) {
      return transferStatus(5, UP, L - 1, L - 1 - zy)
    }

    // 2 left
    if (x in L until 2 * L && y == 0 && dir == 2) {
      return transferStatus(6, UP, L - 1, L - 1 - zx)
    }

    // 2 up
    if (x == L && y in 0 until L && dir == 3) {
      return transferStatus(1, DOWN, 0, L - 1 - zy)
    }


    // 3 down
    if (x == 2 * L - 1 && y in L until 2 * L && dir == 1) {
      return transferStatus(5, RIGHT, L - 1 - zy, 0)

    }

    // 3 up
    if (x == L && y in L until 2 * L && dir == 3) {
      return transferStatus(1, RIGHT, zy, 0)
    }


    // 4 right
    if (x in L until 2 * L && y == 3 * L - 1 && dir == 0) {
      return transferStatus(6, DOWN, 0, L - 1 - zx)
    }

    // 5 down
    if (x == 3 * L - 1 && y in 2 * L until 3 * L && dir == 1) {
      return transferStatus(2, UP, L - 1, L - 1 - zy)
    }

    // 5 left
    if (x in 2 * L until 3 * L && y == 2 * L && dir == 2) {
      return transferStatus(3, UP, L - 1, L - 1 - zx)
    }


    // 6 right
    if (x in 2 * L until 3 * L && y == 3 * L - 1 && dir == 0) {
      return transferStatus(1, LEFT, L - 1 - zx, L - 1)
    }

    // 6 down
    if (x == 3 * L - 1 && y in 3 * L until 4 * L && dir == 1) {
      return transferStatus(2, RIGHT, L - 1 - zx, 0)
    }

    // 6 up
    if (x == 2 * L && y in 3 * L until 4 * L && dir == 3) {
      return transferStatus(4, LEFT, L - 1 - zy, L - 1)
    }

    return this
  }


  fun Status.getNext3(grid: List<String>): Status {
    val (x, y, dir) = this
    val tx = x + dx[dir]
    val ty = y + dy[dir]

    // Still in the grid.
    if (tx in grid.indices && ty in grid[tx].indices && grid[tx][ty] != ' ') return this.copy(x = tx, y = ty)

    val L = grid.size / 4

    val baseMap = mutableMapOf(
      1 to (0 to L),
      2 to (0 to 2 * L),
      3 to (L to L),
      4 to (2 * L to 0),
      5 to (2 * L to L),
      6 to (3 * L to 0)
    )


    fun transferStatus(label: Int, dir: Int, x: Int, y: Int): Status {
      val (baseX, baseY) = baseMap[label]!!

      return Status(
        x = baseX + x,
        y = baseY + y,
        dir = dir
      )
    }

    fun toBeInL(x: Int, y: Int): Pair<Int, Int> {
      var zx = x
      var zy = y
      while (zx >= L) zx -= L
      while (zy >= L) zy -= L
      return zx to zy
    }

    fun checkIfEdge(label: Int, dir: Int): Boolean {
      val (baseX, baseY) = baseMap[label]!!
      if (dir == LEFT) {
        return y == baseY && x in baseX until baseX + L
      }
      if (dir == RIGHT) {
        return y == baseY + L - 1 && x in baseX until baseX + L
      }
      if (dir == UP) {
        return x == baseX && y in baseY until baseY + L
      }
      if (dir == DOWN) {
        return x == baseX + L - 1 && y in baseY until baseY + L
      }

      return false
    }

    val (zx, zy) = toBeInL(x, y)

    // 1 right
    // 1 down
    // 1 left
    if (checkIfEdge(1, LEFT)) {
      return transferStatus(4, RIGHT, L - 1 - zx, 0)
    }
    // 1 up
    if (checkIfEdge(1, UP)) {
      return transferStatus(6, RIGHT, zy, 0)
    }

    // 2 right
    if (checkIfEdge(2, RIGHT)) {
      return transferStatus(5, LEFT, L - 1 - x, L - 1)
    }
    // 2 down
    if (checkIfEdge(2, DOWN)) {
      return transferStatus(3, LEFT, zy, L - 1)
    }
    // 2 left
    // 2 up
    if (checkIfEdge(2, UP)) {
      return transferStatus(6, UP, L - 1, zy)
    }

    // 3 right
    if (checkIfEdge(3, RIGHT)) {
      return transferStatus(2, UP, L - 1, zx)
    }
    // 3 down
    // 3 left
    if (checkIfEdge(3, LEFT)) {
      return transferStatus(4, DOWN, 0, zx)
    }
    // 3 up

    // 4 right
    // 4 down
    // 4 left
    if (checkIfEdge(4, LEFT)) {
      return transferStatus(1, RIGHT, L - 1 - zx, 0)
    }
    // 4 up
    if (checkIfEdge(4, UP)) {
      return transferStatus(3, RIGHT, zy, 0)
    }

    // 5 right
    if (checkIfEdge(5, RIGHT)) {
      return transferStatus(2, LEFT, L - 1 - zx, L - 1)
    }
    // 5 down
    if (checkIfEdge(5, DOWN)) {
      return transferStatus(6, LEFT, zy, L - 1)
    }
    // 5 left
    // 5 up

    // 6 right
    if (checkIfEdge(6, RIGHT)) {
      return transferStatus(5, UP, L - 1, zx)
    }
    // 6 down
    if (checkIfEdge(6, DOWN)) {
      return transferStatus(2, DOWN, 0, zy)
    }
    // 6 left
    if (checkIfEdge(6, LEFT)) {
      return transferStatus(1, DOWN, 0, zx)
    }
    // 6 up

    return this
  }


  fun solve(grid: List<String>, commands: List<String>, next: Status.(g: List<String>) -> Status): Long {
    fun runCommand(st: Status, cmd: String): Status {
      if (cmd == "R") return st.copy(dir = (st.dir + 1) % 4)
      if (cmd == "L") return st.copy(dir = (st.dir + 3) % 4)

      var curSt = st.copy()
      repeat(cmd.toInt()) {
        val ns = curSt.next(grid)
        if (grid[ns.x][ns.y] == '#') {
          return curSt
        }
        curSt = ns
      }

      return curSt
    }

    val x = 0
    val y = grid[0].indexOfFirst { it == '.' }

    var status = Status(x, y, 0)
    for (command in commands) {
      status = runCommand(status, command)
    }

    return 1000L * (status.x + 1) + 4L * (status.y + 1) + status.dir
  }

  fun part1(input: List<String>): Long {
    val grid = input.subList(0, input.size - 2)
    val commands = parseCommand(input.last())
    return solve(grid, commands) { getNext1(grid) }
  }


  fun part2(input: List<String>): Long {
    val grid = input.subList(0, input.size - 2)
    val commands = parseCommand(input.last())
    return solve(grid, commands) { getNext2(grid) }
  }

  fun part3(input: List<String>): Long {
    val grid = input.subList(0, input.size - 2)
    val commands = parseCommand(input.last())
    return solve(grid, commands) { getNext3(grid) }
  }

//  println(part1(readInput("data/Day22_test")))
//  println(part1(readInput("data/Day22")))

  println(part2(readInput("data/Day22_test")))
  println(part3(readInput("data/Day22")))
}
