## Berliner Pattern 

In functional programming, data does not need to be mutable too often. 
Theoretically, it is possible to avoid mutability at all, especially in such programming languages as Haskell. 
However, it may feel awkward and unnecessarily complicated to many coders. 
Thankfully, you can get the best of both worlds in the languages which combine functional with imperative programming.   
In particular, Scala was specifically designed with this connection in mind. 

The Berliner Pattern is an architectural pattern introduced by Bill Venners and Frank Sommers at Scala Days 2018 in Berlin.
Its goal is to restrict mutability to only those parts of a program for which it is inevitable.
The application can be thought of as split into three layers: 

* The external layer which has to deal with the external world. 
  It allows the application to communicate with other programs, services, or the operating system.
  This layer is practically impossible to implement in a purely functional way. 
  Good news, there is no need to do that. 
* The internal layer where we connect to databases or write into files. 
  This part of the application is usually performance-critical, so it's only natural to use mutable data structures here. 
* The middle layer which connect the later two. 
  This is where our business logic resides and where functional programming shines. 
  
Pushing mutability to the thin inner and outer layers has its benefits. 
First of all, the more we restrict the data, the more future-proof our code is. 
We not only give the compiler more information, but also signal the future developers that some data is not to be modified.

Second of all, it simplifies writing concurrent code. 
When multiple threads can modify the same data, we may quickly end up in an invalid state, and it may be complicated to debug. 
There is no need to resort to mutexes, monitors, or other patterns when there is no way to actually modify data. 

Finally, the common pattern in imperative programming with mutable data is to first assign some default value to a variable,
and then modify it. 
For example, you start with an empty collection and then populate it with some specific values. 
However, default values are evil. 
Coders often forget to change them into something meaningful which becomes the source of many bugs, such as 
the billion-dollar mistake caused by using `null`. 

We encourage you to familiarize yourself with the pattern by watching the [original video](https://www.youtube.com/watch?v=DhNw60hxCeY).

### Exercise

We provide you with a sample implementation of the application which deals with creating, modifying and deleting users in a database. 
We mock the database and http layers, and your task would be to implement methods processing requests following the Berliner pattern.

Start by implementing the `onNewUser` and `onVerification` methods in `BerlinerPatternTask.scala`.
We provide the implementations for the database and the client for these methods, so you could familiarize yourself
with the application.
Execute the `run` script in `HttpClient.scala` to make sure your implementation works correctly.

Then implement the functionality related with changing the password as well as removing the users. 
You will have to implement all layers for these methods, so check out `Database.scala` and `HttpClient.scala`. 
Don't forget to uncomment the last several lines in the `run` script for this task. 
