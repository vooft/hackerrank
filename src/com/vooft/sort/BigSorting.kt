package com.vooft.sort

import java.util.*

fun bigSorting(unsorted: Array<String>): Array<String> {
    Arrays.sort(unsorted) { s1, s2 ->
        when {
            s1.length == s2.length -> s1.compareTo(s2)
            else -> s1.length - s2.length
        }
    }

    return unsorted
}

// https://www.hackerrank.com/challenges/big-sorting/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val n = scan.nextLine().trim().toInt()

    val unsorted = Array(n) { "" }
    for (i in 0 until n) {
        val unsortedItem = scan.next()
        unsorted[i] = unsortedItem
    }

    val result = bigSorting(unsorted)

    result.forEach { println(it) }
}
