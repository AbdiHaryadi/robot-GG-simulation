@echo off
javac -cp test/;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar -d ./bin test/*.java src/*.java
java -cp bin/;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore TileBoardTest RobotGGTest ShortestRouteTest