package com.vooft.dicthashmap

import java.io.*
import java.util.*

class Counter {
    private var currentCount = 0

    val count: Int
        get() = currentCount

    fun inc(): Counter {
        currentCount++
        return this
    }
    fun dec(): Counter {
        if (currentCount > 0) {
            currentCount--
        }

        return this
    }
}

// https://www.hackerrank.com/challenges/frequency-queries/problem
fun freqQuery(queries: Array<IntArray?>, bufferedWriter: OutputWriter) {
    val valueMap = hashMapOf<Int, Counter>() // stores value to count of existing records
    val countMap = hashMapOf<Int, Counter>() // stores number of counts, uses to speed up lookups
    for (query in queries) {
        val command = query!![0]
        val value = query[1]

        when (command) {
            1 -> {
                val counter = valueMap.compute(value) { _, oldCounter ->
                    val newCounter = if (oldCounter == null) {
                        Counter()
                    } else {
                        countMap.computeIfAbsent(oldCounter.count) { Counter() }.dec()
                        oldCounter
                    }

                    newCounter.inc()
                }

                countMap.computeIfAbsent(counter!!.count) { Counter() }.inc()
            }
            2 -> {
                val counter = valueMap.compute(value) { _, oldCounter ->
                    val newCounter = if (oldCounter == null) {
                        Counter()
                    } else {
                        countMap.computeIfAbsent(oldCounter.count) { Counter() }.dec()
                        oldCounter
                    }

                    newCounter.dec()
                }

                countMap.computeIfAbsent(counter!!.count) { Counter() }.inc()
            }
            3 -> {
                val counter = countMap[value]
                if (counter != null && counter.count > 0) {
                    bufferedWriter.printLine(1)
                } else {
                    bufferedWriter.printLine(0)
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val scanner = InputReader(System.`in`)

    val outputStream = System.getenv("OUTPUT_PATH")?.let { FileOutputStream(it) }  ?: System.out
    val bufferedWriter = OutputWriter(outputStream)

    val q = scanner.nextInt()

    val queries = arrayOfNulls<IntArray>(q)

    repeat(q) {
        queries[it] = intArrayOf(scanner.nextInt(), scanner.nextInt())
    }

    freqQuery(queries, bufferedWriter)

    bufferedWriter.close()
}

// adapted from https://www.quora.com/What-is-the-best-way-in-Java-to-take-input-and-write-output-for-an-Online-Judge/answer/Shreyans-Sheth-1?ch=10
class InputReader(private val stream: InputStream) {
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

class OutputWriter(outputStream: OutputStream) {
    private val writer = PrintWriter(BufferedWriter(OutputStreamWriter(outputStream)))

    fun print(vararg objects: Any) {
        for (i in objects.indices) {
            if (i != 0) {
                writer.print(' ')
            }
            writer.print(objects[i])
        }
    }

    fun printLine(vararg objects: Any) {
        print(*objects)
        writer.println()
    }

    fun close() {
        writer.close()
    }
}


