#!/bin/bash

for file in data/*.le; do
    echo $file

    java -cp gen Exercice2 < $file >&2 && echo "REUSSI" || echo "ECHEC"

    echo
done
