# Introduction

In this "documentation as Book", we will discover how to develop a platforme 2D fgame, stating from a simple java class, and letting  the code evolve from this simple state to a more complexe one, cpomposed of objectsn, services system and pattern, to produce a game.

The context of this project consists in experimenting a new way of documenting an IT project, the Game here is for the fun side of the project.

Consider that any IT project needs docuemntation, and tha fact that maintiang such doc is always (no, or often so) a painful activity. 

Here, we are going to try and change the way of producing things: let's write a Story Book of the Project.

Yes, you well read the proposition: write a book.

Bout not only, add Test and code ;)

> Let's write the documentation of our IT project like we would write a Book, chapter after chapter, getting personas(objects) and contexts(requirement) becoming more and more detailed and complex.

As we all know that starting with a project, we never exactly know where the project end, and why we finaly end like this, imagine 5 years after, WHY this decision has been taken, and who drive this implementation to this point ?

So my proposed solution is to write, chapter after chpater, the Story of the project, describing the choice, explaining the solution, but never delete a previous part of the stiory to keep the full story, and not only the final picture.

Because, during development of any solution, the engaged design evolve taking account of new needs or requirements, and also with mandatory code refactoring.

In the first chapter you will notice how the code was reorganize while we add new features and classes, to make it undestandable and readable by common human developers.

## Maven project

To organise a little bit the project, we will rely on tha basis of a Maven project structure, with some enhancement to be able to publish our doc to the github pages platform, and build properly the Game.

```text
fromClassToGame
|_ docs
|_ src
|  |_ main
|  | |_ java
|  | |_ resources
|  |_ test
|  | |_ java
|  | |_ resources
|  |    |_ features
|_ README.md
|_ HOW-TO.md
|_ pom.xml
|_ .gitignore
```

The standard `src/main`,`src/test` will contain code ans tests, while the `docs` directory will contain the `*.md` file of the story book.
Note that the  `src/test/resources/features` will host the `*.feature` files describing the use cases with the gherkin language.


## But who is Gherkin ?

*TODO*


Let's go !

Mc G.
