import java.util.*
import kotlin.math.abs

data class Cube(val x: Int, val y: Int, val z: Int)
fun main() {

    fun String.toCube(): Cube {
        val (x, y, z) = this.split(",").map { it.toInt() }
        return Cube(x + 1, y + 1, z + 1)
    }

    fun nextTo(a: Cube, b: Cube): Boolean {
        return abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z) == 1
    }

    fun part1(input: List<String>): Int {
        val cubes = input.map { it.toCube() }
        var total = cubes.size * 6

        for (i in cubes.indices) {
            for (j in i + 1 until cubes.size) {
                if (nextTo(cubes[i], cubes[j])) total -= 2
            }
        }
        return total
    }

    fun toKey(cube: Cube): Int {
        return cube.x * 1000000 + cube.y * 1000 + cube.z
    }

    fun toXYZ(key: Int): List<Int> {
        val x = key / 1000000
        val y = (key % 1000000) / 1000
        val z = key % 1000
        return listOf(x, y, z)
    }

    /**
     * BFS
     */
    fun part2(input: List<String>): Int {
        val n = 50
        val grid = Array(n) { Array(n) { Array(n) { false } } }
        val visited = Array(n) { Array(n) { Array(n) { false } } }

        val cubes = input.map { it.toCube() }
        for (cube in cubes) grid[cube.x][cube.y][cube.z] = true

        visited[0][0][0] = true
        val Q = LinkedList<Int>()
        val key = toKey(Cube(0, 0, 0))
        Q.add(key)

        while (Q.isNotEmpty()) {
            val key = Q.poll()
            val (x, y, z) = toXYZ(key)

            for (dx in -1..1) {
                for (dy in -1..1) {
                    for (dz in -1..1) {
                        val nx = x + dx
                        val ny = y + dy
                        val nz = z + dz

                        if (nx < 0 || nx >= n || ny < 0 || ny >= n || nz < 0 || nz >= n) continue
                        if (!nextTo(Cube(x, y, z), Cube(nx, ny, nz))) continue
                        if (visited[nx][ny][nz]) continue
                        if (grid[nx][ny][nz]) continue

                        visited[nx][ny][nz] = true
                        val nkey = toKey(Cube(nx, ny, nz))
                        Q.add(nkey)
                    }
                }
            }
        }

        var ans = 0
        for (x in 0 until n) {
            for (y in 0 until n) {
                for (z in 0 until n) {
                    if (!visited[x][y][z]) continue

                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            for (dz in -1..1) {
                                val nx = x + dx
                                val ny = y + dy
                                val nz = z + dz

                                if (nx < 0 || nx >= n || ny < 0 || ny >= n || nz < 0 || nz >= n) continue
                                if (!nextTo(Cube(x, y, z), Cube(nx, ny, nz))) continue

                                if (grid[nx][ny][nz]) ans++
                            }
                        }
                    }

                }
            }
        }

        return ans
    }

    println(part1(readInput("data/day18_test")))
    println(part1(readInput("data/day18")))

    println(part2(readInput("data/day18_test")))
    println(part2(readInput("data/day18")))


}
