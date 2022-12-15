import java.math.BigInteger
import java.util.LinkedList
import kotlin.math.abs

data class Sensor(val x: Int, val y: Int, val bx: Int, val by: Int, val dis: Int)

fun main() {

  val dx = listOf(-1, -1, -1, 0, 0, 1, 1, 1)
  val dy = listOf(-1, 0, 1, -1, 1, -1, 0, 1)
  fun toKey(x: Int, y: Int) = "${x}_${y}"
  fun toPair(key: String) = key.split("_").let { Pair(it[0].toInt(), it[1].toInt()) }
  fun getDis(ax: Int, ay: Int, bx: Int, by: Int) = abs(ax - bx) + abs(ay - by)

  fun String.toSensor(): Sensor {
    val x = split(":")[0].split(",")[0].split("=")[1].toInt()
    val y = split(":")[0].split(",")[1].split("=")[1].toInt()

    val bx = split(":")[1].split(",")[0].split("=")[1].toInt()
    val by = split(":")[1].split(",")[1].split("=")[1].toInt()

    return Sensor(x, y, bx, by, getDis(x, y, bx, by))
  }


  fun part1(input: List<String>, y: Int): Int {
    val sensors = input.map { it.toSensor() }
    val beaconSet = sensors.map { toKey(it.bx, it.by) }.toSet()

    return (-5000000 .. 5000000).count { x ->
      (toKey(x, y) !in beaconSet) && sensors.any { sen -> getDis(x, y, sen.x, sen.y) <= sen.dis }
    }
  }

  fun part2(input: List<String>, limit: Int): String {
    val sensors = input.map { it.toSensor() }
    val beaconSet = sensors.map { toKey(it.bx, it.by) }.toSet()

    fun okay(zx: Int, zy: Int) = sensors.all { sen -> getDis(zx, zy, sen.x, sen.y) > sen.dis }

    var ansX = -1
    var ansY = -1

    for (sensor in sensors) {
      val visited = mutableSetOf<String>()
      val Q = LinkedList<String>()
      val key = toKey(sensor.bx, sensor.by)
      Q += key
      visited += key

      while (Q.isNotEmpty()) {
        val top = Q.poll()
        val (x, y) = toPair(top)

        for (k in dx.indices) {
          val zx = x + dx[k]
          val zy = y + dy[k]
          val zKey = toKey(zx, zy)

          if (zx < 0 || zx > limit) continue
          if (zy < 0 || zy > limit) continue
          if (zKey in visited) continue
          if (zKey in beaconSet) continue

          if (getDis(sensor.x, sensor.y, zx, zy) == sensor.dis + 1) {
            if (okay(zx, zy)) {
              ansX = zx
              ansY = zy
              break
            }
            Q += zKey
            visited += zKey
          }
        }

        if (ansX != -1) break
      }

      if (ansX != -1) break
    }

    val result = ansX.toBigInteger() * BigInteger.valueOf(4000000) + ansY.toBigInteger()
    return result.toString()
  }

  println(part1(readInput("data/Day15_test"), 10))
  println(part1(readInput("data/Day15"), 2000000))

  println(part2(readInput("data/Day15_test"), 20))
  println(part2(readInput("data/Day15"), 4000000))
}