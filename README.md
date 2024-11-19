<!--
SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>

SPDX-License-Identifier: MIT
-->

![Project icon][icons.checktimer] checktimer [![Status Terrid][status-terrid]][andivionian-status-classifier] [![GitHub Release][badge.release]][releases]
==========

![checktimer screenshot][screenshot]

Checktimer is a simple manual time tracking application. It allows the user to enter the current activity and saves all the activity to a CSV file (as configured).

Prerequisites
-------------
To run checktimer, install Java 21 or newer.

Run
---
1. Download the package for your operating system from the [Releases][releases] page.

   Note that there are two kinds of packages: JVM-dependent (with `.jvm` suffix) and JVM-independent (that bring their own JVM, without the `.jvm` suffix).
2. Unpack the archive.
3. If you have downloaded a JVM-dependent package, make sure the Java 21 instance is active (e.g., by running `java --version` and inspecting the output in the shell).
4. For a JVM-dependent package, use `checktimer-<version>/bin/checktimer` or `checktimer.bat` script.

   For a JVM-independent package, use `checktimer/checktimer.exe` executable file.

The only command line argument is the path to the configuration file (see below). If no argument is provided, the application will try to read the configuration from `~/checktimer.cfg` file, where `~` is a notation for your user's home directory (`USERPROFILE` on Windows, `HOME` on others).

For instructions on how to build and run the application from sources, see the [Contributor Guide][docs.contributing].

Configure
---------
Here's the configuration file example:
```
database: "/home/user/checktimer.csv" # path to the data storage
```

### Logging
Checktimer uses tinylog 2 logging framework. By default, it will write logs to `<temp folder>/checktimer/` directory, and to the standard output. It is possible to override the logging parameters, see [the logging documentation page][docs.tinylog] for details.

Data File Format
----------------
The data file has CSV format of the following columns:
```csv
# Start datetime in ISO-8601 format,Duration,Project,Activity
2020-01-01T01:00:00.999Z,6:50:46,My Project,My current activity
```

Documentation
-------------
- [Changelog][docs.changelog]
- [Contributor Guide][docs.contributing]
- [Maintainer Guide][docs.maintainer-guide]

License
-------
The project is distributed under the terms of [the MIT license][docs.license].

The license indication in the project's sources is compliant with [the REUSE specification v3.3][reuse.spec].

[andivionian-status-classifier]: https://github.com/ForNeVeR/andivionian-status-classifier#status-terrid-
[badge.release]: https://img.shields.io/github/v/release/ForNeVeR/checktimer
[docs.changelog]: CHANGELOG.md
[docs.contributing]: CONTRIBUTING.md
[docs.license]: LICENSE.txt
[docs.maintainer-guide]: MAINTAINERSHIP.md
[docs.tinylog]: https://tinylog.org/v2/configuration/
[icons.checktimer]: src/main/resources/icons/checktimer.svg
[releases]: https://github.com/ForNeVeR/checktimer/releases
[reuse.spec]: https://reuse.software/spec-3.3/
[screenshot]: docs/screenshot.png
[status-terrid]: https://img.shields.io/badge/status-terrid-green.svg
