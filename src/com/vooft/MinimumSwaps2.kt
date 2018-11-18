package com.vooft

import java.util.*

fun minimumSwaps(arr: Array<Int>): Int {
    var counter = 0

    var index = 0
    while (index < arr.size) {
        val value = arr[index]

        // already in the right position
        if (value - 1 == index) {
            index++
            continue
        }

        counter++

        val tmp = arr[value - 1]
        arr[value - 1] = arr[index]
        arr[index] = tmp

        if (arr[value - 1] == arr[index]) {
            index++
        }
    }

    return counter
}

// https://www.hackerrank.com/challenges/minimum-swaps-2/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    scan.nextLine() // skip n

    val arr = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

    val res = minimumSwaps(arr)

    println(res)
}
