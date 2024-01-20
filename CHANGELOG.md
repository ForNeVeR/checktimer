Changelog
=========

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic
Versioning v2.0.0](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-01-20
### Changed
- **(Breaking!)** Output CSV file format has been changed: now it stores the activity start date time as well.
- **(Breaking!)** The command-line arguments now require a path to the configuration file, not the data file. See the documentation for details.
- **(Platform update!)** The application now requires Java 17 to run.
- Small layout changes in how the information on the active task is hidden by ellipsis.

### Added
- Configuration file (for now, with only one parameter supported: the path to the data file).
- A button to keep the application window on top of all the others.
- A custom window icon.
- The application is now packaged as JAR together with native dependencies (so it only requires external JVM to run).

## [0.0.1] - 2019-10-17
The initial project release as a standalone ScalaFX application. Included features:
- manual time tracking of your current project and activity,
- saving all the data to a CSV file,
- command-line arguments: `[csv-file-path]` (`~/checktimer.csv` by default),
- requires Oracle Java 8 with JavaFX bundled.

[1.0.0]: https://github.com/ForNeVeR/checktimer/releases/tag/0.0.1
[Unreleased]: https://github.com/ForNeVeR/checktimer/compare/0.0.1...HEAD
