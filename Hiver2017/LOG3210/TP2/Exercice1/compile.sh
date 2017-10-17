#!/bin/bash

PATH=$PATH:$HOME/Bureau/Hiver2017/LOG3210/javacc-5.0/bin

mkdir -p gen

cp Ex1.jj gen

cd gen
javacc Ex1.jj

javac *.java
echo Compilation javac terminee

cd ..
