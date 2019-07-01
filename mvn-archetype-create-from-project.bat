call mvn package -Dmaven.test.skip=true -U
call mvn clean
call mvn eclipse:clean
call mvn archetype:create-from-project

echo ----------------------------------------------------------------------------
echo override some file and delete some file
echo ----------------------------------------------------------------------------
del target\generated-sources\archetype\src\main\resources\archetype-resources\todo.txt
del target\generated-sources\archetype\src\main\resources\archetype-resources\release_checklist.txt
del target\generated-sources\archetype\src\main\resources\archetype-resources\pom-archetype.xml
del target\generated-sources\archetype\src\main\resources\archetype-resources\mvn-archetype-create-from-project.bat
del target\generated-sources\archetype\src\main\resources\archetype-resources\archetype-catalog.xml
copy /Y target\generated-sources\archetype\pom.xml target\generated-sources\archetype\pom.bak.xml
copy /Y pom-archetype.xml target\generated-sources\archetype\pom.xml


echo ----------------------------------------------
echo install and deploy archetype
echo ----------------------------------------------
cd target/generated-sources/archetype
call mvn clean
call mvn install deploy
cd ../../../

echo ----------------------------------------------
echo "before exec: mvn eclipse:eclipse "
echo ----------------------------------------------
pause
call mvn eclipse:eclipse
call mvn package -Dmaven.test.skip=true

REM -----------------------------------------------
REM mvn archetype:generate -DarchetypeCatalog=local
REM -----------------------------------------------