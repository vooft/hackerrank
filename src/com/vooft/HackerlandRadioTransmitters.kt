package com.vooft

import java.util.*

fun hackerlandRadioTransmitters(distances: Array<Int>, k: Int): Int {
    Arrays.sort(distances)

    var counter = 0
    var previousCoveredHouse = -1
    var index = 0
    while (index < distances.size) {
        if (previousCoveredHouse < 0
            || distances[index] > previousCoveredHouse) {

            var newLocationIndex = index
            for (possibleIndex in (index until distances.size - 1)) {
                if (distances[possibleIndex + 1] - distances[index] > k) {
                    newLocationIndex = possibleIndex
                    break
                }
            }

            counter++

            index = newLocationIndex
            previousCoveredHouse = distances[newLocationIndex] + k
        }

        index++
    }

    return counter
}

// https://www.hackerrank.com/challenges/hackerland-radio-transmitters/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val nk = scan.nextLine().split(" ")

    val k = nk[1].trim().toInt()

    val x = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

    val result = hackerlandRadioTransmitters(x, k)

    println(result)

//    assertEquals(2, hackerlandRadioTransmitters(arrayOf(1, 2, 3, 4, 5), 1))
//    assertEquals(3, hackerlandRadioTransmitters(arrayOf(7, 2, 4, 6, 5, 9, 12, 11), 2))
//    assertEquals(2, hackerlandRadioTransmitters(arrayOf(0, 6), 3))
}
