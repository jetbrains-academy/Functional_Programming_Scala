## Recursion 

*To understand recursion, one must first understand recursion* 

This topic should be familiar to anyone who delved into functional programming, but we would like to go over it once again. 
The core of recursion is that a function must call itself. 
When first encountered, this approach may not seem natural, but with more practice you'll find it harder to go any 
other way. 
Consider the problem of finding a key in a box, but the box may contain other boxes, which may contain other boxes and 
so forth, and the key is in one of the boxes, but you have no idea which one. 
When trying to solve the problem without recursion, you would generally use some kind of loop: 

```scala 3
// We model the content of a box as a set of Items: it can contain other boxes or keys 
sealed trait Item
case class Box(content: Set[Item]) extends Item
case class Key(id: String) extends Item

def lookForKey(box: Box): Option[Key] =
  // create a mutable variable for a pile of items to look through
  var pile = box.content
  while pile.nonEmpty do
    // pick one item of the pile
    val item = pile.head
    item match
      case key: Key => 
        // found the key
        return Some(key)
      case box: Box => 
        // remove the current item out of a pile and add its contents for further inspection
        pile = pile - box ++ box.content
  // no key was found
  None 

@main
def main() =
  val box = Box(Set(Box(Set(Box(Set.empty), Box(Set.empty))), Box(Set(Key(), Box(Set.empty)))))
  println(lookForKey(box))
```

This solution is valid, but it feels icky. 
We need to create a mutable variable to hold the pile of items inside the box. 
Then we need to remember to remove the box under inspection before adding its content.
We also have to remember to return `None` in the end, after the `while` loop: good thing, that the compiler will 
complain, if we forget it. 
But in general, it is just too easy to make a mistake when writing this function.

Instead, we can think what it means to look for a key in a box of boxes and keys. 
We look inside the box, pick one of the items inside, and if it is a box, we just need to go ahead and look for a key 
in it -- in the exact same way we are currently doing. 
It is exactly the situation where a function calls itself. 
Compare the recursive implementation to the non-recursive one: 

```scala 3
def lookForKey(box: Box): Option[Key] =
  def go(item: Any): Option[Key] =
    item match
      case key: Key => Some(key)
      case box: Box =>
        // process every item in the box recursively and pick the first key in the result, if it exists
        box.content.flatMap(go).headOption
  go(box)
```

Here we just go through evey item in the box, apply the recursive function `go` to it, and then select the first key 
in the resulting collection. 
Notice, we no longer have any mutable variables, our program is much more readable and there are fewer ways it can go
wrong. 
One may protest that this code is not optimal, because `flatMap` will go through the whole box even if we find the key
early, and it is a valid concern. 
There are many ways to deal with this kind of inefficiency. 
One would be to use lazy collections or views, and we'll see another one in the one of the following modules, when we'll
speak about early returns. 

If you struggle to think recursively, consider the following two-step approach. 
1. If the given instance of the problem can be solved directly, solve it directly. 
2. Othewise, reduce it to one or more *simpler instances of the same problem*. 

In our example with boxes, if the item at hands is a key, then we've solved the problem and all we need to do is to 
return the key. 
This is the base case of our problem, the smallest instance of it. 
Otherwise, we have *smaller* instances of the same problem: other boxes in which we can try to look for a key.
Once we looked through the boxes recursively, we may be sure that the key, if it exists, has been found.
The only thing which is left after that is some kind of post-processing of the recursive calls' results -- in our case,
getting the first found key with `headOption`. 

The recursion shines when dealing with recursive data structures which are very common in functional programming. 
Lists, trees and other algebraic data structures are where recursion lends itself very naturally. 
Consider the recursive function which computes the sum of the values in binary tree nodes: 

```scala 3
enum Tree:
  case Node(val payload: Int, val left: Tree, val right: Tree)
  case Leaf(val payload: Int)

import Tree.*

def sumTree(tree: Tree): Int =
  tree match
    case Leaf(payload) => payload
    case Node(payload, left, right) =>
      payload + sumTree(left) + sumTree(right)

@main
def main() =
  /**
   *     4
   *    / \
   *   2  5
   *  / \
   * 1  3
   */
  val tree = Node(4, Node(2, Leaf(1), Leaf(3)), Leaf(5))
  println(sumTree(tree))
```

You can notice, that we call `sumTree` recursively every time there is a `Tree` inside a `Tree` node.
The recursion in the data type points us to a *smaller* instance of a problem to solve.
We then sum the values returned from the recursive calls, which produces the final result. 
There are no `Tree`s in a `Leaf`, thus, we know it is the base case, and we can solve the problem right away.
 
### Exercise 

Implement a tiny calculator `eval` and a printer `prefixPrinter` for arithmetic expressions. 
An expression is presented as its abstract syntax tree, where leaves contain numbers, while nodes correspond to the 
binary operators applied to subexpressions. 
For example, the tree `Node(Mult, Node(Plus, Leaf(1), Leaf(3)), Leaf(5))` corresponds to the expression `(1+3)*5`. 
The printer should convert an expression into the prefix form, in which the operator comes first, then the left 
operand followed by the right operand. 
Our tree should be printed as `* + 1 3 5`. 