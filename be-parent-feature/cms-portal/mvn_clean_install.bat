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

pause