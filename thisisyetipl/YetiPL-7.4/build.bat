@echo off
setlocal
where java >nul 2>nul || (echo Java is required. Install JDK 25. & exit /b 1)
where gradlew.bat >nul 2>nul && (call gradlew.bat clean build & exit /b %errorlevel%)
where gradle >nul 2>nul || (echo Gradle 9.1+ is required. See BUILDING.md. & exit /b 1)
gradle clean build
exit /b %errorlevel%
