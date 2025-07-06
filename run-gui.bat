@echo off
echo Starting Global Development Data Analysis GUI...
echo.

cd /d "%~dp0"
sbt "runMain DevelopmentGUIApp"

pause
