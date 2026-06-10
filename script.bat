@echo off

if exist out rmdir /s /q out
mkdir out
mkdir out\classes

dir /s /b src\*.java > sources.txt

javac -cp "lib/*" -d out\classes @sources.txt

if errorlevel 1 (
    echo Compilation echouee
    pause
    exit /b 1
)

jar cf out\Framework.jar -C out\classes .

del sources.txt

echo JAR cree : out\Framework.jar
pause