## What is an Expression? 

When programming in an imperative style we tend to build functions out of statements. 
We instruct the compiler on the exact steps and the order in which they should be performed in order to get to the 
desired result.
A typical function goes like this: get the data from here, modify it, save it there. 
It manipulates the data which lives somewhere outside the function itself.
This notion contradicts the meaning of a function which we acquired while learning maths at school. 
There, functions didn't modify anything. 
Instead, they took arguments and produced a new result. 
They were expressions. 

An expression can be viewed as a combination of values, variables, functions, and operators which are evaluated by the
programming language's interpreter or compiler to produce another values. 
For example, `1+2`, `x*3` and `f(42)` are all expressions. 
Typically, an expression evaluates to a *value* which can be used in further computations. 
Expressions are also *composable*, which means that an expression can be nested in other expressions enabling complex 
computations. 
You can often recognize an expression by the context it is used in: they most often appear in if-conditions, as arguments to 
functions and in the right-hand side of assignments. 

A statement's main purpose is to perform some specific *action*: declare a variable, run a loop, execute a conditional
statement etc. 
For instance, examples of statements are `val x = 13;`, `return 42`, and `println("Hello world")`. 
They generally don't return a value, instead they are the building blocks of a program written in imperative style. 
Statements dictate the *control flow* of the program. 

Of course, in real programming languages, the distinctions between expressions and statements can be less clear-cut, 
than in theory. 
Many languages permit expressions to have what is called *side effects*: they can throw exceptions, write to logs or 
read from memory. 
On the other hand, statements may return values and even be composed. 
What is important is what we consider to be the primary purpose of a given language feature. 

In functional programming languages, we tend to favor expressions for various reasons. 
We will talk about these reasons in the further lessons. 
Now let's consider how using expressions may affect the way we write code using the example of a program which 
prints if a number is even or odd. 
First, consider a statement-based implementation. 

```scala 3
def even(number: Int): Unit = {
  if (number % 2 == 0)
    println("Number is even")
  else
    println("Number is odd")
}

def main(): Unit = {
  val number = 42
  even(42)
}
```

Here we use if-statement to check if the number is even. 
Depending on the condition, we execute one of the two `println` statements. 
Notice, that no value is returned, instead everything the function does is a side effect of printing to the console. 

This style is not considered idiomatic in Scala. 
Instead, it's better that a function returns a string value which is then printed, like so: 

```scala 3
def even(number: Int): String = { 
  if (number % 2 == 0) "even" else "odd"
} 

@main
def main(): Unit = {
  val number = 42 
  val result = even(12)
  println(s"The number is $result")
}
```

This way you separate the logic of computing the values from outputting them. 
It also makes your code more readable. 

### Exercise 

Rewrite functions `abs` and `concatStrings` to be expressions which do the same things as their original implementations. 
Implement functions `sumOfAbsoluteDifferences` and `longestCommonPrefix` in the expression style.

`abs` computes the absolute value of the given integer number. 

`concatStrings` concatenates the list of strings together. 

`longestCommonPrefix` computes the longest common prefix of strings in the input list.

`sumOfAbsoluteDifferences` first computes the absolute differences of numbers on the same positions in two arrays and 
then sums them up. 
For example, having arrays `[1, 2]` and `[3, 4]`, it results in `abs(1 - 3) + abs(2 - 4) == 4`. 
You can assume the arrays always have the same length. 



































