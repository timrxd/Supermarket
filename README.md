# Supermarket [![Build Status](https://travis-ci.org/timrxd/Supermarket.svg?branch=master)](https://travis-ci.org/timrxd/Supermarket)

Test program for a basic register class. The Register takes a set of product codes, looks up the price of each product, and computes the total amount of the sale. The Register then takes the total and applies the local sales tax (8.75%) and returns the total after applying the extra sales tax.

###### To Run (Unix)
    javac -d bin/main/java/ src/main/java/*.java  
    java -cp ./bin/main/java/:./lib/* main.java.RegisterDriver
or just use

    ./run.sh

###### To Run (Windows)
    javac -d bin/main/java/ src/main/java/*.java
    java -cp "./bin/main/java/;./lib/*" main.java.RegisterDriver
or just use

    ./win_run.sh
