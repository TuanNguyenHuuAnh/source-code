@echo off

%~d0
cd %~p0
SET CurrDir=%CD%
SET DEVELOPMENT_HOME=%CD%

@echo on

cd ../admin-webapp
for /F "usebackq tokens=1,2 delims==" %%i in (`wmic os get LocalDateTime /VALUE 2^>NUL`) do if '.%%i.'=='.LocalDateTime.' set ldt=%%j
set ldt=%ldt:~0,4%-%ldt:~4,2%-%ldt:~6,2% %ldt:~8,2%:%ldt:~10,2%:%ldt:~12,6%
REM echo %ldt% to clipboard
echo %ldt%| clip  
cd ..
cd %DEVELOPMENT_HOME%\shared-resources\src\main\resources\templates\views\home\
del version-info.html
echo %ldt%>version-info.html
cd %DEVELOPMENT_HOME%\admin-webapp
echo %ldt%>version-info.txt
echo version-info: %ldt%

cd ../be-core
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../webapp-config
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../shared-resources
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../be-process
call mvn clean install -e -Dmaven.javadoc.skip=true
 
cd ../cms-core
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../cms-portal
call mvn clean install -e -Dmaven.javadoc.skip=true

cd ../admin-webapp
call mvn clean install -e -Dmaven.javadoc.skip=true

