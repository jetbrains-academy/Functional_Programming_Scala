## Scala Collections instead of Imperative Loops

In the imperative programming style, you will often find the following pattern: a variable is set initially to some 
default value, an empty collection, an empty string, a zero, or null. 
Then, some initialization code is run in a loop to create the proper value step-by-step. 
After this process, the value assigned to the variable does not change anymore — or if it does, 
it’s done in a way that could be replaced by resetting the variable to its default value and rerunning the initialization. 
But the possibility of modifying it stays, even though it is unnecessary. 
For the whole life of the program, it hangs like a  loose end of an electric cable, inviting everyone to touch it.

Functional Programming allows us to build useful values without the need for initial default values and temporary mutability. 
The data structure, even a very complex one, can be computed through extensive use of a higher-order function and then 
assigned to a constant,  preventing future modifications. 
If we need an updated version, we can create a new data structure instead of modifying the old one.

Scala provides a rich library of collections — `Array`, `List`, `Vector`, `Set`, `Map`, and many others — 
and methods that allow us to manipulate those collections and elements inside them. 
You already learned about some of those methods in the first chapter. 
In this chapter, you will learn more about how to avoid mutability and use immutability to write safer and sometimes 
even more performant code.
