package com.vooft.implementations

import java.util.*
import kotlin.math.min

fun rotateLayer(matrix: Array<IntArray>, left: Int, top: Int) {
    val rows = matrix.size
    val cols = matrix[0].size

    val bottom = rows - top - 1
    val right = cols - left - 1

    // going down
    var previousValue = matrix[left][top]
    for (i in (top + 1) until bottom + 1) {
        val tmp = previousValue
        previousValue = matrix[i][left]
        matrix[i][left] = tmp
    }

    // going right
    for (i in (left + 1) until right + 1) {
        val tmp = previousValue
        previousValue = matrix[bottom][i]
        matrix[bottom][i] = tmp
    }

    // going up
    for (i in (bottom - 1) downTo top) {
        val tmp = previousValue
        previousValue = matrix[i][right]
        matrix[i][right] = tmp
    }

    // going left
    for (i in (right - 1) downTo left) {
        val tmp = previousValue
        previousValue = matrix[top][i]
        matrix[top][i] = tmp
    }
}

// https://www.hackerrank.com/challenges/matrix-rotation-algo/problem
fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val m = scanner.nextInt()
    val n = scanner.nextInt()
    val r = scanner.nextInt()

    val matrix = Array(m) { IntArray(n) }
    for (matrix_i in 0 until m) {
        for (matrix_j in 0 until n) {
            matrix[matrix_i][matrix_j] = scanner.nextInt()
        }
    }

    val layers = min(n, m) / 2

    repeat(layers) { pos ->
        val repeats = r % ((m - pos * 2) * 2 + (n - pos * 2) * 2 - 4)
        repeat(repeats) {
            rotateLayer(matrix, pos, pos)
        }
    }

    println(matrix.joinToString("\n") { it.joinToString(" ") })

    scanner.close()
}
