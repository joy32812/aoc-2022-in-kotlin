import java.util.LinkedList

private data class Position(val x: Int, val y: Int, val time: Int)

private fun Position.toId() = "$x,$y,$time"
private fun String.toPosition() = split(",").map { it.toInt() }.let { Position(it[0], it[1], it[2]) }

fun main() {

    // up, down, left, right
    val dx = listOf(-1, 1, 0, 0)
    val dy = listOf(0, 0, -1, 1)

    fun Char.getDeltaId() = when (this) {
        '^' -> 0
        'v' -> 1
        '<' -> 2
        '>' -> 3
        else -> throw IllegalArgumentException("Invalid direction: $this")
    }

    fun initBlockSet(grid: List<String>): Set<String> {
        val m = grid.size
        val n = grid[0].length
        val blockSet = mutableSetOf<String>()

        fun generateBlockSet(x: Int, y: Int, dId: Int) {
            var nx = x
            var ny = y

            for (i in 0 until 1000) {
                blockSet += "$nx,$ny,$i"

                nx = (nx + dx[dId] + m) % m
                ny = (ny + dy[dId] + n) % n
                while (grid[nx][ny] == '#') {
                    nx = (nx + dx[dId] + m) % m
                    ny = (ny + dy[dId] + n) % n
                }
            }
        }

        for (i in 0 until m) {
            for (j in 0 until n) {
                if (grid[i][j] in "^v<>") {
                    generateBlockSet(i, j, grid[i][j].getDeltaId())
                }
            }
        }

        return blockSet
    }

    fun travel(from: Position, to: Position, blockSet: Set<String>, grid: List<String>): Int {
        var Q = mutableSetOf(from.toId())

        while (Q.isNotEmpty()) {

            val newQ = mutableSetOf<String>()

            for (p in Q) {
                val (x, y, time) = p.toPosition()
                if ("$x,$y,${time + 1}" !in blockSet) {
                    newQ += "$x,$y,${time + 1}"
                }

                for (k in dx.indices) {
                    val nx = x + dx[k]
                    val ny = y + dy[k]
                    val nTime = time + 1

                    if (nx !in grid.indices || ny !in grid[0].indices) continue
                    if (grid[nx][ny] == '#') continue

                    if (nx == to.x && ny == to.y) {
                        return nTime
                    }

                    val nP = Position(nx, ny, nTime)
                    if (nP.toId() in blockSet) continue

                    newQ += nP.toId()
                }
            }

            Q = newQ
        }

        return -1
    }


    fun part1(grid: List<String>): Int {
        val blockSet = initBlockSet(grid)

        return travel(Position(0, 1, 0), Position(grid.size - 1, grid[0].length - 2, 0), blockSet, grid)
    }

    fun part2(grid: List<String>): Int {
        val blockSet = initBlockSet(grid)

        val t1 = travel(Position(0, 1, 0), Position(grid.size - 1, grid[0].length - 2, 0), blockSet, grid)
        val t2 = travel(Position(grid.size - 1, grid[0].length - 2, t1), Position(0, 1, 0), blockSet, grid)
        val t3 = travel(Position(0, 1, t2), Position(grid.size - 1, grid[0].length - 2, 0), blockSet, grid)

        return t3
    }





//    println(part1(readInput("data/Day24_test")))
//    println(part1(readInput("data/Day24")))

    println(part2(readInput("data/Day24_test")))
    println(part2(readInput("data/Day24")))

}
