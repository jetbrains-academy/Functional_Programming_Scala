In case of any monad, be it `Option`, `Either`, `Try`, or any other, it's possible to chain multiple functions together with `flatMap`.
We've seen many examples where a successfully computed result is passed straight to the next function: `foo(a).flatMap(bar).flatMap(baz)`.
In many real-world situations, there is some additional logic that is executed in between calls.
Consider the following realistic example:

```scala 3
val res = client.getTeamMembers(teamId).flatMap { members =>
  storage.getUserData(members.map(_.userId)).flatMap { users =>
    log(s”members: $members, users: $users”)
    system.getPriorityLevels(teamId).flatMap {
      case levels if levels.size > 1 =>
        doSomeStuffOrFail(members, users, levels)
      case _ =>
        doSomeOtherStuffOrFail(members, users)
    }
  }
}
```

It doesn't look pretty, there is a new nesting level for every call, and it's rather complicated to untangle the mess to understand what is happening.  
Thankfully, Scala provides syntactic sugar called *for-comprehensions* reminiscent of the do-notation in Haskell.
The same code can be written more succinctly using `for/yield`:

```scala 3
val res = for {
  members <- client.getTeamMembers(teamId)
  users   <- storage.getUserData(members.map(_.userId))
  _       =  log(s"members: $members, users: $users")
  levels  <- system.getPriorityLevels(teamId)
} yield
  if (levels.size > 1) 
    doSomeStuffOrFail(members, users, levels)
  else
    doSomeOtherStuffOrFail(members, users)
```

Each line with a left arrow corresponds to a `flatMap` call, where the variable name to the left of the arrow represents the name of the variable in the lambda function.
We start by binding the successful results of retrieving team members with `members`, then get user data based on the members' ids and bind it with `users`.
Note that the first line in a for-comprehension must contain the left arrow.  
This is how Scala compiler understands it is a monadic action, and what type it has.

After that, a message is logged and priority levels are fetched.
Note that we don't use the arrow to the left of the `log` function, because it's a regular function and not a monadic operation which is not chained with `flatMap` in the original piece of code.
We also don't care about the value returned by `log` and because of that use the underscore to the left of the equal sign. 
After all this is done, the `yield` block computes the final values to be returned.
If any line fails, the computation is aborted and the whole comprehension results in a failure. 

### Exercise 

Use for-comprehensions to implement `getGrandchild` and `getGrandchildAge` from the previous exercise. 




