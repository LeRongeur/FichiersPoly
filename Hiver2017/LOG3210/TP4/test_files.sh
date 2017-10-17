#!/bin/bash
mkdir -p res
if [ $1 = "1" ]
then
	for file in data/*.ea; do
		echo $file
		filename=$(basename $file .${file##*.})
		java -cp gen Parser < $file > "res/$filename".txt

		echo
	done
else
	for file in data/*.eg; do
		echo $file
		filename=$(basename $file .${file##*.})
		java -cp gen Parser < $file > "res/$filename".txt

		echo
	done
fi
