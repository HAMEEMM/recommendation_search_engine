@echo off
rem =============================================================================
rem build.bat – Compiles sources and packages search-engine.jar (Windows CMD)
rem =============================================================================
setlocal

set "SCRIPT_DIR=%~dp0"
set "SRC=%SCRIPT_DIR%src\main\java"
set "LIB=%SCRIPT_DIR%lib"
set "BUILD=%SCRIPT_DIR%build"
set "JAR=%SCRIPT_DIR%search-engine.jar"
set "CP=%LIB%\jackson-databind-2.15.2.jar;%LIB%\jackson-core-2.15.2.jar;%LIB%\jackson-annotations-2.15.2.jar"

echo ==> Compiling sources...
if not exist "%BUILD%" mkdir "%BUILD%"
dir /s /b "%SRC%\*.java" > "%BUILD%\sources.txt"
javac -source 8 -target 8 -cp "%CP%" -d "%BUILD%" @"%BUILD%\sources.txt"
if errorlevel 1 ( echo Compilation failed & exit /b 1 )

echo ==> Packaging fat JAR...
if not exist "%BUILD%\_extracted" mkdir "%BUILD%\_extracted"
for %%j in ("%LIB%\*.jar") do (
    pushd "%BUILD%\_extracted" && jar xf "%%j" && popd
)
xcopy /E /Y /Q "%BUILD%\com" "%BUILD%\_extracted\com\" >nul

if not exist "%BUILD%\_extracted\META-INF" mkdir "%BUILD%\_extracted\META-INF"
echo Main-Class: com.mayo.searchengine.Main> "%BUILD%\_extracted\META-INF\MANIFEST.MF"

pushd "%BUILD%\_extracted" && jar cfm "%JAR%" META-INF\MANIFEST.MF . && popd

echo ==> Build complete: %JAR%
endlocal
