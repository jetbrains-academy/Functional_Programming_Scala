When all code is in our control, it's easy to avoid throwing exceptions by using `Option` or `Either`. 
However, we often interact with Java libraries where exceptions are omnipresent, for example, in the context of working with databases, files, or internet services.
One option to bridge this gap is by using `try/catch` and converting exception code into monadic one: 

```scala 3
def foo(data: Data): Either[Throwable, Result] =
  try {
    val res: Result = javaLib.getSomethingOrThrowException(data)
    Right(res)
  } catch {
    case NonFatal(err) => Left(err)
  }
```

This case is so common that Scala provides a special monad `Try[A]`.
`Try[A]` functions as a version of `Either[Throwable, A]` specially designed to handle failures coming from JVM.
You can think of this as a necessary evil: in the ideal world, there wouldn't be any exceptions, but since there is no such thing as the ideal world and exceptions are everywhere, we have `Try` to bridge the gap.
Using `Try` simplifies the conversion significantly: 

```scala 3
def foo(data: Data): Try[Result] =
  Try(javaLib.getSomethingOrThrowException(data))
```

`Try` comes with two subclasses `Success[A]` and `Failure`, which are like the `Right[A]` and `Left[Throwable]` of `Either[Thowable, A]`.
The former wraps the result of the successful computation, while the latter signals failure by wrapping the exception thrown. 
Since `Try` is a monad, you can use `flatMap` to pipeline functions, and whenever any of them throws an exception, the computation is aborted. 

Sometimes, an exception is not fatal and you know how to recover from it. 
Here, you can use the `recover` or `recoverWith`.
The `recover` method takes a partial function that for some exceptions produces a value which is then wrapped in `Success`, while with all other exceptions result in `Failure`. 
A more flexible treatment is possible with the `recoverWith` method: its argument is a function that can decide on the appropriate way to react to particular errors. 


```scala 3
val t: Try[Result] =
  Try(javaLib.getSomethingOrThrowException(data))
  
t.recover {
  case ex: IOException => defaultResult
}
  
t.recoverWith {
  case ex: IOException =>
    if (ignoreErrors) Success(defaultResult)
    else Failure(ex)
}
```

To sum up, we strongly recommend that you use `Try` instead of `try/catch`.


