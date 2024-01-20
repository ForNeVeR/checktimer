![Project icon][icons.checktimer] checktimer [![Status Ventis][status-ventis]][andivionian-status-classifier]
==========

![checktimer screenshot][screenshot]

checktimer is a simple manual time tracking application. It allows the user to
enter the current activity and saves all the activity to a log file.

Prerequisites
-------------
To run checktimer, install Java 17 or newer.

Run
---
1. Download the package for your operating system from the [Releases][releases] page.
2. Unpack the archive.
3. Start the application using `checktimer-<version>/bin/checktimer` or `checktimer.bat` script.

The only command line argument is path to the configuration file (see below). If no argument is provided, the application will try to read the configuration from `~/checktimer.cfg` file, where `~` is a notation for your user's home directory (`USERPROFILE` on Windows, `HOME` on others).

For instructions on how to build and run the application from sources, see the [Contributor Guide][docs.contributing].

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
- [Maintainer Guide][docs.maintainer-guide]
- [License (MIT)][docs.license]

[andivionian-status-classifier]: https://github.com/ForNeVeR/andivionian-status-classifier#status-ventis-
[docs.contributing]: CONTRIBUTING.md
[docs.license]: LICENSE.md
[docs.maintainer-guide]: MAINTAINERSHIP.md
[docs.tinylog]: https://tinylog.org/v2/configuration/
[icons.checktimer]: src/main/resources/icons/checktimer.svg
[releases]: https://github.com/ForNeVeR/checktimer/releases
[screenshot]: docs/screenshot.png
[status-ventis]: https://img.shields.io/badge/status-ventis-yellow.svg
