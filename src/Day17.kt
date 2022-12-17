
enum class Direction(val dx: Int, val dy: Int) {
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0)
}
fun main() {

    val rocks = arrayOf(
        listOf("@@@@"),
        listOf(
            ".@.",
            "@@@",
            ".@.",
        ),
        listOf(
            "..@",
            "..@",
            "@@@",
        ),
        listOf(
            "@",
            "@",
            "@",
            "@",
        ),
        listOf(
            "@@",
            "@@",
        ),
    )

    var grid = Array(50000) { Array(7) { '.' } }

    var jetId = 0
    var topX = 0

    fun init() {
        grid = Array(50000) { Array(7) { '.' } }
        topX = grid.size
        jetId = 0
    }

    fun dropRock(jet: String, rock: List<String>): Pair<Int, Int> {
        var x: Int = -1
        var y: Int = -1

        fun placeRock() {
            x = topX - 3 - rock.size
            y = 2

            for (i in rock.indices) {
                for (j in rock[i].indices) {
                    grid[x + i][y + j] = rock[i][j]
                }
            }
        }

        fun Direction.toCord() = when (this) {
            Direction.DOWN -> 1 to 0
            Direction.LEFT -> 0 to -1
            Direction.RIGHT -> 0 to 1
        }

        fun canMove(dir: Direction): Boolean {
            val (dx, dy) = dir.toCord()

            for (i in rock.indices) {
                for (j in rock[i].indices) {
                    if (rock[i][j] == '@') {
                        val tx = x + i + dx
                        val ty = y + j + dy
                        if (tx !in grid.indices || ty !in grid[tx].indices) return false

                        if (grid[x + i + dx][y + j + dy] == '#') {
                            return false
                        }
                    }
                }
            }

            return true
        }

        fun moveRock(dir: Direction) {
            val (dx, dy) = dir.toCord()

            for (i in rock.indices) {
                for (j in rock[i].indices) {
                    if (rock[i][j] == '@') {
                        grid[x + i][y + j] = '.'
                    }
                }
            }

            for (i in rock.indices) {
                for (j in rock[i].indices) {
                    if (rock[i][j] == '@') {
                        grid[x + i + dx][y + j + dy] = '@'
                    }
                }
            }


            x += dx
            y += dy
        }

        fun stopRock() {
            for (i in rock.indices) {
                for (j in rock[i].indices) {
                    if (rock[i][j] == '@') {
                        grid[x + i][y + j] = '#'
                    }
                }
            }
        }

        placeRock()

        while (true) {
            val dir = when (jet[jetId]) {
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> Direction.DOWN
            }
            jetId = (jetId + 1) % jet.length

            if (canMove(dir)) moveRock(dir)

            if (canMove(Direction.DOWN)) {
                moveRock(Direction.DOWN)
            } else {
                stopRock()
                break
            }
        }

        topX = minOf(topX, x)
        return x to y
    }


    fun part1(input: List<String>): Int {
        init()

        var typeId = 0
        repeat(2022) {
            dropRock(input.first(), rocks[typeId])
            typeId = (typeId + 1) % rocks.size
        }

        return grid.size - topX
    }

    fun part2(input: List<String>): Long {
        init()

        val map = mutableMapOf<String, Int>()
        val heights = Array(10000) { 0 }

        var typeId = 0
        for (i in 0 until 10000) {
            val (_, y) = dropRock(input.first(), rocks[typeId])
            typeId = (typeId + 1) % rocks.size

            val key = "${typeId}_${jetId}_${y}"
            heights[i] = grid.size - topX
            if (i > 5000 && key in map) {
                val j = map[key]!!
                val loop = i - j
                val loopHeight = heights[i] - heights[j]

                val left = 1000000000000L - j + 1

                val ans = 0L + heights[j - 1] + (left / loop) * loopHeight

                val remain = (left % loop).toInt()
                return ans + heights[j + remain - 1 - 1] - heights[j - 1]
            }
            map[key] = i
        }

        return 0L
    }

    println(part1(readInput("data/Day17_test")))
    println(part1(readInput("data/Day17")))

    println(part2(readInput("data/Day17_test")))
    println(part2(readInput("data/Day17")))
}
