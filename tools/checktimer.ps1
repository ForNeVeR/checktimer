param (
    $ConfigFile = "$(Resolve-Path ~)/checktimer.cfg",

    $SourceDir = "$PSScriptRoot/..",
    $Jar = "$SourceDir/target/scala-2.12/checktimer_2.12-0.0.1.jar",
    $MainClass = 'me.fornever.checktimer.Application',

    $java = 'java',
    $sbt = 'sbt'
)

$ErrorActionPreference = 'Stop'

function exec($command) {
    Write-Host "[checktimer exec] $command $args" -ForegroundColor White
    & $command $args
    if (!$?) {
        throw "[checktimer error] $command $args = $LASTEXITCODE"
    }
}

Push-Location $SourceDir
try {
    exec $sbt package
    $classpath = exec $sbt 'export compile:dependencyClasspath' | Select-Object -Last 1

    exec $java -cp "$classpath;$Jar" $MainClass $ConfigFile
} finally {
    Pop-Location
}
