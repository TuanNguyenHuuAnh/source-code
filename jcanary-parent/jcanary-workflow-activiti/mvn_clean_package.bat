@echo off

%~d0
cd %~p0

SET CurrDir=%CD%
CD..
SET DEVELOPMENT_HOME=%CD%

@echo on

cd %DEVELOPMENT_HOME%\jcanary-common\
call mvn clean install

cd %DEVELOPMENT_HOME%\jcanary-workflow-activiti\
call mvn clean package

CD..
CD..
SET DEVELOPMENT_HOME=%CD%

copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-workflow-activiti\target\*.jar %DEVELOPMENT_HOME%\enterprise-parent\src\libs\

pause