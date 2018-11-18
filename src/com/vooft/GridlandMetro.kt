package com.vooft

import java.util.*

fun gridlandMetro(n: Long, m: Long, allTracks: Array<Array<Int>>): Long {
    // create a map grouped by row and sorted by the start cell
    val tracksByRow: Map<Int, LinkedList<Array<Int>>> = allTracks.groupBy { it[0] }
        .mapValues { entry -> LinkedList(entry.value.sortedBy { it[1] }) }

    var counter = (n - tracksByRow.size) * m

    tracksByRow.values.forEach { tracks ->
        val firstTrack = tracks.removeFirst()

        val occupiedRanges = mutableListOf<Pair<Int, Int>>()
        var currentTrack: Pair<Int, Int> = firstTrack[1] to firstTrack[2]
        for (track in tracks) {
            if (track[1] <= currentTrack.second) {
                if (track[2] > currentTrack.second) {
                    currentTrack = currentTrack.first to track[2]
                }
            } else {
                occupiedRanges.add(currentTrack)
                currentTrack = track[1] to track[2]
            }
        }

        occupiedRanges.add(currentTrack)

        val occupiedCounter = occupiedRanges.map { it.second - it.first + 1 }.sum()
        counter += m - occupiedCounter
    }

    return counter
}

// https://www.hackerrank.com/challenges/gridland-metro/problem
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val nmk = scan.nextLine().split(" ")

    val n = nmk[0].trim().toLong()

    val m = nmk[1].trim().toLong()

    val k = nmk[2].trim().toInt()

    val track = Array(k) { Array(3) { 0 } }

    for (i in 0 until k) {
        track[i] = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()
    }

    val result = gridlandMetro(n, m, track)

    println(result)

//    assertEquals(9, gridlandMetro(4, 4, arrayOf(arrayOf(2, 2, 3), arrayOf(3, 1, 4), arrayOf(4, 4, 4))))
//    assertEquals(16, gridlandMetro(4, 4, arrayOf()))
//    assertEquals(335820405811182700, gridlandMetro(693177850, 484465003, arrayOf(
//        arrayOf(236503426, 316332186, 461015095),
//        arrayOf(325185143, 159242615, 308431802),
//        arrayOf(55252986, 91207426, 145729635),
//        arrayOf(570145738, 66187476, 391212600),
//        arrayOf(371200915, 365371882, 394702624),
//        arrayOf(118881009, 192274800, 210407482),
//        arrayOf(494441153, 367388414, 454335996),
//        arrayOf(679306824, 25218436, 93249072),
//        arrayOf(664325323, 205159570, 231255973),
//        arrayOf(397092493, 153491684, 180964464),
//        arrayOf(481315381, 2248120, 93447775),
//        arrayOf(479915594, 29751180, 336039384),
//        arrayOf(237043892, 220540564, 426154493),
//        arrayOf(350500883, 222092050, 333611680),
//        arrayOf(467416230, 12887611, 289492620),
//        arrayOf(118881009, 192274800, 199091060),
//        arrayOf(55252986, 145729635, 180957024),
//        arrayOf(236503426, 461015095, 483469100),
//        arrayOf(467416230, 289492620, 406503093),
//        arrayOf(664325323, 205159570, 413796563),
//        arrayOf(467416230, 12887611, 163385052),
//        arrayOf(325185143, 308431802, 378788221),
//        arrayOf(679306824, 25218436, 138282970),
//        arrayOf(55252986, 145729635, 180435002),
//        arrayOf(236503426, 461015095, 465216925),
//        arrayOf(397092493, 153491684, 438933079),
//        arrayOf(570145738, 66187476, 357191181),
//        arrayOf(664325323, 231255973, 378071076),
//        arrayOf(325185143, 308431802, 393452446),
//        arrayOf(371200915, 394702624, 478996774),
//        arrayOf(479915594, 336039384, 347196320),
//        arrayOf(494441153, 454335996, 462823117),
//        arrayOf(397092493, 153491684, 278739320),
//        arrayOf(397092493, 180964464, 420999696),
//        arrayOf(237043892, 426154493, 445271380),
//        arrayOf(570145738, 391212600, 455384599),
//        arrayOf(350500883, 222092050, 480223826),
//        arrayOf(237043892, 220540564, 416563766),
//        arrayOf(570145738, 66187476, 276585244),
//        arrayOf(118881009, 210407482, 391001534),
//        arrayOf(494441153, 367388414, 455636020),
//        arrayOf(467416230, 289492620, 409024638),
//        arrayOf(55252986, 145729635, 151698140),
//        arrayOf(479915594, 336039384, 365916020),
//        arrayOf(118881009, 192274800, 440912976),
//        arrayOf(481315381, 93447775, 210550337))
//    ))
}
