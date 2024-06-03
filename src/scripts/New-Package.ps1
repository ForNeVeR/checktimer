# SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
#
# SPDX-License-Identifier: MIT

param (
    $SourceRoot = "$PSScriptRoot/../..",
    $TargetDir = "$SourceRoot/target/universal/stage",
    [switch] $Force,
    $OutDir = "$SourceRoot/target/jpackage"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$version = (Get-Content "$SourceRoot/build.sbt" | Select-String '^version\s*:=\s*"(.*)"$').Matches[0].Groups[1].Value
if (!$version) {
    throw 'Cannot find version in the build.sbt file.'
}

if ($Force -and (Test-Path $OutDir)) {
    Write-Output "Removing directory `"$OutDir`"."
    Remove-Item -Force -Recurse -LiteralPath $OutDir
}

Write-Host "Running jpackage."
jpackage --type app-image `
    --name checktimer `
    --app-version $version `
    --input "$TargetDir/lib" `
    --dest target/jpackage `
    --main-jar "me.fornever.checktimer-$version-launcher.jar" `
    --win-console
if (!$?) {
    throw "Exit code from jpackage: $LASTEXITCODE."
}

Write-Output "Package written to `"$OutDir`"."
