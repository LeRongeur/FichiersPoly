#!/bin/bash
mkdir -p res
for file in data/*.le; do
    echo $file
	filename=$(basename $file .${file##*.})
    java -cp gen Parser < $file > "res/$filename".dot
	dot -Tjpg -o "res/$filename".jpg "res/$filename".dot

    echo
done
