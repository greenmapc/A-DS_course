//
fun getCoronaPoison() {
    val firstSeq = intArrayOf(1, 5, 3, 7, 2, 1, 6) // 1f + 2s + 5f + 6s + 1s + 3f + 6f
    val bridgeSeq = intArrayOf(1, 2, 1, 1, 2, 3)
    val bitdgeBackSeq = intArrayOf(1, 2, 1, 1, 15, 25)
    val secondSeq = intArrayOf(2, 1, 6, 5, 1, 7, 13) // 2s + 1s + 5f + 6s + 1s + 3f + 6f


    var firstOptimalWay = firstSeq[0]
    var secondOptimalWay = secondSeq[0]
    val firstStack = ArrayList<String>()
    val secondStack = ArrayList<String>()
    var firstPlace = true
    var secondPlace = false

    firstStack.add("first")
    secondStack.add("second")

    for (i in 1 until firstSeq.size - 1) {
        if (firstPlace) {
            if (firstSeq[i] > secondSeq[i] + bridgeSeq[i - 1]) {
                firstOptimalWay += secondSeq[i] + bridgeSeq[i - 1]
                firstStack.add("second")
                firstPlace = false
            } else {
                firstOptimalWay += firstSeq[i]
                firstStack.add("first")
                firstPlace = true
            }
        } else {
            if (secondSeq[i] > firstSeq[i] + bridgeSeq[i - 1]) {
                firstOptimalWay += firstSeq[i] + bridgeSeq[i - 1]
                firstStack.add("first")
                firstPlace = true
            } else {
                firstOptimalWay += secondSeq[i]
                firstStack.add("second")
                firstPlace = false
            }

        }

        if (secondPlace) {
            if (firstSeq[i] > secondSeq[i] + bitdgeBackSeq[i - 1]) {
                secondOptimalWay += secondSeq[i] + bitdgeBackSeq[i - 1]
                secondStack.add("second")
                secondPlace = false
            } else {
                secondOptimalWay += firstSeq[i]
                secondStack.add("first")
                secondPlace = true
            }
        } else {
            if (secondSeq[i] > firstSeq[i] + bitdgeBackSeq[i - 1]) {
                secondOptimalWay += firstSeq[i] + bitdgeBackSeq[i - 1]
                secondStack.add("first")
                secondPlace = true
            } else {
                secondOptimalWay += secondSeq[i]
                secondStack.add("second")
                secondPlace = false
            }

        }
    }

    if (firstStack.last() == "first")
        firstStack.add("first")
    else
        firstStack.add("second")

    if (secondStack.last() == "first")
        secondStack.add("first")
    else
        secondStack.add("second")

    if (firstOptimalWay > secondOptimalWay) {
        println("second way optimal and equals $secondOptimalWay")
        for (el in secondStack)
            println(el)
    } else {
        println("first way optimal and equals $firstOptimalWay")
        for (el in firstStack)
            println(el)
    }
}

getCoronaPoison()