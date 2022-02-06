# thanks to https://stackoverflow.com/questions/41608676/run-a-jar-file-from-powershell/51359402

if(-not $env:JAVA_HOME)
{
    Write-Error "JAVA_HOME not set"
    break
}

$params = @{
    FilePath = [string]::Format("{0}\bin\java.exe",$env:JAVA_HOME)
    WorkingDirectory = ""
    ArgumentList = @("-jar", "bin\demogame-${project.version}-shaded.jar")
    RedirectStandardError = "c:\temp\demogame-error.log"
    PassThru = $true
    Wait = $true
}

$p = Start-Process @params

if($p.ExitCode -eq 0)
{
    Write-Output "Demogame complete"
}
else
{
    Write-Output "Demogame start failed"
}