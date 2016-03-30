# Supermarket [![Build Status](https://travis-ci.org/timrxd/Supermarket.svg?branch=master)](https://travis-ci.org/timrxd/Supermarket)

Test program for a basic register class. The Register takes a set of product codes, looks up the price of each product, and computes the total amount of the sale. The Register then takes the total and applies the local sales tax (8.75%) and returns the total after applying the extra sales tax.

###### To Run
    javac -d bin/main/java/ src/main/java/*.java  
    java -cp ./bin/main/java/:./lib/* main.java.RegisterDriver
