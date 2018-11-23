package com.vooft.graphs.dijkstrashortreach

import java.io.*
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

fun shortestReach(n: Int, edges: Array<IntArray>, s: Int): Array<Int> {
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
    val scanner = InputReader(System.`in`)

    val outputStream = System.getenv("OUTPUT_PATH")?.let { FileOutputStream(it) }  ?: System.out
    val bufferedWriter = OutputWriter(outputStream)

    val t = scanner.nextInt()

    for (tItr in 0 until t) {
        val n = scanner.nextInt()
        val m = scanner.nextInt()

        val edges = Array(m) { IntArray(3) }

        for (i in 0 until m) {
            for (j in 0..2) {
                edges[i][j] = scanner.nextInt()
            }
        }

        val s = scanner.nextInt()

        val result = shortestReach(n, edges, s)

        for (i in result.indices) {
            bufferedWriter.print(result[i].toString())

            if (i != result.size - 1) {
                bufferedWriter.print(" ")
            }
        }

        bufferedWriter.printLine()
    }

    bufferedWriter.close()
}


data class Node(val id: Int, val neighbors: MutableMap<Int, Edge>) {
    override fun toString(): String {
        return id.toString() + " -> " + neighbors.keys.sorted().joinToString(", ", "[", "]")
    }
}

data class Edge(val node: Node, val weight: Int)

// adapted from https://www.quora.com/What-is-the-best-way-in-Java-to-take-input-and-write-output-for-an-Online-Judge/answer/Shreyans-Sheth-1?ch=10
private class InputReader(private val stream: InputStream) {
    private val buf = ByteArray(1024)
    private var curChar: Int = 0
    private var numChars: Int = 0

    fun read(): Int {
        if (numChars == -1) {
            throw InputMismatchException()
        }
        if (curChar >= numChars) {
            curChar = 0
            try {
                numChars = stream.read(buf)
            } catch (e: IOException) {
                throw InputMismatchException()
            }

            if (numChars <= 0) {
                return -1
            }
        }
        return buf[curChar++].toInt()
    }

    fun nextInt(): Int {
        var c = read()
        while (isSpaceChar(c)) {
            c = read()
        }
        var sgn = 1
        if (c == '-'.toInt()) {
            sgn = -1
            c = read()
        }
        var res = 0
        do {
            if (c < '0'.toInt() || c > '9'.toInt()) {
                throw InputMismatchException()
            }
            res *= 10
            res += c - '0'.toInt()
            c = read()
        } while (!isSpaceChar(c))
        return res * sgn
    }

    fun isSpaceChar(c: Int): Boolean {
        return (c == ' '.toInt() || c == '\n'.toInt() || c == '\r'.toInt() || c == '\t'.toInt() || c == -1)
    }
}

private class OutputWriter(outputStream: OutputStream) {
    private val writer = PrintWriter(BufferedWriter(OutputStreamWriter(outputStream)))

    fun print(vararg objects: Any) {
        for (i in objects.indices) {
            if (i != 0) {
                writer.print(' ')
            }
            writer.print(objects[i])
        }
        writer.flush()
    }

    fun printLine(vararg objects: Any) {
        print(*objects)
        writer.println()
        writer.flush()
    }

    fun close() {
        writer.close()
    }
}


