package com.vooft.graphs.dijkstrashortreach

import java.util.*

fun putEdge(neighbors: MutableMap<Int, Edge>, node: Node, weight: Int) {
    neighbors.compute(node.id) { _, edge ->
        if (edge == null || edge.weight > weight) {
            Edge(node, weight)
        } else {
            edge
        }
    }
}

fun shortestReach(n: Int, edges: Array<List<Int>>, s: Int): Array<Int> {
    val nodes = (0..n).map { Node(it, mutableMapOf()) }
    for (edgeArray in edges) {
        val node1 = nodes[edgeArray[0]]
        val node2 = nodes[edgeArray[1]]
        val weight = edgeArray[2]

        putEdge(node1.neighbors, node2, weight)
        putEdge(node2.neighbors, node1, weight)
    }

    val distances = MutableList(n + 1) { Int.MAX_VALUE.toLong() }
    distances[s] = 0

    val queue = LinkedList(nodes)
    queue.removeIf { it.neighbors.isEmpty() }
    while (!queue.isEmpty()) {
        val minNode = queue.minBy { distances[it.id] }!!
        queue.remove(minNode)

        for (edge in minNode.neighbors.values) {
            val d = distances[minNode.id] + edge.weight
            if (d < distances[edge.node.id]) {
                distances[edge.node.id] = d
            }
        }
    }

    distances.removeAt(s)
    distances.removeAt(0)

    return distances.map { it.toInt() }.map {
        when {
            it < 0 -> it
            it == Int.MAX_VALUE -> -1
            else -> it
        }
    }.toTypedArray()
}

// https://www.hackerrank.com/challenges/dijkstrashortreach/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val t = scan.nextLine().trim().toInt()

    for (tItr in 1..t) {
        val nm = scan.nextLine().split(" ")

        val n = nm[0].trim().toInt()

        val m = nm[1].trim().toInt()

        val edges = Array(m) { List(3) { 0 } }

        for (i in 0 until m) {
            edges[i] = scan.nextLine().split(" ").map{ it.trim().toInt() }
        }

        val s = scan.nextLine().trim().toInt()

        val result = shortestReach(n, edges, s)

        println(result.joinToString(" "))
    }
}


data class Node(val id: Int, val neighbors: MutableMap<Int, Edge>) {
    override fun toString(): String {
        return id.toString() + " -> " + neighbors.keys.sorted().joinToString(", ", "[", "]")
    }
}

data class Edge(val node: Node, val weight: Int)
