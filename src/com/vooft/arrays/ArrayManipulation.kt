package com.vooft.arrays

import java.util.*

// https://www.hackerrank.com/challenges/crush/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
fun arrayManipulation(n: Int, queries: Array<IntArray>): Long {
    val d = LongArray(n + 1) { 0L }

    for (q in queries) {
        val l = q[0] - 1
        val r = q[1] - 1
        val k = q[2]

        d[l] = d[l] + k
        d[r + 1] = d[r + 1] - k
    }

    var previous = d[0]
    var max = d[0]

    for (i in 1 until d.size) {
        previous += d[i]
        if (previous > max) {
            max = previous
        }
    }

    return max
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val nm = scan.nextLine().split(" ")

    val n = nm[0].trim().toInt()

    val m = nm[1].trim().toInt()

    val queries = Array(m) { IntArray(3) { 0 } }

    for (i in 0 until m) {
        queries[i] = scan.nextLine().split(" ").map{ it.trim().toInt() }.toIntArray()
    }

    val result = com.vooft.tmp.arrayManipulation(n, queries)

    println(result)
}
