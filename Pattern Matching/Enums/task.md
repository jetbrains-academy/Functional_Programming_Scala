# Enum 

An enumeration (or enum) is a type that represents a limited set of distinct values. 
Enumerations are commonly used to limit the set of possible values of a field. 
This way enums improve code clarity and reliability – 
since it is not possible for a field to be set to something outside a small set of well-known values, 
we can make sure that the logic we implement handles all possibilities and 
there's nothing left that we haven't thought about.

In Scala 3, enumerations are created using the `enum` keyword. 
Each value of the enum is an object of the *enumerated type*. 
Scala 3 enums can also have parameterized values and  methods. 
You have already seen it in our previous examples where we used enums to define colors of cat's fur:

```scala 3
enum Color:
  case White, Black, Ginger
``` 

But Scala 3 enums are more powerful than that. 
In fact, more powerful than their equivalent in many other programming languages. 
Enums in Scala 3 can also be used as algebraic data types 
(also known as sealed trait hierarchies in Scala 2). 
You can have an enum with cases that carry different data. 
Here's an example:

```scala 3
enum Tree[+A]:
  case Branch(left: Tree[A], value: A, right: Tree[A])
  case Leaf(value: A)
  case Stump

/*
    3
   / \
  2   5
 /   / \
1   4   6
*/

import Tree.*

val tree: Tree[Int] =
  Branch(
    Branch(Leaf(1), 2, Stump),
    3,
    Branch(Leaf(4), 5, Leaf(6))
  )
``` 

In this example, `Tree` is an enum which models a binary tree data structure. 
Binary trees are used in many areas of computer science, for sorting, searching, and efficient data access.
They consist of nodes where each node can have at most two subtrees. 
Here we implement a binary tree with an enum `Tree[A]` which allows the nodes of the tree
to be one of three possible kinds:
* a `Branch` which has a value of type `A` and two subtrees, `left` and `right`,
* a `Leaf` which has a value of type `A` but no subtrees,
* a `Stump` which models the end of the branch on the given side.

Please note that our implementation of a binary tree is slightly different from the classic one. 
You may notice that ours is a bit redundant: 
a `Leaf` is in all practical sense the same as a `Branch` where both subtrees are stumps. 
But having `Leaf` as a separate enum case allows us to write the code for building 
the tree in a more concise way.

## Exercise 

Implement a function which checks if the tree is balanced.

A balanced binary tree meets the following conditions:
* The absolute difference of heights of left and right subtrees at any node is no more than 1.
* For each node its left subtree is a balanced binary tree
* For each node its right subtree is a balanced binary tree

Try to do it in one pass for the extra challenge. 