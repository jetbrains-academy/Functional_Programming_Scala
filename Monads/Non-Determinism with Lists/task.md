Monads can express different computational effects, and failure is just one of them. 
Another is non-determinism, which allows a program to have multiple possible results. 
One way to encapsulate different outcomes is by using a `List`. 

Consider a program that computes a factor of a number. 
For non-prime numbers, there is at least one factor that is neither 1 nor the number itself, and many numbers have multiple factors.
The question is: which of the factors should we return?
Of course, we could return a random factor, but a more functional way is to return all of them, packed in a collection such as a `List`.
In this case, the caller can decide on the appropriate treatment.

```scala
// The non-deterministic function to compute all factors of a number 
def factors(n: Int): List[Int] = {
  (1 to n).filter(n % _ == 0).toList
}

// factors(42) == List(1, 2, 3, 6, 7, 14, 21, 42)
```

Let's now discuss the List monad. 
The `identity` method simply creates a singleton list with its argument inside, indicating that the computation has finished with only one value.
`flatMap` applies the monadic action to each element in a list and then concatenates the results. 
If we run `factors(4).flatMap(factors)`, we get `List(1,2,4).flatMap(factors)`, which concatenates `List(1)`, `List(1,2)`, and `List(1,2,4)` for the final result `List(1,1,2,1,2,4)`.

`List` is not the only collection that can describe non-determinism; another is `Set`. 
The difference between the two is that the latter doesn't care about repeats, while the former retains all of them. 
You can choose the suitable collection based on the problem at hand. 
For `factors`, it may make sense to use `Set` because we only care about unique factors. 

```scala
// The non-deterministic function to compute all factors of a number 
def factors(n: Int): Set[Int] = {
  (1 to n).filter(n % _ == 0).toSet
}

// factors(42) == Set(1, 2, 3, 6, 7, 14, 21, 42)
// factors(6).flatMap(factors) == Set(1, 2, 4) 
```

## Exercise

To make our model of users a little more realistic, we should take into an account that a user may have multiple children. 
This makes our `getGrandchild` function non-deterministic. 
Let's reflect that in the names, types, and implementations. 

Now, function `getGrandchildren` aggregates all grandchildren of a particular user.
Since each person is unique, we use `Set`. 
However, there might be some grandchildren whose ages are the same, and we don't want to lose this information. 
Because of that, `List` is used as the return type of the `getGrandchildrenAges` function. 
Note that there is no need to explicitly report errors any longer because an empty collection signifies the failure on its own. 
