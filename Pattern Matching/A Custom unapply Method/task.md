# A Custom unapply Method 

But you can also implement a custom unapply method, both for a regular class that lacks an automatically generated unapply, as well as an additional destructuring way for a case class. Here's an example of a custom unapply method for the Cat case class we defined in the previous chapter:

```scala 3
object Cat:
def unapply(cat: Cat): (String, Int, String) =
val ageDescription = if (cat.age < 2) "kitten" else "adult"
(cat.name, cat.age, ageDescription)
```

Here we've defined an unapply that not only returns the name and age of the Cat, but also a description of the cat's age ("kitten" or "adult"). Now we can use this custom unapply method in pattern matching:

```scala 3val mittens = Cat("Mittens", 1)
mittens match
case Cat(name, age, description) =>
println(s"$name is a $description.")
// This will print out "Mittens is a kitten."
``` 

Take note that our unapply works in every situation - no matter the age of the cat, deconstructing it will produce a valid result. This is called the Universal Apply Method and it is a new feature in Scala 3. Before, in Scala 2, every unapply had to return an Option of the collection of fields produced during destructuring. That Option would be Some(...) if destructuring succeeded, or None if destructuring failed. When can it fail? Imagine that we develop a system for handling driver's licenses. In Germany, if you want to drive a regular car, you have to be at least 18 years old. But if you just want a license for a small motorcycle, you can get it when you're 16, and for a moped it's enough if you're 15. So, we will create an enum for VehicleType and a class Applicant which will consists of the name of the person who applies for a driver's license, their age, and the vehicle type they want to drive:

```scala 3
enum VehicleType:
case Car
case SmallMotorcycle
case Moped

class Applicant(name: String, age: Int, vehicleType: VehicleType)
```

Now, somewhere in the code we have a sequence of all applicants and we want to get the names of those who are eligible for a driver's license given their age and the vehicle type they apply for. Just as in the previous chapter when we were looking for cats older than one, we could define an Universal Apply Method and use guards inside pattern matching, only this time instead of foreach we will use collect:

```scala 3
object Applicant:
def unapply(applicant: Applicant): (String, Int, VehicleType) =
(applicant.name, applicant.age, applicant.vehicleType)

val applicants: Seq[Applicant] = ???
val names = applicants.collect {
case Applicant(name, age, VehicleType.Car) if age >= 18 => name
case Applicant(name, age, VehicleType.SmallMotorcycle) if age >= 16 => name
case Applicant(name, age, VehicleType.Moped) if age >= 15 => name
}
```

But since in this example we define our own unapply method anyway, we might as well code in it logic for checking if the applicant is eligible for a driver's license, and return an Option of their name or None:

```scala 3
object Applicant:
def unapply(applicant: Applicant): Option[String] = applicant.vehicleType match
case VehicleType.Car if age >= 18 => Some(applicant.name)
case VehicleType.SmallMotorcycle if age >= 16 => Some(applicant.name)
case VehicleType.Moped if age >= 15 => Some(applicant.name)
case _ => None

val applicants: Seq[Applicant] = ???
val names = applicants.collect {
case Applicant(name) => name
}
``` 

As you can see, we moved the logic from the collect method to the unapply method. The code as a whole hasn't become shorter or more readable in any significant way, but depending on a situation, the possibility of moving logic that checks if an entity is valid for something to a separate place in your codebase, might prove valuable.
