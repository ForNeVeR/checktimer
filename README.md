![Project icon][icons.checktimer] checktimer [![Status Ventis][status-ventis]][andivionian-status-classifier]
==========

![checktimer screenshot][screenshot]

checktimer is a simple manual time tracking application. It allows the user to
enter the current activity and saves all the activity to a log file.

Run
---

```console
$ sbt run # will read the configuration from ~/checktimer.cfg
$ sbt "run ./file.cfg" # will read the configuration from file.cfg
```

Configure
---------

Here's the configuration file example:

```
database: "/home/user/checktimer.csv" # path to the data storage
```

### Logging
Checktimer uses tinylog 2 logging framework. By default, it will write logs to `<temp folder>/checktimer/` directory, and to the standard output. It is possible to override the logging parameters, see [the logging documentation page][docs.tinylog] for details.

Documentation
-------------
- [Contributor Guide][docs.contributing]
- [License (MIT)][docs.license]

[andivionian-status-classifier]: https://github.com/ForNeVeR/andivionian-status-classifier#status-ventis-
[docs.contributing]: CONTRIBUTING.md
[docs.license]: LICENSE.md
[docs.tinylog]: https://tinylog.org/v2/configuration/
[icons.checktimer]: src/main/resources/icons/checktimer.svg
[screenshot]: docs/screenshot.png
[status-ventis]: https://img.shields.io/badge/status-ventis-yellow.svg
