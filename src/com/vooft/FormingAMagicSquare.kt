package com.vooft

import java.util.*
import kotlin.math.abs

val arrays = arrayOf(
    arrayOf(intArrayOf(8, 1, 6), intArrayOf(3, 5, 7), intArrayOf(4, 9, 2)),
    arrayOf(intArrayOf(6, 1, 8), intArrayOf(7, 5, 3), intArrayOf(2, 9, 4)),
    arrayOf(intArrayOf(4, 9, 2), intArrayOf(3, 5, 7), intArrayOf(8, 1, 6)),
    arrayOf(intArrayOf(2, 9, 4), intArrayOf(7, 5, 3), intArrayOf(6, 1, 8)),
    arrayOf(intArrayOf(8, 3, 4), intArrayOf(1, 5, 9), intArrayOf(6, 7, 2)),
    arrayOf(intArrayOf(4, 3, 8), intArrayOf(9, 5, 1), intArrayOf(2, 7, 6)),
    arrayOf(intArrayOf(6, 7, 2), intArrayOf(1, 5, 9), intArrayOf(8, 3, 4)),
    arrayOf(intArrayOf(2, 7, 6), intArrayOf(9, 5, 1), intArrayOf(4, 3, 8))
)

fun Array<IntArray>.diff(a: Array<IntArray>): Int {
    var result = 0
    for (row in 0..2) {
        for (col in 0..2) {
            result += abs(this[row][col] - a[row][col])
        }
    }

    return result
}

fun formingMagicSquare(s: Array<IntArray>): Int {
    return arrays.map { s.diff(it) }
        .min() ?: 0
}

// https://www.hackerrank.com/challenges/magic-square-forming/problem
fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val s = Array(3) { IntArray(3) }
    for (s_i in 0..2) {
        for (s_j in 0..2) {
            s[s_i][s_j] = scanner.nextInt()
        }
    }
    val result = formingMagicSquare(s)
    println(result)
    scanner.close()
}
