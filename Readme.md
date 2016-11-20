checktimer [![Status Ventis][status-ventis]][andivionian-status-classifier] [![Appveyor status][badge-appveyor]][build-appveyor] [![Travis status][badge-travis]][build-travis] 
==========

![checktimer screenshot][screenshot]

checktimer is a simple manual time tracking application. It allows the user to
enter the current activity and saves all the activity to a log file. 

Build
-----

```console
$ sbt compile
```

Run
---

```console
$ sbt run # will write results to ~/checktimer.csv
$ sbt "run file.csv" # will write results to file.csv
```

[andivionian-status-classifier]: https://github.com/ForNeVeR/andivionian-status-classifier#status-ventis-
[build-appveyor]: https://ci.appveyor.com/project/ForNeVeR/checktimer/branch/develop
[build-travis]: https://travis-ci.org/ForNeVeR/checktimer

[badge-appveyor]: https://ci.appveyor.com/api/projects/status/gn9obicxe8msp3h0/branch/develop?svg=true
[badge-travis]: https://travis-ci.org/ForNeVeR/checktimer.svg?branch=develop
[status-ventis]: https://img.shields.io/badge/status-ventis-yellow.svg

[screenshot]: docs/screenshot.png
