@echo off

SET CurrDir=%CD%
CD..
SET DEVELOPMENT_HOME=%CD%

@echo on

cd %DEVELOPMENT_HOME%\jcanary-common\
call mvn clean install

cd %DEVELOPMENT_HOME%\jcanary-workflow\
call mvn clean package

CD..
CD..
SET DEVELOPMENT_HOME=%CD%

copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-workflow\target\*.jar %DEVELOPMENT_HOME%\enterprise-parent\src\libs\

pause