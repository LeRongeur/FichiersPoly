#!/bin/bash

for file in data/*.eq; do
	touch temp.data
	touch temp2.data
	read equation < "$file";
	echo $equation >> temp.data
	expected=$(cat "$file" | sed -n "2 p")

	java -cp gen Exercice1 < temp.data >> temp2.data
	result=$(cat temp2.data)
	
	echo "resultat pour $file"
	if [ $expected = $result ]
	then
		echo "REUSSI"
	else
		echo "ECHEC : expected : $expected found : $result"
	fi
	rm *.data
	echo "--------------------------------"
	echo
done
