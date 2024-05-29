## The Problem

It is often the case that we do not need to go through all the elements in a collection to solve a specific problem.
For example, in the Recursion chapter of the previous module, we saw a function to search for a key in a box.
It was enough to find a single key, and there was no point in continuing the search in the box after one had been found.

The problem might get trickier as data becomes more complex.
Consider an application designed to track your team members, detailing the projects they worked on and the
specific days they were involved.
Then, the team manager could use the application to run complicated queries such as the following:
* Find an instance when the team worked more person-hours than X in a day.
* Find an example of a bug that took longer than Y days to fix.

It's common to run some kind of conversion on an element of the original data collection into a derivative entry that
better describes the problem domain.
Then, this converted entry is validated with a predicate to decide whether it's a suitable example.
Both the conversion and the verification may be expensive, which makes a naive implementation, like our
key search example, inefficient.
In languages such as Java, you can use `return` to stop the exploration of the collection once you've found your answer.
The implementation might look something like this:

```java
Bar complexConversion(Foo foo) {
  ...
}
 
bool complexValidation(Bar bar) {
  ...
}
 
Bar findFirstValidBar(Collection<Foo> foos) {
  for(Foo foo : foos) {
    Bar bar = complexConversion(foo);
    if (complexValidation(bar)) return bar;
  }
  return null;
}
```

Here, we enumerate the elements of the collection `foos` sequentially, running `complexConversion` on them, followed by
`complexValidation`.
If we find the element for which `complexValidation(bar)` succeeds, the converted entry is immediately returned,
and the enumeration is stopped.
If there was no such element, then `null` is returned after the entire collection has been explored without success.

How do we apply this pattern in Scala?
It's tempting to translate this code line-by-line directly into Scala:

```scala 3
def complexConversion(foo: Foo): Bar = ...
def complexValidation(bar: Bar): Boolean = ...
 
def findFirstValidBar(seq: Seq[Foo]): Option[Bar] = {
  for (foo <- seq) {
    val bar = complexConversion(foo)
    if (complexValidation(bar)) return Some(bar)
  }
  None
}
```

We've replaced `null` with the more appropriate `None`, but otherwise, the code remains the same.  
However, this is not good Scala code, where the use of `return` is not idiomatic.
Since every block of code in Scala is an expression, the last expression within the block is what is returned.   
You can write `x` instead of `return x` for the last expression, and it would have the same semantics.
Once `return` is used in the middle of a block, the programmer can no longer rely on the last statement as the one
returning the result from the block.
This makes the code less readable, makes it harder to inline code, and ruins referential transparency.
Thus, using `return` is considered a code smell and should be avoided.

In this module, we'll explore more idiomatic ways to do early returns in Scala. 








