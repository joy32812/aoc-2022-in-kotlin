fun main() {
  fun getAnsMap(opMap: Map<String, String>, humnUnknow: Boolean = false): Map<String, Long?> {
    val ansMap = mutableMapOf<String, Long?>()
    fun dfs(key: String): Long? {
      if (key in ansMap) return ansMap[key]

      val op = opMap[key]!!
      val result = if (op[0].isDigit()) {
        op.toLong()
      } else {
        val splits = op.split(" ")
        val a = dfs(splits[0])
        val b = dfs(splits[2])

        if (a == null || b == null) null
        else {
          when (splits[1]) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> throw java.lang.RuntimeException()
          }
        }
      }

      ansMap[key] = result
      return result
    }

    if (humnUnknow) ansMap["humn"] = null
    dfs("root")

    return ansMap
  }

  fun part1(input: List<String>): Long {
    val opMap = input.associate { it.split(": ")[0] to it.split(": ")[1] }
    val ansMap = getAnsMap(opMap)

    return ansMap["root"]!!
  }

  // Find the value of "humn" from top to bottom.
  fun part2(input: List<String>): Long {

    val opMap = input.associate { it.split(": ")[0] to it.split(": ")[1] }
    val ansMap = getAnsMap(opMap, humnUnknow = true)

    val ka = opMap["root"]!!.split(" ")[0]
    val kb = opMap["root"]!!.split(" ")[2]

    val va = ansMap[ka]
    val vb = ansMap[kb]

    var (key, currentValue) = if (va == null) ka to vb!! else kb to va

    while (key != "humn") {
      val op = opMap[key]!!
      val splits = op.split(" ")

      val kx = splits[0]
      val ky = splits[2]

      val vx = ansMap[kx]
      val vy = ansMap[ky]

      if (vx == null) {
        key = kx

        currentValue = when (splits[1]) {
          "+" -> currentValue - vy!!
          "-" -> currentValue + vy!!
          "*" -> currentValue / vy!!
          "/" -> currentValue * vy!!
          else -> 0
        }
      } else {
        key = ky
        currentValue = when (splits[1]) {
          "+" -> currentValue - vx
          "-" -> vx - currentValue
          "*" -> currentValue / vx
          "/" -> vx * currentValue
          else -> 0
        }
      }
    }

    return currentValue
  }


  println(part1(readInput("data/Day21_test")))
  println(part1(readInput("data/Day21")))

  println(part2(readInput("data/Day21_test")))
  println(part2(readInput("data/Day21")))

}