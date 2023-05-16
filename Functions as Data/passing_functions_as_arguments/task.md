### Passing functions as arguments

We can pass a named function as an argument to another function just like we would pass any other value. This is useful, for example, when we want to manipulate data in a collection. There are many methods in Scala collections classes that operate by taking a function as an argument, and applying it in some way to each element of the collection. In the previous chapter we saw how we can use the map method on a sequence of numbers to double them. Now let's try something different. Imagine that you have a bag of cats of different colors, and you want to get a bag of only black cats.

```scala
// We model colors as enums.
enum Color:
 case Black
 case White
 case Ginger

// We model a cat as a class. In this example we are interested only in color of the cat.
class Cat(val color: Color)

// We create our bag (a set) of cats. Each cat has a different color.
val bagofCats = new Set[Cat](Cat(Color.Black), Cat(Color.White), Cat(Color.Ginger))

// We use the `filter` method to create a new bag of black cats.  
val bagOfBlackCats = bagOfCats.filter(cat => cat.color == Color.Black)
```

In Scala 3, we can use enums to define colors. Then we create a class `Cat` which has a value for the color of the cat, and we make a "bag" of cats, that is, a set with three cats: one black, one white, and one ginger. Finally, we use the `filter` method and give to it, as an argument, an anonymous function that  takes an argument of the class `Cat` and will return `true` if the color of the cat is black. The method `filter` will apply this function to each cat in the original set, and will create a new set with only those cats for which the function returns `true`.

But our function that checks if the cat is black doesn't have to be anonymous. The `filter method will work with a named function just as well.

```scala
def isCatBlack(cat: Cat): Boolean = cat.color == Color.Black
val bagOfBlackCats = bagOfCats.filter(isCatBlack)
```

Passing a function as an argument to a method (or another function) can be useful when we want to apply the same logic to all elements in a collection, in a stream, or whatever the method works on. This way we can create more reusable and modular code.

So far you saw examples of how it is done with `map` and `filter` - two methods from Scala collections. In the next chapters, we will discuss other methods which can be called in a similar fashion but which perform different operations.