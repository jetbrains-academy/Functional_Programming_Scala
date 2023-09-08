# Case Class
In Scala, a case class is a special kind of class that comes with some useful default behavior and methods, which are
beneficial for modeling immutable data.
The Scala compiler will put some limitations on it, but it will also automatically enrich it with features that 
otherwise we would have to code ourselves:

1. Fields of a case class are immutable by default. 
   You need to explicitly mark them as `var` if you want to change that, but this practice is frowned upon as highly 
   un-idiomatic in Scala. 
   Instances of case classes should serve as immutable data structures. 
   Changing that makes code less intuitive and readable.
2. A case class provides a default constructor with public, read-only parameters, making instantiation of a case class 
   less boiler-plate'y.
3. Scala automatically defines some useful methods for case classes, such as `toString`, `hashCode`, and `equals`. 
   The `toString` method gives a string representation of the object, 
   `hashCode` is used in hashing collections like `HashSet` and `HashMap`, 
   and `equals` checks structural equality instead of reference equality, 
   i.e. it will check the equality of the case class respective fields, 
   instead of just checking if the two references point to the same object.
4. Case classes come with the `copy` method that can be used to create a copy of the case class instance: 
   exactly the same as the original, or with some parameters modified 
   (the signature of the `copy` method follows the default constructor).
5. Scala automatically creates a companion object for the case class. 
   It contains factory `apply` and `unapply` methods. 
   The `apply` method matches the default constructor and lets you create instances of the class without using the `new` keyword. 
   The `unapply` method is used in pattern matching.
6. Case classes can be conveniently used in pattern matching as they automatically have the default `unapply` method, 
   which lets you destructure the case class instance.
7. On top of that, by convention, case classes are not extended. 
   They can extend traits and other classes themselves, but they shouldn't be used as superclasses for other classes. 
   Technically though, extending case classes is not forbidden by default. 
   If you want to ensure that a case class will not be extended, mark it with the `final` keyword.

You should already be familiar with some of the features, as we used them in the previous module. 
The difference is that here we want you to focus on different aspects that you'll see in examples and exercises.

Below is the simple example of a case class which models cats. 
We create a `Cat` instance called `myCat` and then pattern match it against `Cat` to get access to its name and color.  

```scala 3
case class Cat(name: String, color: String)

val myCat = Cat("Whiskers", "white")
myCat match {
  case Cat(name, color) => println(s"I have a $color cat named $name.")
}
```

## Exercise 

Create a case class which represents a dog.
Each dog should have a name, a breed, and a favorite toy.
Model these features as Strings for now.
Use pattern matching to introduce the dog. 