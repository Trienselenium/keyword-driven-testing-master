set ProjectPath=%~dp0
cd %ProjectPath%
set classpath=%ProjectPath%bin;%ProjectPath%lib\*
java %ProjectPath%\execute\executionEngine\RunTestscript.java
pause