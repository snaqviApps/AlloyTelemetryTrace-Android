package com.digitssolutions.alloytelemetryandroid.purekotlin

fun main() {
    println("\n\n-------------------------------------")
    println("Step#1, No-Receiver Lambda at work:")
    println("-------------------------------------\n")
    lambdaNoReceiver { lNum, lNum2, lText ->
        inputFun(lNum, lNum2, lText)
    }

    println("\n\n-------------------------------------")
    println("Step#2, Receiver Lambda at work:")
    println("-------------------------------------\n")
    lambdaWithReceiver {

        // `this` is implicitly a ComputationContext. We can call its methods directly:
        processAndPrint()
        avoidSecFunction()
    }

}

