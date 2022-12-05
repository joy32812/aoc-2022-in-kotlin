import java.util.Stack

fun main() {

  fun List<Char>.toStack(): Stack<Char> {
    val result = Stack<Char>()
    this.reversed().forEach { result.push(it) }
    return result
  }


  fun getStacks(stackInput: List<String>): List<Stack<Char>> {
    val listsByIndex = stackInput[stackInput.size - 2].chunked(4).map { mutableListOf<Char>() }

    (0 until stackInput.size - 2).forEach { i ->
      stackInput[i].chunked(4).forEachIndexed { j, s ->
        if (s[1] != ' ') listsByIndex[j] += s[1]
      }
    }

    return listsByIndex.map { it.toStack() }
  }

  fun doWork(input: List<String>, move: (from: Stack<Char>, to: Stack<Char>, num: Int) -> Unit): String {
    val (stackInput, commands) = input.partition { !it.startsWith("move") }
    val stackList = getStacks(stackInput)

    commands.forEach { command ->
      val parts = command.split(" ")
      val num = parts[1].toInt()
      val from = stackList[parts[3].toInt() - 1]
      val to = stackList[parts[5].toInt() - 1]

      move(from, to, num)
    }

    return stackList.joinToString("") { if (it.isEmpty()) "" else "" + it.peek() }
  }

  fun part1(input: List<String>): String {
    return doWork(input) { from, to, num ->
      repeat(num) { to.push(from.pop()) }
    }
  }

  fun part2(input: List<String>): String {
    return doWork(input) { from, to, num ->
      val tmp = Stack<Char>()
      repeat(num) { tmp.push(from.pop()) }
      repeat(num) { to.push(tmp.pop()) }
    }
  }


  println(part1(readInput("data/Day05_test")))
  println(part1(readInput("data/Day05")))

  println(part2(readInput("data/Day05_test")))
  println(part2(readInput("data/Day05")))
}
