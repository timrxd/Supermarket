#!/bin/bash

mkdir -p bin/main/java
javac -d bin/main/java/ src/main/java/*.java  
java -cp ./bin/main/java/:./lib/* main.java.RegisterDriver
