package com.llamalad7.ipcdemo

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "Please pass the process name and args as parameters!" }
    withProcess(*args) {
        sendLine("Hi")
        val greeting = receiveLine()
        check(greeting == "Hi") { "Process responded incorrectly to 'Hi': '$greeting'" }

        val nums = (1..100).map { getRandomNumber() }.sorted()
        println(nums)
        println(nums.average())
        println(nums.median())

        sendLine("Shutdown")
    }
}

private fun ProcessScope.getRandomNumber(): Int {
    sendLine("GetRandom")
    val str = receiveLine()
    val num = str.toIntOrNull()
    checkNotNull(num) { "Process responded incorrectly to 'GetRandom': '$str'" }
    return num
}

private fun List<Int>.median(): Double =
    if (size % 2 == 1) {
        this[size / 2].toDouble()
    } else {
        (this[size / 2] + this[size / 2 - 1]) / 2.0
    }