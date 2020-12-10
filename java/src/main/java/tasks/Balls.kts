package tasks

// n - кол-во шаров, m - кол-во этажей k, кол-во попыток
fun countFlours(n: Int, m: Int) {
    val k = 100
    val matrix = Array(n) { Array(k) { 1 } } // n - X k - Y
    for (i in 0 until k)
        matrix[0][i] = i + 1

    for (i in 1 until n)
        for (j in 1 until k) {
            matrix[i][j] = matrix[i - 1][j - 1] + matrix[i][j - 1]
        }

    for (i in 0 until k) {
        if (matrix[n - 1][i] >= m) {
            val a = matrix[n - 2][i - 1]
            println("нужно  $i попыток, начинаем с $a этажа")
            break
        }
    }
    println()
}

countFlours(2, 100)
