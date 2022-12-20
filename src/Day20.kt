import kotlin.math.abs

fun main() {

  fun solve(input: List<String>, repeatTimes: Int, multiplier: Int): Long {
    val raw = input.map { it.toLong() *  multiplier}
    val arr = raw.withIndex().map { "${it.value}_${it.index}" }.toTypedArray()

    val posMap = arr.withIndex().associate { it.value to it.index }.toMutableMap()

    fun swap(i: Int, j: Int) {
      val tmp = arr[i]
      arr[i] = arr[j]
      arr[j] = tmp

      posMap[arr[i]] = i
      posMap[arr[j]] = j
    }

    repeat(repeatTimes) {
      for (k in raw.indices) {
        val key = "${raw[k]}_${k}"

        val steps = (raw[k] % (raw.size - 1)).toInt()
        repeat(abs(steps)) {
          val i = posMap[key]!!
          val diff = if (steps > 0) 1 else -1
          val j = (i + diff + raw.size) % raw.size

          swap(i, j)
        }
      }
    }

    fun getByPos(x: Int): Long {
      val index = raw.indexOf(0)
      val key = "0_${index}"
      var i = (posMap[key]!! + x) % raw.size

      return arr[i].split("_")[0].toLong()
    }

    return getByPos(1000) + getByPos(2000) + getByPos(3000)
  }

  fun part1(input: List<String>): Long {
    return solve(input, 1, 1)
  }

  fun part2(input: List<String>): Long {
    return solve(input, 10, 811589153)
  }


  println(part1(readInput("data/Day20_test")))
  println(part1(readInput("data/Day20")))

  println(part2(readInput("data/Day20_test")))
  println(part2(readInput("data/Day20")))
}