%~d0
cd %~p0
SET DEVELOPMENT_HOME=%CD%
echo off;

for /F "usebackq tokens=1,2 delims==" %%i in (`wmic os get LocalDateTime /VALUE 2^>NUL`) do if '.%%i.'=='.LocalDateTime.' set ldt=%%j
set ldt=%ldt:~0,4%-%ldt:~4,2%-%ldt:~6,2% %ldt:~8,2%:%ldt:~10,2%:%ldt:~12,6%
REM echo %ldt% to clipboard
echo %ldt%| clip  
cd ..
cd %DEVELOPMENT_HOME%\be-api\src\main\resources\
del version-info-api.txt
echo %ldt%>version-info-api.txt
echo version-info-api: %ldt%

cd %DEVELOPMENT_HOME%
cd be-core
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../be-api-config
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../cms-core
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../be-api
call mvn clean install -e -Dmaven.javadoc.skip=true

