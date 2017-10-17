#!/bin/bash

PATH=$PATH:$HOME/Bureau/Hiver2017/LOG3210/javacc-5.0/bin

mkdir -p gen

cp LangageH17.jjt gen
cp PrintMachineCodeVisitor.java gen
cp ast/*.java gen

cd gen
jjtree LangageH17.jjt
javacc LangageH17.jj

javac *.java
echo Compilation javac terminee

cd ..
