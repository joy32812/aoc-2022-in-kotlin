import java.util.LinkedList

data class BluePrint(
    val label: Int,
    val oreForOreBot: Int,
    val oreForClayBot: Int,
    val oreForObBot: Int,
    val clayForObBot: Int,
    val oreForGeoBot: Int,
    val obForGeoBot: Int,
)

data class State(
    val turn: Int = 24,
    val oreBots: Int = 1,
    val clayBots: Int = 0,
    val obBots: Int = 0,
    val geoBots: Int = 0,
    val ore: Int = 0,
    val clay: Int = 0,
    val ob: Int = 0,
    val geo: Int = 0,
)

fun main() {

    fun String.toBluePrint(): BluePrint {
        val ns = filter { it != ':' }
            .split(" ")
            .filter { word -> word.all { it.isDigit() } }
            .map { it.toInt() }

        return BluePrint(ns[0], ns[1], ns[2], ns[3], ns[4], ns[5], ns[6])
    }

    fun work(print: BluePrint, turn: Int): Int {
        var ans = 0
        val visited = mutableSetOf<Int>()

        fun bestGuess(state: State): Int {
            return state.geo + state.geoBots * state.turn + (state.turn - 1) * state.turn / 2
        }

        fun dfs(state: State) {
            if (state.turn == 0) {
                ans = maxOf(ans, state.geo)
                return
            }

            if (state.hashCode() in visited) return
            visited.add(state.hashCode())

            if (bestGuess(state) <= ans) return

            // make geo bots.
            if (state.ore >= print.oreForGeoBot && state.ob >= print.obForGeoBot && state.turn >= 2) {
                val newState = state.copy(
                    turn = state.turn - 1,
                    geoBots = state.geoBots + 1,
                    ore = state.ore - print.oreForGeoBot + state.oreBots,
                    clay = state.clay + state.clayBots,
                    ob = state.ob - print.obForGeoBot + state.obBots,
                    geo = state.geo + state.geoBots
                )
                dfs(newState)
            }

            // make ob bots.
            if (state.ore >= print.oreForObBot && state.clay >= print.clayForObBot && state.turn >= 4 && state.obBots < 10) {
                val newState = state.copy(
                    turn = state.turn - 1,
                    obBots = state.obBots + 1,
                    ore = state.ore - print.oreForObBot + state.oreBots,
                    clay = state.clay - print.clayForObBot + state.clayBots,
                    ob = state.ob + state.obBots,
                    geo = state.geo + state.geoBots
                )
                dfs(newState)
            }

            // make clay bots.
            if (state.ore >= print.oreForClayBot && state.turn >= 6 && state.clayBots < 10) {
                val newState = state.copy(
                    turn = state.turn - 1,
                    clayBots = state.clayBots + 1,
                    ore = state.ore - print.oreForClayBot + state.oreBots,
                    clay = state.clay + state.clayBots,
                    ob = state.ob + state.obBots,
                    geo = state.geo + state.geoBots
                )
                dfs(newState)
            }

            // make ore bots.
            if (state.ore >= print.oreForOreBot && state.turn >= 4 && state.oreBots < 12) {
                val newState = state.copy(
                    turn = state.turn - 1,
                    oreBots = state.oreBots + 1,
                    ore = state.ore - print.oreForOreBot + state.oreBots,
                    clay = state.clay + state.clayBots,
                    ob = state.ob + state.obBots,
                    geo = state.geo + state.geoBots
                )
                dfs(newState)
            }

            // do nothing.
            val newState = state.copy(
                turn = state.turn - 1,
                ore = state.ore + state.oreBots,
                clay = state.clay + state.clayBots,
                ob = state.ob + state.obBots,
                geo = state.geo + state.geoBots
            )
            dfs(newState)
        }

        dfs(State(turn = turn))

        return ans
    }

    fun part1(input: List<String>): Int {
        val bluePrints = input.map { it.toBluePrint() }
        return bluePrints.sumOf { it.label * work(it, 24) }
    }

    fun part2(input: List<String>): Int {
        val bluePrints = input.map { it.toBluePrint() }.take(3)
        return bluePrints.map { work(it, 32) }.reduce { acc, i -> acc * i }
    }




//    println(part1(readInput("data/Day19_test")))
//    println(part1(readInput("data/Day19")))
//
//    println(part2(readInput("data/Day19_test")))
    println(part2(readInput("data/Day19")))


}
