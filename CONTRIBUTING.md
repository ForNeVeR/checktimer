Contributor Guide
=================
This document describes how to build and test the project.

Prerequisites
-------------
This project requires the following software on the developer computer:
- JDK version 21 (there are various distributions among operating systems; if you are not sure what distribution to choose, our CI uses [Amazon Corretto 21][jdk.corretto.21]),
- [SBT][sbt] (so that the `sbt` command works in the shell).

Build
-----
To build the project, execute the following shell command:
```console
$ sbt compile
```

Run
---
To run the project in developer mode, execute one of the following shell commands:
```console
$ sbt run # will read the configuration from ~/checktimer.cfg
$ sbt "run ./file.cfg" # will read the configuration from file.cfg
```

Test
----
To run the tests, execute the following shell command:
```console
$ sbt test
```

Distribute
----------
To build a distributable package, execute the following shell command:
```console
$ sbt universal:packageBin
```

This will create a ZIP archive at path `target/universal/checktimer-<version>.zip`. The archive contains the application and all the required dependencies.

To run the package from the distribution, run `checktimer-<version>/bin/checktimer<.bat>` script.

[jdk.corretto.21]: https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html
[sbt]: https://www.scala-sbt.org/
