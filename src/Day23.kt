import java.lang.Math.abs

data class Elf(var x: Int, var y: Int, var d: Int = 0)
fun main() {

    val dirs = listOf(
        listOf(-1, -1, -1) to listOf(-1, 0, 1), // north
        listOf(1, 1, 1) to listOf(-1, 0, 1), // south
        listOf(-1, 0, 1) to listOf(-1, -1, -1), // west
        listOf(-1, 0, 1) to listOf(1, 1, 1) // east
    )

    val deltaX = listOf(-1, 1, 0, 0)
    val deltaY = listOf(0, 0, -1, 1)


    fun getElves(input: List<String>): List<Elf> {
        val elves = mutableListOf<Elf>()

        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#') {
                    elves.add(Elf(i, j))
                }
            }
        }

        return elves
    }

    fun Elf.toPosId() = "${this.x}_${this.y}"
    fun String.toCord() = this.split("_").map { it.toInt() }

    fun move(elves: List<Elf>): Boolean {
        val posMap = elves.associateBy { it.toPosId() }.toMutableMap()

        fun hasNeighbor(elf: Elf): Boolean {
            for (i in -1..1) {
                for (j in -1..1) {
                    if (i == 0 && j == 0) continue
                    val key = "${elf.x + i}_${elf.y + j}"
                    if (key in posMap) return true
                }
            }

            return false
        }

        if (elves.all { !hasNeighbor(it) }) return false

        fun getProposals(): Map<String, String> {
            val proposals = mutableMapOf<String, String>()

            for (elf in elves) {
                if (!hasNeighbor(elf)) {
                    proposals[elf.toPosId()] = "x_x"
                    continue
                }

                for (k in 0 until 4) {
                    val z = (elf.d + k) % 4
                    val (dx, dy) = dirs[z]

                    var count = 0
                    for (t in dx.indices) {
                        val x = elf.x + dx[t]
                        val y = elf.y + dy[t]
                        val id = "$x" + "_" + "$y"

                        if (id in posMap) count ++
                    }

                    if (count == 0) {
                        proposals[elf.toPosId()] = "${elf.x + deltaX[z]}_${elf.y + deltaY[z]}"
                        break
                    }
                }

                if (elf.toPosId() !in proposals) {
                    proposals[elf.toPosId()] = "x_x"
                }
            }

            return proposals
        }

        // first round
        val proposals = getProposals()
        val propCntMap = proposals.values.groupingBy { it }.eachCount()

        // second round
        for (p in proposals) {
            val elf = posMap[p.key]!!
            if (propCntMap[p.value] == 1) {
                val (tx, ty) = p.value.toCord()
                posMap.remove(p.key)

                elf.x = tx
                elf.y = ty

                posMap[elf.toPosId()] = elf
            }
            elf.d = (elf.d + 1) % 4
        }

        return true
    }

    fun part1(input: List<String>): Int {
        val elves = getElves(input)

        repeat(10) {
            move(elves)
        }

        val minX = elves.minBy { it.x }.x
        val maxX = elves.maxBy { it.x }.x

        val minY = elves.minBy { it.y }.y
        val maxY = elves.maxBy { it.y }.y

        return (maxX - minX + 1) * (maxY - minY + 1) - elves.size
    }

    fun part2(input: List<String>): Int {
        val elves = getElves(input)

        var round = 0
        while (true) {
            round ++
            if (!move(elves)) break
        }
        return round
    }

    println(part1(readInput("data/Day23_test")))
    println(part1(readInput("data/Day23")))

    println(part2(readInput("data/Day23_test")))
    println(part2(readInput("data/Day23")))
}
