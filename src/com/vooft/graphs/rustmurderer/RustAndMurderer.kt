package com.vooft.graphs.rustmurderer

import java.io.*
import java.util.*

// https://www.hackerrank.com/challenges/rust-murderer/problem
fun main(args: Array<String>) {
    val scanner = InputReader(System.`in`)

    val outputStream = System.getenv("OUTPUT_PATH")?.let { FileOutputStream(it) }  ?: System.out
    val bufferedWriter = OutputWriter(outputStream)

    val t = scanner.nextInt()

    for (tItr in 0 until t) {
        val n = scanner.nextInt()

        val m = scanner.nextInt()

        val roads = Array(m) { IntArray(2) }

        for (roadsRowItr in 0 until m) {
            for (roadsColumnItr in 0..1) {
                roads[roadsRowItr][roadsColumnItr] = scanner.nextInt()
            }
        }

        val s = scanner.nextInt()

        val result = rustMurderer(n, roads, s)

        for (resultItr in result.indices) {
            bufferedWriter.write(result[resultItr].toString())

            if (resultItr != result.size - 1) {
                bufferedWriter.write(" ")
            }
        }

        bufferedWriter.newLine()
    }

    bufferedWriter.close()
}

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

    fun write(vararg objects: Any) {
        for (i in objects.indices) {
            if (i != 0) {
                writer.print(' ')
            }
            writer.print(objects[i])
        }
        writer.flush()
    }

    fun newLine(vararg objects: Any) {
        write(*objects)
        writer.println()
        writer.flush()
    }

    fun close() {
        writer.close()
    }
}

data class Node(val id: Int, val neighbors: MutableSet<Int>)

fun rustMurderer(n: Int, mainRoads: Array<IntArray>, s: Int): IntArray {
    val nodes = (0..n).map { Node(it, mutableSetOf()) }
    mainRoads.forEach {
        val node1 = nodes[it[0]]
        val node2 = nodes[it[1]]

        node1.neighbors.add(node2.id)
        node2.neighbors.add(node1.id)
    }

    val hasOrphanNode = nodes.any { it.id != 0 && it.neighbors.isEmpty() }

    // if there's an orphan node, it is either 1 (s -> i) or 2 (s -> orphan -> i)
    val distances = if (hasOrphanNode) {
        val startNode = nodes[s]

        MutableList(n + 1) {
            if (startNode.neighbors.contains(it)) {
                2
            } else {
                1
            }
        }
    } else {
        // if there's no orphan node, try bfs
        val distances = IntArray(n + 1) { n + 1 }
        distances[s] = 0

        val queue = LinkedList(nodes)
        while (!queue.isEmpty()) {
            val minNode = queue.minBy { distances[it.id] }!!
            queue.remove(minNode)

            (1..n).filter { !minNode.neighbors.contains(it) }.forEach {
                val d = distances[minNode.id] + 1
                if (d < distances[it]) {
                    distances[it] = d
                }
            }
        }

        distances.toMutableList()
    }

    distances.removeAt(s)
    distances.removeAt(0)

    return distances.toIntArray()
}

