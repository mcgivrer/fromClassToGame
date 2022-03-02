# Introduction

In this "documentation as Book", we will discover how to develop a platform 2D game, stating from a simple java class,
and letting the code evolve from this simple state to a more complexe one, composed of objects, services system and
pattern, to produce a game.

The context of this project consists in experimenting a new way of documenting an IT project, the Game here is for the
fun side of the project.

Consider that any IT project needs documentation, and tha fact that keeping up to date such doc is always (no, or often
so) a painful activity.

Here, we are going to try and change the way of producing things: let's write a Story Book of the Project.

Yes, you will read the proposition: write a book.

But not only, add Test and code ;)

> **INFO**<br/>Let's write the documentation of our IT project like we would write a Book, chapter after chapter, getting personas(objects) and contexts(requirement) becoming more and more detailed and complex.

As we all know that starting with a project, we never exactly know where the project end, and why we finally end like
this, imagine 5 years after, WHY this decision has been taken, and who drive this implementation to this point ?

So my proposed solution is to write, chapter after chapter, the Story of the project, describing the choice, explaining
the solution, but never delete a previous part of the story to keep the full story, and not only the final picture.

Because, during development of any solution, the engaged design evolve taking account of new needs or requirements, and
also with mandatory code refactoring.

In the first chapter you will notice how the code was reorganized while we add new features and classes, to make it
understandable and readable by common human developers.

## Maven project

To organise a litlle bit the project, we will rely on tha basis of a Maven project structure, with some enhancement to
be able to publish our doc to the github pages platform, and build properly the Game.

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
|_ LICENSE
|_ HOW-TO.md
|_ pom.xml
|_ .gitignore
|_ run.sh
|_ run.bat
```

The standard `src/main`,`src/test` will contain code ans tests, while the `docs` directory will contain the `*.md` file
of the story book. Note that the  `src/test/resources/features` will host the `*.feature` files describing the use cases
with the gherkin language.

## But who is Gherkin ?

Do you know cucumber ? the Gherkin is the little version of cucumber ! (
see "Pickled cucumber or Gherkin" on [wikipedia](https://en.wikipedia.org/wiki/Pickled_cucumber#Gherkin))

![The Gherkin, an english word for Pickle](https://upload.wikimedia.org/wikipedia/commons/b/bb/Pickle.jpg "The english Pickel: the Gherkin !")

Ok, no, in our context, its a bit
different, [Gherkin](https://cucumber.io/docs/guides/overview/#what-is-gherkin "see and discover the real gherkin cucumber langage")
is a functional description language to explain the user requirement in common english words.

Based on simple structure, each use case is described by listing, the who, the what and the when.

Nothing better than an example, let's dive in the file `Game_hsa_scene_with_objetcs.feature` :

```gherkin
Feature: The game has scene with different types of GameObject.

  The Game class is started normally by initializing with arguments or default values.

  Scenario Outline: 01 - The Game has one GameObject
    Given the Game is instantiated
    And the Game is running
    And I activate the scene "test1"
    And I add a GameObject named <name> at (<x>,<y>)
    And the <name> size is <w> x <h>
    Then the Game has <i> GameObject at window center.
    Examples:
      | name     | x     | y     | w  | h  | i |
      | "player" | 160.0 | 100.0 | 16 | 16 | 1 |
```

Here is a first extract of the `GameObject`'s scene management requirement. We clearly explain what the Scene object
intends to do with GameObject. It is very technical on this sample text because we build a game and its framework.

Looking at [8. Scene manager](./08-scene-manager.md) you can see in this book's chapter described the corresponding
implementation to serve the `GameObject`'s directly from a `Scene` object.

## Ready ? Go !

We all have understood what we are doing now and there. No ? you, young guy, you have any question ? NO ? OK, so let's
go, and HAVE FUN !

Mc G.
