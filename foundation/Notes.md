
### Summary Table:
| Code Segment | Correct Kotlin Term |
| :--- | :--- |
| `inputFun(...)` | Named Function / Utility Function |
| `lambdaNoReceiver(...)` | Higher-Order Function |
| `plainLambda: (Int, Int, String) -> Unit` | Function Type Parameter (Plain Lambda) |
| `{ lNum, lNum2, lText -> ... }` | Lambda Expression / Lambda Block |


## 2. Plain Lambda vs. Lambda with Receiver
| Concept / Role | Lambda With No Receiver (`LambdaWithNoReceiver.kt`) | Lambda With Receiver (`LambdaWithReceiver.kt`) |
| :--- | :--- | :--- |
| **Higher-Order Function** | `lambdaNoReceiver(plainLambda: ...)` | `lambdaWithReceiver(block: ContextualLambda.() -> Unit)` |
| **Function Type Parameter** | **Plain Function Type**:<br>`(Int, Int, String) -> Unit` | **Extension Function Type** (Lambda with Receiver):<br>`ContextualLambda.() -> Unit` |
| **How Data is Passed** | Explicitly as function arguments:<br>`plainLambda(num1, num2, stringToTest)` | Implicitly as a receiver scope (`this` context):<br>`contextHere.block()` |
| **Lambda Expression (Call Site)** | `lambdaNoReceiver { lNum, lNum2, lText -> ... }` | `lambdaWithReceiver { processAndPrint() }` |
| **How Members Are Accessed** | Via named parameter variables (`lNum`, `lText`) | Directly via `this` context (`processAndPrint()`, `value1`, etc.) |

## Key Takeaways
* **Plain Lambda (`(A, B) -> Unit`):** You must explicitly declare and name every input parameter in the lambda header (`{ a, b -> ... }`).
* **Lambda with Receiver (`Receiver.() -> Unit`):** You prepend a receiver class name (`ContextualLambda.()`). Inside the lambda block, that object becomes `this`, allowing you to call its methods and properties directly without naming them (powering Kotlin's clean DSL and scope builder patterns like `with`, `run`, or Jetpack Compose UI builders!).