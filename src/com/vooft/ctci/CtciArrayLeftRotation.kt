package com.vooft.ctci

import java.util.*

fun rotLeft(a: Array<Int>, originalD: Int): Array<Int> {
    val d = originalD % a.size
    if (d == 0) {
        return a
    }

    val b = Array(a.size) { 0 }

    for (index in (0 until a.size)) {
        var newIndex = index - d
        if (newIndex < 0) {
            newIndex += a.size
        }

        b[newIndex] = a[index]
    }

    return b
}

// https://www.hackerrank.com/challenges/ctci-array-left-rotation/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val nd = scan.nextLine().split(" ")

    val d = nd[1].trim().toInt()

    val a = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

    val result = rotLeft(a, d)

    println(result.joinToString(" "))
}
