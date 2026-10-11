@echo off
setlocal
cd /d "%~dp0"
set "ZOO_JAVA=java"
set "ZOO_JAVAC=javac"
if defined JAVA_HOME (
    set "ZOO_JAVA=%JAVA_HOME%\bin\java.exe"
    set "ZOO_JAVAC=%JAVA_HOME%\bin\javac.exe"
)
"%ZOO_JAVAC%" -version >nul 2>&1
if errorlevel 1 (
    echo JDK 8+ diperlukan. Atur JAVA_HOME ke folder JDK, bukan JRE.
    pause
    exit /b 1
)
if not exist build\classes mkdir build\classes
"%ZOO_JAVAC%" -encoding UTF-8 -source 8 -target 8 -d build\classes src\main\java\*.java
if errorlevel 1 (
    pause
    exit /b 1
)
"%ZOO_JAVA%" -cp build\classes Main
