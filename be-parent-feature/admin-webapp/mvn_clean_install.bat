@echo off

%~d0
cd %~p0

SET CurrDir=%CD%
SET DEVELOPMENT_HOME=%CD%

@echo on

::call mvn clean package
call mvn clean install -e -Dmaven.javadoc.skip=true

::CD..
::SET DEVELOPMENT_HOME=%CD%

::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-dts\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\

::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-common\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\

::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-sla\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\

::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-storage\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\

::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-workflow\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\
::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-workflow-activiti\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\

::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-core\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\
::copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-import-excel\target\*.jar %DEVELOPMENT_HOME%\mbal-parent\src\libs\

for /F "usebackq tokens=1,2 delims==" %%i in (`wmic os get LocalDateTime /VALUE 2^>NUL`) do if '.%%i.'=='.LocalDateTime.' set ldt=%%j
set ldt=%ldt:~0,4%-%ldt:~4,2%-%ldt:~6,2% %ldt:~8,2%:%ldt:~10,2%:%ldt:~12,6%
REM echo %ldt% to clipboard
echo %ldt%| clip  
cd ..
cd shared-resources\src\main\resources\templates\views\home\
del version-info.html
echo %ldt%>version-info.html
cd %DEVELOPMENT_HOME%
echo %ldt%>version-info.txt
echo version-info: %ldt%
REM pause
TIMEOUT /T 5
call mvn clean package
pause