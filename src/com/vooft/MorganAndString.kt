package com.vooft

fun morganAndString(aSrc: String, bSrc: String): String {
    val a = "${aSrc}x"
    val b = "${bSrc}z"

    var aIndex = 0
    var bIndex = 0

    val aSize = a.length
    val bSize = b.length

    val sb = StringBuilder(aSize + bSize)
    while (aIndex < aSize || bIndex < bSize) {
        if (aIndex == aSize) {
            sb.append(b[bIndex++])
        } else if (bIndex == bSize) {
            sb.append(a[aIndex++])
        } else if (a[aIndex] < b[bIndex]) {
            sb.append(a[aIndex++])
        } else if (a[aIndex] > b[bIndex]) {
            sb.append(b[bIndex++])
        } else {
            var aNextChar = a[aIndex]
            var bNextChar = b[bIndex]

            var offset = 1
            var sameChars = true
            var sameCharsCounter = 1
            while (aNextChar == bNextChar) {
                aNextChar = a[aIndex + offset]
                bNextChar = b[bIndex + offset]

                if (sameChars && aNextChar == a[aIndex]) {
                    sameCharsCounter++
                } else {
                    sameChars = false
                }

                offset++
            }

            if (aNextChar <= bNextChar) {
                sb.append(a.substring(aIndex, aIndex + sameCharsCounter))
                aIndex += sameCharsCounter
            } else {
                sb.append(b.substring(bIndex, bIndex + sameCharsCounter))
                bIndex += sameCharsCounter
            }
        }
    }

    val result = sb.toString()
    return result.substring(0, result.length - 2)
}

// https://www.hackerrank.com/challenges/morgan-and-a-string/problem
fun main(args: Array<String>) {
    val scanner = java.util.Scanner(System.`in`)
    val t = scanner.nextInt()

    repeat(t) {
        val a = scanner.next()
        val b = scanner.next()
        println(morganAndString(a, b))
    }

    scanner.close()

//    assertEquals(morganAndString("JACK", "DANIEL"), "DAJACKNIEL")
//    assertEquals(morganAndString("ABACABA", "ABACABA"), "AABABACABACABA")
//    assertEquals(morganAndString("DAD", "DAD"), "DADADD")
//    assertEquals(morganAndString("ABCBA", "BCBA"), "ABBCBACBA")
//    assertEquals(morganAndString("BAC", "BAB"), "BABABC")
//    assertEquals(morganAndString("DAD", "DABC"), "DABCDAD")
//    assertEquals(morganAndString("YZYYZYZYY", "ZYYZYZYY"), "YZYYZYYZYZYYZYZYY")
//    assertEquals(morganAndString("ZZYYZZZA", "ZZYYZZZB"), "ZZYYZZYYZZZAZZZB")
}
