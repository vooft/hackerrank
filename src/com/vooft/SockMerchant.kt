package com.vooft

import java.util.*

fun sockMerchant(ar: Array<Int>): Int {
    val map = hashMapOf<Int, Int>()

    for (i in ar) {
        map.compute(i) { _, v -> v?.inc() ?: 1 }
    }

    return map.values
        .map { it / 2 }
        .sum()
}

// https://www.hackerrank.com/challenges/sock-merchant/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    scan.nextLine() // skip n

    val ar = scan.nextLine().trim().split(" ").map{ it.trim().toInt() }.toTypedArray()

    val result = sockMerchant(ar)

    println(result)
}
