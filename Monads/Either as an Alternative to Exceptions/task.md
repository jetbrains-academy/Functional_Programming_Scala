Sometimes you want to know a little more about the reason why a particular function failed. 
This is why we have multiple types of exceptions: apart from sending a panic signal, we also explain what happened. 
`Option` is not suitable to convey this information, and `Either[A, B]` is used instead. 
An instance of `Either[A, B]` can only contain a value of type `A`, or a value of type `B`, but not simultaneously.
This is achieved by `Either` having two subclasses: `Left[A]` and `Right[B]`.
Every time there is a partial function `def partialFoo(...): B` that throws exceptions and returns type `B`, we can replace it with a total function `def totalFoo(...): Either[A, B]` where `A` describes the possible errors. 

Like `Option`, `Either` is a monad that means it allows chaining of successful operations. 
The convention is that the failure is represented with `Left`, while `Right` wraps the value computed in the case of success. 
It's an arbitrary decision and everything would work the same way if we were to choose differently. 
However, a useful mnemonic is that `Right` is for cases when everything went right. 
Thus, `unit` wraps the value in the `Right` constructor, and `flatMap` runs the second function only if the first function resulted in `Right`. 
If an error happens and `Left` appears at any point, then the execution stops and that error is reported.  

Consider a case where you read two numbers from the input stream and divide one by the other. 
This function can fail in two ways: if the user provides a non-numeric input, or if a division by zero error occurs. 
We can implement this as a sequence of two functions: 

```scala 3
def readNumbers(x: String, y: String): Either[String, (Double, Double)] =
    (x.toDoubleOption, y.toDoubleOption) match
        case (Some(x), Some(y)) => Right (x, y)
        case (None, Some(y)) => Left("First string is not a number")
        case (Some(x), None) => Left("Second string is not a number")
        case (None, None) => Left("Both strings are not numbers")

def safeDiv(x: Double, y: Double): Either[String, Double] =
    if (y == 0) Left("Division by zero")
    else Right(x / y)

@main
def main() =
    val x = readLine()
    val y = readLine()
    print(readNumbers(x, y).flatMap(safeDiv))
```

Note that we have used `String` for errors here, but we could have used a custom data type. 
We could even create a whole hierarchy of errors if we wished to do so. 
For example, we could make `Error` into a trait and then implement classes for IO errors, network errors, invalid state errors, and so on. 
Another option is to use the standard Java hierarchy of exceptions, like in the following `safeDiv` implementation. 
Note that no exception is actually thrown here, instead you can retrieve the kind of error by pattern matching on the result.  

```scala 3
def safeDiv(x: Double, y: Double): Either[Throwable, Double] =
    if (y == 0) Left(new IllegalArgumentException("Division by zero"))
    else Right(x / y)
```

### Exercise

Let's get back to our `UserService` from the previous lesson. 
There are three possible reasons why `getGrandchild` may fail: 

* The user with the given name can't be found.  
* The user doesn't have a child. 
* The user's child doesn't have a child. 

To explain the failure to the caller, we created the `SearchError` enum and changed the types of the `findUser`, `getGrandchild`, `getGrandchildAge` functions to be `Either[SearchError, _]`. 

Your task is to implement the functions providing the appropriate error message. 
There is a helper function `getChild` to implement so that `getGrandchild` could use `flatMap`s naturally. 



