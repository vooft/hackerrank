package com.vooft.sort

import java.io.*
import java.util.*

fun countSort(arr: Array<Array<String>>): String {
    val sorted = Array<MutableList<String>>(100) { mutableListOf() }

    val middle = arr.size / 2
    for ((index, a) in arr.withIndex()) {
        val value = if (index >= middle) {
            a[1]
        } else {
            "-"
        }
        sorted[a[0].toInt()].add(value)
    }

    return sorted.asSequence()
        .filter { !it.isEmpty() }
        .flatMap { it.asSequence() }
        .joinToString(" ")
}

// https://www.hackerrank.com/challenges/countingsort4/problem
fun main(args: Array<String>) {
    val scan = InputReader(System.`in`)
    val outputWriter = OutputWriter(System.out)

    val n = scan.nextInt()

    val arr = Array(n) { Array(2) { "" } }

    repeat(n) {
        arr[it][0] = scan.nextString()
        arr[it][1] = scan.nextString()
    }

    val result = countSort(arr)
    outputWriter.write(result)
    outputWriter.close()
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

    fun nextString(): String {
        var c = read()
        while (isSpaceChar(c)) {
            c = read()
        }
        val res = StringBuilder()
        do {
            res.appendCodePoint(c)
            c = read()
        } while (!isSpaceChar(c))
        return res.toString()
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

