package com.vooft

import java.util.*

fun icecreamParlor(m: Int, arr: Array<Int>): Array<Int> {
    val prices = arr.withIndex().groupBy { it.value }

    for (priceList in prices.values) {
        val price = priceList[0].value
        val remaining = m - price
        if (remaining == price) {
            if (priceList.size > 1) {
                return arrayOf(priceList[0].index + 1, priceList[1].index + 1).sortedArray()
            }
        } else {
            val secondIceCream = prices[remaining]
            if (secondIceCream != null) {
                return arrayOf(priceList[0].index + 1, secondIceCream[0].index + 1).sortedArray()
            }
        }
    }

    return arrayOf()
}

// https://www.hackerrank.com/challenges/icecream-parlor/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val t = scan.nextLine().trim().toInt()

    for (tItr in 1..t) {
        val m = scan.nextLine().trim().toInt()

        scan.nextLine() // ignore n

        val arr = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

        val result = icecreamParlor(m, arr)

        println(result.joinToString(" "))
    }
}
