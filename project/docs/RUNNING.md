# Running and Development Guide

## Prerequisites
- Java JDK 21+
- PowerShell (Windows)

## Compile
```powershell
$src = "Demo_LMS_UsingPatterns/src"
$out = "Demo_LMS_UsingPatterns/out"
$files = Get-ChildItem -Path $src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d $out $files
```

## Run
```powershell
java -cp "Demo_LMS_UsingPatterns/out" com.lms.Main
```

## Open App
- Browser: `http://localhost:8080`

## Common Issue: Port 8080 busy
```powershell
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

## Demo Data
Demo data is seeded in `LmsApplication.seedDemoData()` at startup.
