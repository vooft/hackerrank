package com.vooft.graphs

import java.util.*

fun bfs(numberOfNodes: Int, edges: Array<Array<Int>>, startNodeId: Int): Array<Int> {
    val nodes = (1..numberOfNodes).map { Node(it, mutableMapOf()) }
    for (edge in edges) {
        val node1 = nodes[edge[0] - 1]
        val node2 = nodes[edge[1] - 1]

        node1.neighbors[node2.id] = node2
        node2.neighbors[node1.id] = node1
    }

    // numberOfNodes + 1 is the max length of any path
    val distances = MutableList(numberOfNodes + 1) { numberOfNodes + 1 }

    distances[startNodeId] = 0

    val queue = LinkedList(nodes)
    queue.removeIf { it.neighbors.isEmpty() }
    while (!queue.isEmpty()) {
        val minNode = queue.minBy { distances[it.id] }!!
        queue.remove(minNode)

        for (neighbor in minNode.neighbors.values) {
            val alt = distances[minNode.id] + 1
            if (alt < distances[neighbor.id]) {
                distances[neighbor.id] = alt
            }
        }
    }

    distances.removeAt(startNodeId)
    distances.removeAt(0)
    return distances.map {
        when {
            it < 0 -> it
            it == numberOfNodes + 1 -> -1
            else -> it * 6
        }
    }.toTypedArray()
}

// https://www.hackerrank.com/challenges/bfsshortreach/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val q = scan.nextLine().trim().toInt()

    for (qItr in 1..q) {
        val nm = scan.nextLine().split(" ")

        val n = nm[0].trim().toInt()

        val m = nm[1].trim().toInt()

        val edges = Array(m) { Array(2) { 0 } }

        for (i in 0 until m) {
            edges[i] = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()
        }

        val s = scan.nextLine().trim().toInt()

        val result = bfs(n, edges, s)

        println(result.joinToString(" "))
    }

    println()
}

data class Node(val id: Int, val neighbors: MutableMap<Int, Node>) {
    override fun toString(): String {
        return id.toString() + " -> " + neighbors.keys.sorted().joinToString(", ", "[", "]")
    }
}
