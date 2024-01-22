Changelog
=========

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic
Versioning v2.0.0](https://semver.org/spec/v2.0.0.html).

## [Unreleased] (2.0.0)
### Changed
- **(Requirement update!)** The application now requires Java 21 to run.
- The file operations are now asynchronous. A corresponding label will be shown on the main form during the operation.

## [1.0.0] - 2024-01-20
### Changed
- **(Breaking!)** Output CSV file format has been changed: now it stores the activity start date time as well.
- **(Breaking!)** The command-line arguments now require a path to the configuration file, not the data file. See the documentation for details.
- **(Requirement update!)** The application now requires Java 17 to run.
- Small layout changes in how the information on the active task is hidden by ellipsis.

### Added
- Configuration file (for now, with only one parameter supported: the path to the data file).
- A button to keep the application window on top of all the others.
- A custom window icon.
- The application is now packaged as JAR together with native dependencies (so it only requires external JVM to run).

## [0.0.1] - 2016-11-20
The initial project release as a standalone ScalaFX application. Included features:
- manual time tracking of your current project and activity,
- saving all the data to a CSV file,
- command-line arguments: `[csv-file-path]` (`~/checktimer.csv` by default),
- requires Oracle Java 8 with JavaFX bundled.

[0.0.1]: https://github.com/ForNeVeR/checktimer/releases/tag/0.0.1
[1.0.0]: https://github.com/ForNeVeR/checktimer/compare/0.0.1...v1.0.0
[Unreleased]: https://github.com/ForNeVeR/checktimer/compare/v1.0.0...HEAD
