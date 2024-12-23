

In this exercise, you'll apply Cats Effect to implement a virtual dog adoption center. 
Create a command-line application that allows one to view the available dogs, adopt or surrender a dog. 
This exercise demonstrates a referentially transparent way to handle side effects such as console input/output and state management. 

We will be working with a case class that models a dog from one of the previous lessons. 
It describes a dog by their identifier, name, breed, and favorite toy: see `Dog.scala`. 
The information about the residents of our shelter is stored in a CSV format in `src/main/resources/adoptionCenter.csv`.

As a visitor of the center, you can do one of four actions: 

1. View all available dogs
2. Adopt a dog
3. Surrender a dog
4. Exit 

The application should prompt the user to pick one of the options until they selected Exit. 
The first option should print out all available dogs read from the file. 
The second option prompts the user to select a dog to adopt by their identifier. 
Finally, the third option prompts the user to input the name, breed and favorite toy of a dog they wish to surrender, assigns it a new unique identifier and adds the dog to the shelter. 

Since we don't want to write into a file after every operation, the application should operate with a list of dogs in memory and only commit the result of all adoptions and surrenders on exit.
This is why the return type of `adoptDog` and `surrenderDog` is `IO[List[Dog]]`. 
Since viewing dogs and exiting doesn't modify the state of our shelter, the output type of corresponding functions is `IO[Unit]`. 
Make sure that a dog cannot be adopted twice, and that a surrendered dog gets a unique identifier.