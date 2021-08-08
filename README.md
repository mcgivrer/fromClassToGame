# fromClassToGame

[![Java CI with Maven](https://github.com/mcgivrer/fromClassToGame/actions/workflows/maven.yml/badge.svg)](https://github.com/mcgivrer/fromClassToGame/actions/workflows/maven.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/0b6d37e6859d48e99c0493c08efa4d63)](https://www.codacy.com/gh/mcgivrer/fromClassToGame/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mcgivrer/fromClassToGame&amp;utm_campaign=Badge_Grade)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fmcgivrer%2FfromClassToGame.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fmcgivrer%2FfromClassToGame?ref=badge_shield)
[![Known Vulnerabilities](https://snyk.io//test/github/mcgivrer/fromClassToGame/badge.svg?targetFile=pom.xml)](https://snyk.io//test/github/mcgivrer/fromClassToGame?targetFile=pom.xml)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/0b6d37e6859d48e99c0493c08efa4d63)](https://www.codacy.com/gh/mcgivrer/fromClassToGame/dashboard?utm_source=github.com&utm_medium=referral&utm_content=mcgivrer/fromClassToGame&utm_campaign=Badge_Coverage)
[![Cucumber Reports](https://messages.cucumber.io/api/report-collections/dce051b0-e607-40d6-af37-8bf5b67deb97/badge)](https://reports.cucumber.io/report-collections/dce051b0-e607-40d6-af37-8bf5b67deb97 "See the latest Cucumber Reports")

## Description

This project is a journey from a Class to a Game. First commits will set the basis, and progressively transform, chapter
after chapter, the initial class into a playable game.

During the chapters from the `docs/` you will transform the
simple [`Game`](src/main/java/fr/snapgames/fromclasstogame/core/Game.java "Go and see the Game class") class from
beginning to a package structured framework with some useful features to support object management and display, and just
now how to organize the code in a project, using a bit of TDD to develop and refactor

## build

To build the project, the good maven command is :

```bash
mvn clean install
```

## test

To execute test verification, using a bunch of [cucumber](https://cucumber.io/ "visit the official site") tests :
```bash
mvn test
```

## run

And finally, to run such a beautiful piece of code :

```bash
mvn exec:java
```

You will get the current display:

![screenshot](docs/images/../../src/docs/images/capture-01.png "a screenshot of the current sample demo")
