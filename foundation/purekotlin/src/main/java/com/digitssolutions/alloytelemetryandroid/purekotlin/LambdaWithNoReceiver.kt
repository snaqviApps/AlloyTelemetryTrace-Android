package com.digitssolutions.alloytelemetryandroid.purekotlin

fun inputFun(num1: Int, num2: Int, inputStr: String) {
    println("\nplain-lambda-Function Output")
    println("------------------------------")

    val multiple = num1 * num2
    val uppedFirstLetter = inputStr.replaceFirstChar { it.uppercase() }
    print("\nResults:\n1. Input-Multiple: $multiple,\n2. First letter capitalized: $uppedFirstLetter\n")
}

// 1. Higher-order function that accepts the data values AND the plain lambda
fun lambdaNoReceiver(
    plainLambda: (Int, Int, String) -> Unit
) {
    println("Higher-Order Function Start")
    println("-----------------------------------")

    val num1=10; val num2=2; val stringToTest = "test No-Receiver input-string"

    //  Lambda is going to take input here and not in main(), to keep main()-entry clean, and only for 'How', not 'What'
    println("Inputs:\nNumber1: $num1,\nNumber2: $num2,\nInputString: $stringToTest" )
    plainLambda(num1, num2, stringToTest)
}