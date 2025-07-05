@echo off

SET CurrDir=%CD%
SET DEVELOPMENT_HOME=%CD%

@echo on

call mvn clean package

CD..
CD..
SET DEVELOPMENT_HOME=%CD%

copy %DEVELOPMENT_HOME%\jcanary-parent\jcanary-storage\target\*.jar %DEVELOPMENT_HOME%\enterprise-parent\src\libs\

pause