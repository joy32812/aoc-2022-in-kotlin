import java.util.LinkedList

fun main() {

  fun toKey(x: Int, y: Int) = x * 10000 + y
  fun toCord(key: Int) = key / 10000 to key % 10000

  fun shortestPath(grid: Array<CharArray>, edgeMap: Map<Int, MutableSet<Int>>, sx: Int, sy: Int, tx: Int, ty: Int): Int {
    val m = grid.size
    val n = grid[0].size

    val ans = Array(m) { Array(n) { Int.MAX_VALUE } }
    ans[sx][sy] = 0

    val sKey = toKey(sx, sy)
    val Q = LinkedList<Int>()
    val inQ = mutableSetOf<Int>()
    Q += sKey
    inQ += sKey

    while (Q.isNotEmpty()) {
      val top = Q.poll()
      inQ.remove(top)

      val (ax, ay) = toCord(top)

      val edges = edgeMap[top] ?: emptySet()

      for (b in edges) {
        val (bx, by) = toCord(b)

        if (ans[bx][by] > ans[ax][ay] + 1) {
          ans[bx][by] = ans[ax][ay] + 1

          if (b !in inQ) {
            inQ += b
            Q += b
          }
        }
      }
    }

    return ans[tx][ty]
  }

  fun getSourceAndDest(grid: Array<CharArray>): List<Int> {
    var (sx, sy, tx, ty) = listOf(0, 0, 0, 0)

    for (i in grid.indices) {
      for (j in grid[0].indices) {
        if (grid[i][j] == 'S') {
          grid[i][j] = 'a'
          sx = i
          sy = j
        }

        if (grid[i][j] == 'E') {
          grid[i][j] = 'z'
          tx = i
          ty = j
        }
      }
    }

    return listOf(sx, sy, tx, ty)
  }

  fun getEdgeMap(grid: Array<CharArray>): Map<Int, MutableSet<Int>> {
    val dx = listOf(0, 0, 1, -1)
    val dy = listOf(1, -1, 0, 0)

    val edgeMap = mutableMapOf<Int, MutableSet<Int>>()

    for (i in grid.indices) {
      for (j in grid[0].indices) {
        for (k in dx.indices) {

          val zx = i + dx[k]
          val zy = j + dy[k]

          if (zx !in grid.indices || zy !in grid[0].indices) continue
          if (grid[zx][zy] - grid[i][j] > 1) continue

          val aKey = toKey(i, j)
          val bKey = toKey(zx, zy)

          edgeMap.getOrPut(aKey) { mutableSetOf() } += bKey
        }
      }
    }

    return edgeMap
  }

  fun part1(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }.toTypedArray()
    val (sx, sy, tx, ty) = getSourceAndDest(grid)
    val edgeMap = getEdgeMap(grid)

    return shortestPath(grid, edgeMap, sx, sy, tx, ty)
  }

  fun part2(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }.toTypedArray()
    val (_, _, tx, ty) = getSourceAndDest(grid)
    val edgeMap = getEdgeMap(grid)

    var ans = Int.MAX_VALUE
    for (i in grid.indices) {
      for (j in grid[0].indices) {

        if (grid[i][j] == 'a') {
          ans = minOf(ans, shortestPath(grid, edgeMap, i, j, tx, ty))
        }

      }
    }

    return ans
  }


  println(part1(readInput("data/Day12_test")))
  println(part1(readInput("data/Day12")))

  println(part2(readInput("data/Day12_test")))
  println(part2(readInput("data/Day12")))
}
