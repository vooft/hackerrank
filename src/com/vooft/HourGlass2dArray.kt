package com.vooft

import java.util.*

fun hourglassSum(arr: Array<Array<Int>>): Int {
    var maxSum = Integer.MIN_VALUE;

    for (row in (0..arr.size - 3)) {
        for (col in (0..arr[row].size - 3)) {
            val firstRowSum = (0..2).map { arr[row][col + it] }.sum()
            val secondRowSum = arr[row + 1][col + 1]
            val thirdRowSum = (0..2).map { arr[row + 2][col + it] }.sum()

            val currentSum = firstRowSum + secondRowSum + thirdRowSum
            if (maxSum < currentSum) {
                maxSum = currentSum
            }
        }
    }

    return maxSum
}

// https://www.hackerrank.com/challenges/2d-array/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val arr = Array(6) { Array(6) { 0 } }

    for (i in 0 until 6) {
        arr[i] = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()
    }

    val result = hourglassSum(arr)

    println(result)
}
