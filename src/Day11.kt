import java.util.LinkedList

fun main() {

    class Monkey(
        val items: LinkedList<Long> = LinkedList(),
        val ops: Array<String> = Array(3) { "" },
        val tests: Array<Int> = Array(3) { 0 },
    )

    fun List<String>.toMonkeys(): List<Monkey> {
        return chunked(7).map { ms ->
            val monkey = Monkey()
            ms[1].split(":")[1].split(",").map { it.trim().toLong() }.forEach {
                monkey.items.add(it)
            }

            ms[2].split(" ").takeLast(3).forEachIndexed { i, s ->
                monkey.ops[i] = s
            }

            monkey.tests[0] = ms[3].split(" ").last().toInt()
            monkey.tests[1] = ms[4].split(" ").last().toInt()
            monkey.tests[2] = ms[5].split(" ").last().toInt()

            monkey
        }
    }

    fun solve(monkeys: List<Monkey>, repeatNum: Int, transfer: (a: Long) -> Long): Long {
        val cnt = Array(monkeys.size) { 0 }

        fun inspect() {
            monkeys.forEachIndexed { index, monkey ->
                cnt[index] += monkey.items.size

                val items = monkey.items
                val ops = monkey.ops
                val tests = monkey.tests

                for (item in items) {
                    val a = if (ops[0] == "old") item else ops[0].toLong()
                    val b = if (ops[2] == "old") item else ops[2].toLong()

                    val newValue = transfer(if (ops[1] == "+") a + b else a * b)

                    if (newValue % tests[0] == 0L) {
                        monkeys[tests[1]].items.add(newValue)
                    } else {
                        monkeys[tests[2]].items.add(newValue)
                    }
                }

                monkey.items.clear()
            }
        }

        repeat(repeatNum) {
            inspect()
        }

        return cnt.sortedDescending().take(2).let { 1L * it[0] * it[1] }
    }

    fun part1(input: List<String>): Long {
        return solve(input.toMonkeys(), 20) { it / 3 }
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.toMonkeys()
        val mod = monkeys.map { it.tests[0] }.reduce { acc, i -> acc * i }

        return solve(monkeys, 10000) { it % mod }
    }

    println(part1(readInput("data/Day11_test")))
    println(part1(readInput("data/Day11")))

    println(part2(readInput("data/Day11_test")))
    println(part2(readInput("data/Day11")))
}
