
data class Valve(val id: String, val rate: Int, val neighbors: List<String>)
data class VNode(val rate: Int, val friends: List<Int>)

fun main() {

  fun String.toValve(): Valve {
    val id = split(" ")[1]
    val rate = split(";")[0].split("=")[1].toInt()

    val neighbors = if (" valves " in this) split(" valves ")[1].split(", ")
    else split(" valve ")[1].split(", ")

    return Valve(id, rate, neighbors)
  }

  fun List<Valve>.toVNode(): List<VNode> {
    val idMap = this.mapIndexed { index, valve -> valve.id to index }.toMap()
    return this.map { VNode(it.rate, it.neighbors.map { n -> idMap[n]!! }) }
  }

  fun getDis(nodes: List<VNode>): Array<Array<Int>> {
    val n = nodes.size
    val MAX = Int.MAX_VALUE / 2
    val dist = Array(n) { Array(n) { MAX } }

    for (i in nodes.indices) {
      for (j in nodes[i].friends) {
        dist[i][j] = 1
        dist[j][i] = 1
      }
    }

    for (k in 0 until n) {
      for (i in 0 until n) {
        for (j in 0 until n) {
          if (dist[i][j] > dist[i][k] + dist[k][j])
            dist[i][j] = dist[i][k] + dist[k][j]
        }
      }
    }

    return dist
  }
  
  fun getAns(nodes: List<VNode>): Int {
    val dist = getDis(nodes)
    var ans = -1

    fun dfs(x: Int, remains: MutableSet<Int>, now: Int, score: Int) {
      if (now >= 30 || remains.isEmpty()) {
        ans = maxOf(ans, score)
        return
      }

      val allLeft = remains.toList()
      for (y in allLeft) {
        val then = now + dist[x][y] + 1
        val yPressure = if (then <= 30) nodes[y].rate * (30 - then) else 0

        remains -= y
        dfs(y, remains, then, score + yPressure)
        remains += y
      }
    }

    val remains = nodes
      .mapIndexed { index, vNode -> index to vNode }
      .filter { it.second.rate > 0 }
      .map { it.first }
      .toMutableSet()

    dfs(0, remains, 0, 0)

    return ans
  }

  fun part1(input: List<String>): Int {
    val valves = input.map { it.toValve() }.sortedBy { it.id }
    val nodes = valves.toVNode()

    return getAns(nodes)
  }

  fun getAns2(nodes: List<VNode>): Int {
    val dist = getDis(nodes)
    var ans = -1

    fun dfs(x: Int, remains: MutableSet<Int>, now: Int, score: Int) {
      if (now >= 30 || remains.isEmpty()) {
        ans = maxOf(ans, score)
        return
      }

      val allLeft = remains.toList()
      for (y in allLeft) {
        val then = now + dist[x][y] + 1
        val yPressure = if (then <= 30) nodes[y].rate * (30 - then) else 0

        remains -= y
        dfs(y, remains, then, score + yPressure)
        remains += y
      }
    }

    val remains = nodes
      .mapIndexed { index, vNode -> index to vNode }
      .filter { it.second.rate > 0 }
      .map { it.first }
      .toMutableSet()

    var result = -1
    val remainList = remains.toList()
    val size = remains.size
    for (i in 0 until (1 shl size)) {
      val left = mutableSetOf<Int>()
      for (j in 0 until size) {
        if ((i and (1 shl j)) > 0) {
          left += remainList[j]
        }
      }

      val right = remains - left
      var tmp = 0

      ans = -1
      dfs(0, left, 4, 0)
      tmp += ans

      ans = -1
      dfs(0, right.toMutableSet(), 4, 0)
      tmp += ans

      result = maxOf(result, tmp)
    }

    return result
  }

  fun part2(input: List<String>): Int {
    val valves = input.map { it.toValve() }.sortedBy { it.id }
    val nodes = valves.toVNode()

    return getAns2(nodes)
  }


  println(part1(readInput("data/Day16_test")))
  println(part1(readInput("data/Day16")))

  println(part2(readInput("data/Day16_test")))
  println(part2(readInput("data/Day16")))

}