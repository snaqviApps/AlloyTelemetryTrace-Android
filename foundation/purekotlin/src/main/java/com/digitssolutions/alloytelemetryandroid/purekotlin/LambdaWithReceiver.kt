package com.digitssolutions.alloytelemetryandroid.purekotlin

// 1. The Context Holder class that acts as the receiver scope
class ContextualLambda(
    val value1: Int,
    val value2: Int,
    val inputString: String)
{
    init {
        println("Inputs:\nvalue1: $value1\nvalue2: $value2\nstringData:$inputString ")
    }

    fun processAndPrint() {
        val multiple = value1 * value2
        val uppedFirstLetter = inputString.replaceFirstChar { it.uppercase() }
        print("\nResults:\n- Multiple: $multiple\n- Capitalized: $uppedFirstLetter\n")
    }

    fun avoidSecFunction() {
        print("can we have 2nd function? ")
    }
}

// 1. // 2. Higher-order function accepting a LAMBDA WITH A RECEIVER
fun lambdaWithReceiver(
    block: ContextualLambda.() -> Unit            // plainLambda counterpart: in no-receiver lambda Sample
) {
    println("Higher-Order Function Start (With Receiver)")
    println("-----------------------------------")

    // create Context data
    val contextHere = ContextualLambda(10, 20, "test With-Receiver input-string")

    //  invoke lambda with context, binding 'context' with 'this'
    contextHere.block()
}