<!--
SPDX-FileCopyrightText: 2024-2025 Friedrich von Never <friedrich@fornever.me>

SPDX-License-Identifier: MIT
-->

Maintainer Guide
================

Release
-------
To release a new version:
1. Update the project status in the `README.md`, if required.
2. Update the copyright statement in the `LICENSE.txt`, if required.
3. Choose a new version according to [Semantic Versioning][semver]. It should consist of three numbers (i.e. `1.0.0`).
4. Make sure there's a properly formed version entry in the `CHANGELOG.md`.
5. Update the `version` property in the `build.sbt` file.
6. Merge the aforementioned changes via a pull request.
7. Push a tag named `v<VERSION>` to GitHub.

The new release for all three supported operating systems will be published automatically.

[semver]: https://semver.org/spec/v2.0.0.html
