import java.util.LinkedList

fun main() {

  class Node(val size: Int, val children: List<String>)

  fun getFileMap(input: List<String>): Map<String, Node> {
    val fileMap = mutableMapOf<String, Node>()
    val pathList = LinkedList<String>()

    var i = 0
    while (i < input.size) {
      val splits = input[i].split(" ")

      if (splits[1] == "cd") {
        if (splits[2] == "..") pathList.removeLast()
        else pathList.add(splits[2])
        i ++
      } else if (splits[1] == "ls") {

        i ++
        val children = mutableListOf<String>()
        while (i < input.size) {
          if (input[i].startsWith("$")) break
          val parts = input[i].split(" ")
          val path = pathList.joinToString("/") + "/" + parts[1]

          children += path
          if (parts[0] != "dir") {
            fileMap[path] = Node(parts[0].toInt(), emptyList())
          }
          i ++
        }

        val path = pathList.joinToString("/")
        fileMap[path] = Node(-1, children)
      }
    }

    return fileMap
  }

  fun getDirSizeList(fileMap: Map<String, Node>): List<Int> {
    val dirSizeList = mutableListOf<Int>()
    fun dfs(path: String): Int {
      val node = fileMap[path] ?: return 0
      if (node.size != -1) return node.size

      val size = node.children.sumOf { dfs(it) }
      dirSizeList += size

      return size
    }

    dfs("/")
    return dirSizeList
  }

  fun part1(input: List<String>): Int {
    val fileMap = getFileMap(input)
    val dirSizeList = getDirSizeList(fileMap)

    return dirSizeList.filter { it < 100000 }.sum()
  }

  fun part2(input: List<String>): Int {
    val fileMap = getFileMap(input)
    val dirSizeList = getDirSizeList(fileMap)

    val needed = 30000000 - (70000000 - dirSizeList.max())
    return dirSizeList.filter { it >= needed }.min()
  }

  println(part1(readInput("data/Day07_test")))
  println(part1(readInput("data/Day07")))

  println(part2(readInput("data/Day07_test")))
  println(part2(readInput("data/Day07")))
}
