# Smart Constructors and the `apply` Method

In Scala,  apply  is a special method that can be invoked without specifying its name.
```scala 3
class Cat:
  def apply(): String = "meow"
```

```scala 3
val cat = Cat()
cat() // returns "meow"
```

And technically this is it – you can implement apply any way you want for any reason you want. It is only by convention that the most popular way to use apply is as a smart constructor. But it's a very important convention and we would like to advise you to follow it. There are a few other ways you can use apply – for example, the Scala collections library uses it often to retrieve data from a collection  which superficially looks as if Scala traded the square brackets of more traditional languages for parentheses:

```scala 3
val entry1 = listOfEntries(5) // listOfEntries: List[Entry]
val entry2 = listOfEntries.apply(5) // this is the same as above
``` 

and this use is popular enough that people understand it when they  see it, but if you try to use it for something much different, you may make your code harder to read to other Scala developers who would expect that a pair of parentheses after a name indicate a call to a smart constructor.
A smart constructor is a design pattern often used in functional  programming languages. Its main purpose is to encapsulate the creation logic of an object, thus enforcing some constraints or rules whenever an instance of a class is created. For example, you can use it to ensure that the object is always created in a valid state.
This pattern can be especially useful in situations where:
The construction of an object is complex and needs to be abstracted away.
You want to control how objects are created and ensure they're always in a valid state.
You need to enforce a specific protocol of object creation, such as caching objects, singleton objects, or creating objects through a factory.
The idiomatic way to use apply as a smart constructor is to put it in the companion object of a class and call it with the name of the class and a pair of parentheses. For example, let's consider again a class Cat with a companion object that has an apply method:

```scala 3
class Cat private (val name: String, val age: Int)

object Cat:
def apply(name: String, age: Int): Cat =
if (age < 0) new Cat(name, 0)
else new Cat(name, age)

val fluffy = Cat("Fluffy", -5) // the age of Fluffy is set to 0, not -5
```

The Cat class has a primary constructor which takes a String and an Int, and sets the name and the age of the new cat with them. But on top of that, we create a companion object and define the apply method in it. This way, when we later call Cat("fluffy", -5), it's the apply method that is being called, not the primary constructor. In the apply method, we check the given age of the cat, and if it's smaller than zero, we create an instance of a cat with the age set to zero, instead of the given age.
Please also notice how we differentiate between calling the primary constructor and the apply method. When we call Cat("Fluffy", -5), the Scala 3 compiler checks if a matching apply method exists. If yes, that apply method will be called. If not, Scala 3 will call the primary constructor (again, if the signature matches). This makes the apply method transparent for the user. But if we need to call the primary constructor explicitly, by-passing the apply method, we can use the new keyword for that and write new Cat(name age). We use this trick here in the example to avoid endless recursion – if we didn't, calling Cat(name, age) or Cat(name, 0) would again call the apply method.
You may ask now, what will prevent the user from writing `new Cat("Fluffy", -5)` and by-passing our apply method? Notice that in the first line of the above example, where we define class Cat, there is the private keyword between the name of the class and the parentheses. The private keyword at this place means that the primary constructor of the class Cat can be called only by methods of the class or its companion object. This way, we can still use new Cat(name,age) in the apply method, because the apply method is in the companion object, but the user can't do it from outside the class.