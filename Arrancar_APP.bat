@echo off
set JAVA_HOME=C:\Java\jdk-24.0.2
set PATH=%JAVA_HOME%\bin;%PATH%
cd /d "%~dp0"
docker compose -p baskethub up -d
call mvnw.cmd spring-boot:run
pause