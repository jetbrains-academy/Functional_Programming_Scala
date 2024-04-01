## Breaking Boundaries

Similarly to Java and other popular languages, Scala provides a way to break out of a loop. 
Since Scala 3.3, it's achieved with a composition of boundaries and breaks which provides a cleaner alternative to 
non-local returns. 
With this feature, a computational context is established with `boundary:`, and `break` returns a value from within the 
enclosing boundary.
Check out the [implementation](https://github.com/scala/scala3/blob/3.3.0/library/src/scala/util/boundary.scala) 
if you want to know how it works under the hood.
One important thing is that it ensures that the users never call `break` without an enclosing `boundary` thus making 
the code much safer. 

The following snippet showcases the use of boundary/break in its simplest form. 
If our conversion and validation work out then `break(Some(userData))` jumps out of the loop labeled with `boundary:`. 
Since it's the end of the method, it immediately returns `Some(userData)`. 

```scala 3
  def findFirstValidUser10(userIds: Seq[UserId]): Option[UserData] =
    boundary:
      for userId <- userIds do
        safeComplexConversion(userId).foreach { userData =>
          if (complexValidation(userData)) break(Some(userData))
        }
      None
```

Sometimes there are multiple boundaries, in this case one can add labels to `break` calls. 
This is especially important when there are embedded loops.
One example of using labels can be found [here](https://gist.github.com/bishabosha/95880882ee9ba6c53681d21c93d24a97).
