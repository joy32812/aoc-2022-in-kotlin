import java.util.Stack

data class Node(val num: Int?, val list: List<Node>?)

fun main() {
  fun String.toNode(): Node {
    fun getPairMap(): Map<Int, Int> {
      val pairMap = mutableMapOf<Int, Int>()
      val stack = Stack<Int>()

      for (i in indices) {
        if (this[i] == '[') {
          stack.push(i)
        } else if (this[i] == ']') {
          val top = stack.pop()
          pairMap[top] = i
        }
      }
      return pairMap
    }

    val pairMap = getPairMap()
    fun dfs(l: Int, r: Int): Node {
      if (this[l].isDigit()) return Node(substring(l..r).toInt(), null)

      val splits = mutableListOf(l, r)
      var i = l + 1
      while (i < r) {
        if (this[i] == '[') i = pairMap[i]!!
        if (this[i] == ',') splits += i
        i++
      }

      splits.sort()

      val list = mutableListOf<Node>()
      for (j in 1 until splits.size) {
        val left = splits[j - 1] + 1
        val right = splits[j] - 1
        if (left <= right) list += dfs(left, right)
      }

      return Node(null, list)
    }

    return dfs(0, length - 1)
  }

  fun compareTo(a: Int, b: Int) = when {
    a < b -> -1
    a > b -> 1
    else -> 0
  }

  fun compareTo(x: Node, y: Node): Int {
    if (x.num != null && y.num != null) return compareTo(x.num, y.num)

    if (x.list != null && y.list != null) {
      val size = minOf(x.list.size, y.list.size)
      for (i in 0 until size) {
        val diff = compareTo(x.list[i], y.list[i])
        if (diff != 0) return diff
      }

      return compareTo(x.list.size, y.list.size)
    }

    val a = if (x.list != null) x else Node(null, listOf(Node(x.num, null)))
    val b = if (y.list != null) y else Node(null, listOf(Node(y.num, null)))

    return compareTo(a, b)
  }

  fun compareTo(s: String, t: String) = compareTo(s.toNode(), t.toNode())

  fun part1(input: List<String>): Int {
    return input.chunked(3).withIndex().sumOf {
      val index = it.index
      val list = it.value

      if (compareTo(list[0], list[1]) == -1) index + 1 else 0
    }
  }

  fun part2(input: List<String>): Int {
    val d1 = "[[2]]"
    val d2 = "[[6]]"

    val result = input.toMutableList().apply {
      add(d1)
      add(d2)
    }.filter { it.isNotEmpty() }
      .sortedWith { o1, o2 -> compareTo(o1, o2) }

    val i1 = result.indexOf(d1)
    val i2 = result.indexOf(d2)

    return (i1 + 1) * (i2 + 1)
  }

  println(part1(readInput("data/Day13_test")))
  println(part1(readInput("data/Day13")))

  println(part2(readInput("data/Day13_test")))
  println(part2(readInput("data/Day13")))
}