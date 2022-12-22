
data class Status(val x: Int, val y: Int, val dir: Int)

fun main() {

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

  fun part1(input: List<String>): Long {
    val grid = input.subList(0, input.size - 2)
    val commands = parseCommand(input.last())

    val m = grid.size
    val n = grid.maxOf { it.length }

    val nextMap = mutableMapOf<Int, Pair<Int, Int>>()
    fun getNext(st: Status): Pair<Int, Int> {
      val key = st.hashCode()
      if (key in nextMap) return nextMap[key]!!

      var (x, y, dir) = st


      while (true) {
        x = (x + dx[dir] + m) % m
        y = (y + dy[dir] + n) % n

        if (y >= grid[x].length) continue
        if (grid[x][y] == '.' || grid[x][y] == '#') {
          nextMap[key] = x to y
          return x to y
        }
      }
    }

    fun runCommand(st: Status, cmd: String): Status {
      if (cmd == "R") return st.copy(dir = (st.dir + 1) % 4)

      if (cmd == "L") return st.copy(dir = (st.dir + 3) % 4)

      var curSt = st
      repeat(cmd.toInt()) {
        val (nx, ny) = getNext(curSt)
        if (grid[nx][ny] == '#') return curSt
        curSt = curSt.copy(x = nx, y = ny)
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

  // println(part1(readInput("data/Day22_test")))
  println(part1(readInput("data/Day22")))

}